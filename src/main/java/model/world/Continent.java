package main.java.model.world;

import java.util.ArrayList;
import java.util.List;

public class Continent {
    private int id;
    private List<Country> countries;
    private int continentBonus;

    public Continent(int id, int continentBonus) {
        this.id = id;
        this.continentBonus = continentBonus;
        this.countries = new ArrayList<>();
    }


    public int getId() {
        return id;
    }


    public List<Country> getCountries() {
        return countries;
    }

    public void addCountry(Country country) {
        this.countries.add(country);
        country.setContinent(this);
    }

    public int getContinentBonus() {
        return continentBonus;
    }

    public boolean isConquered(Player player) {
        for (Country country : this.countries) {
            if (!country.getOccupant().equals(player)) {
                return false;
            }
        }
        return true;
    }
}
