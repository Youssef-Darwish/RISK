package main.java.model.world;

import java.util.ArrayList;
import java.util.List;

public class WorldMap {
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

    public void addContinent(Continent continent) {
        this.continents.add(continent);
    }

    public void addCountry(int countryId) {
        this.countries.add(new Country(countryId));
    }

    public void addEdge(int countryOneId, int countryTwoId) {
        this.countries.get(countryOneId).addNeighbour(this.countries.get(countryTwoId));
        this.countries.get(countryTwoId).addNeighbour(this.countries.get(countryOneId));
    }

    public Player getPlayerOne() {
        return this.playerOne;
    }

    public Player getPlayerTwo() {
        return this.playerTwo;
    }

}
