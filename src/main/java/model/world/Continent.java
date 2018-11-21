package main.java.model.world;

import java.util.List;

public class Continent {
    public Continent(List<Integer> countriesIds) {
        //TODO
    }

    private int id;
    private List<Country> countries;
    private int continentBonus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public int getContinentBonus() {
        return continentBonus;
    }

    public void setContinentBonus(int continentBonus) {
        this.continentBonus = continentBonus;
    }

    public boolean isConquered(Player player) {
        for (Country country : this.countries) {
            if (!country.getOccupant().equals(player)) {
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
