package main.java.model.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class WorldMap implements Cloneable {
    private List<Continent> continents;
    private List<Country> countries;
    private Player playerOne, playerTwo;

    public WorldMap() {
        this.continents = new ArrayList<>();
        this.countries = new ArrayList<>();
        this.playerOne = new Player(0);
        this.playerTwo = new Player(1);
    }

    public Country getCountryById(int id) {
        return this.countries.get(id);
    }

    public Continent getContinentById(int id) {
        return this.continents.get(id);
    }

    public void addContinent(int continentId, List<Integer> continentSpecification) {
        Continent continent = new Continent(continentId, continentSpecification.get(0));
        for (int countryId :  continentSpecification.subList(1, continentSpecification.size())) {
            continent.addCountry(getCountryById(countryId));
        }
        this.continents.add(continent);
    }

    public void addCountry(int countryId) {
        this.countries.add(new Country(countryId));
    }

    public void addEdge(int countryOneId, int countryTwoId) {
        addEdgeDirected(countryOneId, countryTwoId);
        addEdgeDirected(countryTwoId, countryOneId);
    }

    public void addEdgeDirected(int countryOneId, int countryTwoId) {
        this.countries.get(countryOneId).addNeighbour(this.countries.get(countryTwoId));
    }

    public void setPlayerOneCountries(List<Integer> playerOneUnits) {
        for (int i = 0; i < this.countries.size(); i++) {
            if (playerOneUnits.get(i) > 0) {
                this.getCountryById(i).setUnits(playerOneUnits.get(i));
                this.getCountryById(i).setOccupant(this.playerOne);
                this.playerOne.addConqueredCountry(getCountryById(i));
            }
        }
    }

    public void setPlayerOneContinents() {
        for (Continent continent : this.continents) {
            if (continent.isConquered(this.playerOne)) {
                this.playerOne.addConqueredContinent(continent);
            }
        }
    }

    public void setPlayerTwoCountries(List<Integer> playerTwoUnits) {
        for (int i = 0; i < this.countries.size(); i++) {
            if (playerTwoUnits.get(i) > 0) {
                this.getCountryById(i).setUnits(playerTwoUnits.get(i));
                this.getCountryById(i).setOccupant(this.playerTwo);
                this.playerTwo.addConqueredCountry(getCountryById(i));
            }
        }
    }

    public void setPlayerTwoContinents() {
        for (Continent continent : this.continents) {
            if (continent.isConquered(this.playerTwo)) {
                this.playerTwo.addConqueredContinent(continent);
            }
        }
    }

    public Player getPlayerOne() {
        return this.playerOne;
    }

    public Player getPlayerTwo() {
        return this.playerTwo;
    }

    public List<Country> getCountries() {
        return this.countries;
    }

    public List<Continent> getContinents() {
        return this.continents;
    }

    @Override
    public Object clone() {
        WorldMap clone = new WorldMap();
        // Clone countries.
        this.getCountries().forEach(country -> clone.addCountry(country.getId()));
        // Clone edges.
        this.getCountries().forEach(country ->
                country.getNeighbours().forEach(neighbor ->
                        clone.addEdgeDirected(country.getId(), neighbor.getId())));
        // Clone continents.
        this.getContinents().forEach(continent -> {
            List<Integer> spec = continent.getCountries().stream().mapToInt(Country::getId)
                    .boxed().collect(Collectors.toList());
            spec.add(0, continent.getContinentBonus());
            clone.addContinent(continent.getId(), spec);
        });
        // Clone players
        this.getCountries().forEach(country -> {
            if (country.getOccupant().getId() == clone.getPlayerOne().getId()) {
                clone.getCountryById(country.getId()).setOccupant(clone.getPlayerOne());
                clone.getCountryById(country.getId()).setUnits(country.getUnits());
                clone.getPlayerOne().addConqueredCountry(clone.getCountryById(country.getId()));
            } else if (country.getOccupant().getId() == clone.getPlayerTwo().getId()) {
                clone.getCountryById(country.getId()).setOccupant(clone.getPlayerTwo());
                clone.getCountryById(country.getId()).setUnits(country.getUnits());
                clone.getPlayerTwo().addConqueredCountry(clone.getCountryById(country.getId()));
            }
        });
        clone.getContinents().forEach(continent -> {
            if (continent.isConquered(clone.getPlayerOne())) {
                clone.getPlayerOne().addConqueredContinent(continent);
            } else if (continent.isConquered(clone.getPlayerTwo())) {
                clone.getPlayerTwo().addConqueredContinent(continent);
            }
        });
        return clone;
    }

    public List<Continent> getUnoccupiedContinents() {
        return this.continents.stream()
                .filter(continent -> !playerOne.getConqueredContinents().contains(continent))
                .collect(Collectors.toList());
    }

    public List<Continent> getUnconqueredContinents(Player player, Comparator<Continent> comparator) {
        return this.continents.stream()
                .filter(continent -> !player.getConqueredContinents().contains(continent))
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorldMap)) return false;
        WorldMap worldMap = (WorldMap) o;
        return Objects.equals(continents, worldMap.continents) &&
                Objects.equals(countries, worldMap.countries) &&
                Objects.equals(playerOne, worldMap.playerOne) &&
                Objects.equals(playerTwo, worldMap.playerTwo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(continents, countries, playerOne, playerTwo);
    }
}
