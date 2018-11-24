package main.java.model.world;

import java.util.ArrayList;
import java.util.List;

public class Country implements Comparable<Country>{

    private int id;
    private Player occupant;
    private Continent continent;
    private int units;
    private List<Country> neighbours;

    public Country(int id) {
        this.id = id;
        this.units = 0;
        this.neighbours = new ArrayList<>();
        this.occupant = null;
    }

    public int getId() {
        return id;
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

    public Integer getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public List<Country> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Country neighbour) {
        this.neighbours.add(neighbour);
    }

    public boolean hasOccupant() {
        return this.occupant != null;
    }

    public boolean canAttack(Country country){
        return this.hasOccupant() && !country.getOccupant().equals(this.occupant) &&
                this.neighbours.contains(country) && (this.getUnits() - country.getUnits() > 1);
    }

    @Override
    public int compareTo(Country o) {
        return this.getUnits().compareTo(o.getUnits());
    }
}
