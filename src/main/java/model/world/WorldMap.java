package main.java.model.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    public void setPlayerOneUnits(List<Integer> playerOneUnits) {
        for (int i = 0; i < this.countries.size(); i++) {
            if (playerOneUnits.get(i) > 0) {
                this.getCountryById(i).setUnits(playerOneUnits.get(i));
                this.getCountryById(i).setOccupant(this.playerOne);
                this.playerOne.addConqueredCountry(getCountryById(i));
            }
        }
        for (Continent continent : this.continents) {
            boolean conquered = true;
            for (Country country : continent.getCountries()) {
                if (!country.hasOccupant() || !country.getOccupant().equals(this.playerOne)) {
                    conquered = false;
                    break;
                }
            }
            if (conquered) {
                this.playerOne.addConqueredContinent(continent);
            }
        }
    }

    public void setPlayerTwoUnits(List<Integer> playerTwoUnits) {
        for (int i = 0; i < this.countries.size(); i++) {
            if (playerTwoUnits.get(i) > 0) {
                this.getCountryById(i).setUnits(playerTwoUnits.get(i));
                this.getCountryById(i).setOccupant(this.playerTwo);
                this.playerTwo.addConqueredCountry(getCountryById(i));
            }
        }
        for (Continent continent : this.continents) {
            boolean conquered = true;
            for (Country country : continent.getCountries()) {
                if (!country.hasOccupant() || !country.getOccupant().equals(this.playerTwo)) {
                    conquered = false;
                    break;
                }
            }
            if (conquered) {
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

    public Country getLeastFortifiedCountry(Player player) {
        return this.countries.stream()
                .filter(country -> !country.hasOccupant() || country.getOccupant().equals(player))
                .sorted(Comparator.comparing(Country::getUnits)).collect(toList()).get(0);
    }

    @Override
    public Object clone() {
        WorldMap clone = new WorldMap();
        // Clone countries.
        this.getCountries().forEach(country -> clone.addCountry(country.getId()));
        // Clone continents.
        this.getContinents().forEach(continent -> {
            List<Integer> spec = continent.getCountries().stream().mapToInt(Country::getId)
                    .boxed().collect(Collectors.toList());
            spec.add(0, continent.getContinentBonus());
            clone.addContinent(continent.getId(), spec);
        });
        // Clone edges.
        this.getCountries().forEach(country ->
                country.getNeighbours().forEach(neighbor ->
                        clone.addEdgeDirected(country.getId(), neighbor.getId())));

        return clone;
    }
}
