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
            if (!country.hasOccupant() || !country.getOccupant().equals(player)) {
                return false;
            }
        }
        return true;
    }

    // Used by semi-pacifist agent
    public Country getLeastFortifiedCountry(){
        int minIndex = 0;
        for (int i = 0;i < countries.size();i++){
            if(countries.get(i).getUnits()<countries.get(minIndex).getUnits()){
                minIndex = i;
            }
        }

        return countries.get(minIndex);
    }

    // used by aggressive agent
    public Country getMostFortifiedCountry(){
        int maxIndex = 0;
        for (int i = 0;i < countries.size();i++){
            if(countries.get(i).getUnits()>countries.get(maxIndex).getUnits()){
                maxIndex = i;
            }
        }
        return countries.get(maxIndex);
    }

}


//TODO : add comparator :
//compare continents : owned by oppnent : put first ,
//else : sort by number of countries owned by opponent
