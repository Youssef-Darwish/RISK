package main.java.model.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.floor;
import static java.lang.Math.max;

public class Player {
    private int id;
    private int lastTurnBonusUnits;
    private List<Country> conqueredCountries;
    private List<Continent> conqueredContinents;

    //TODO : logic and order of calculating bonus armies ( next & current)
    public Player(int id) {
        this.id = id;
        this.lastTurnBonusUnits = 0;
        this.conqueredCountries = new ArrayList<>();
        this.conqueredContinents = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public int getLastTurnBonusUnits() {
        return lastTurnBonusUnits;
    }

    public List<Country> getConqueredCountries() {
        return conqueredCountries;
    }

    public void addConqueredCountry(Country country) {
        this.conqueredCountries.add(country);
    }

    public void removeConqueredCountry(Country country) {
        this.conqueredCountries.remove(country);
    }

    public List<Continent> getConqueredContinents() {
        return conqueredContinents;
    }

    public void addConqueredContinent(Continent continent) {
        this.conqueredContinents.add(continent);
    }

    public void removeConqueredContinent(Continent continent) {
        this.conqueredContinents.remove(continent);
    }

    public void setLastTurnBonusUnits(int bonusUnits) {
        this.lastTurnBonusUnits = bonusUnits;
    }

    public Country getMostFortifiedCountry(){
        Country mostFortifiedCountry =  this.getConqueredCountries().get(0);
        int maxUnits = mostFortifiedCountry.getUnits();

        for (Country c:this.getConqueredCountries()){
            if(c.getUnits()>maxUnits){
                mostFortifiedCountry = c;
                maxUnits = c.getUnits();
            }
        }
        return mostFortifiedCountry;
        // loop and get the max number of armies in countries
    }

    public Country getWeakestCountry(){
        Country weakestCountry =  this.getConqueredCountries().get(0);
        int minUnits = weakestCountry.getUnits();

        for (Country c:this.getConqueredCountries()){
            if(c.getUnits()<minUnits){
               weakestCountry = c;
               minUnits = c.getUnits();
            }
        }
        return weakestCountry;
    }

    public Continent getNearestConqueredContinent(){
        // TODO
        return null;
    }

    public int getTurnBonus(){
        // At the start of each turn, the palyer gets a bonus = 2 per each continent + # of conquered countries / 3 + bonus from last turn conquests.
        return (2 * conqueredContinents.size()) + (int)max(3, floor(conqueredCountries.size() / 3)) + lastTurnBonusUnits;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    //TODO implement / decide : canAttack in Player Class or State Class
    //Done in country Class


    //TODO implement / discuss logic
    public void attack(Country country){

    }
}
