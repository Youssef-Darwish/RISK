package main.java.model.world;

import java.util.ArrayList;
import java.util.List;

public class Country {

    private int id;
    private Player occupant;
    private Continent continent;
    private int units;
    private List<Country> neighbours;

    public Country(int id) {
        this.id = id;
        this.neighbours = new ArrayList<>();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getOccupant() {
        return occupant;
    }

    public void setOccupant(Player occupant) {
        this.occupant = occupant;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public List<Country> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Country> neighbours) {
        this.neighbours = neighbours;
    }

    public void addNeighbour(Country neighbour) {
        this.neighbours.add(neighbour);
    }


}
