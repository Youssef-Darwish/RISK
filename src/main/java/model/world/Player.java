package main.java.model.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.lang.Math.floor;
import static java.lang.Math.max;

public class Player {
    private int id;
    private int lastTurnBonusUnits;
    private List<Country> conqueredCountries;
    private List<Continent> conqueredContinents;

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

    public List<Country> getConqueredCountries(Comparator<Country> comparator) {
        return this.conqueredCountries.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
    public void addConqueredCountry(Country country) {
        this.conqueredCountries.add(country);
    }

    public void removeConqueredCountry(Country country) {
        this.conqueredCountries.remove(country);
    }

    public List<Continent> getConqueredContinents() {
        return this.conqueredContinents;
    }

    public List<Continent> getConqueredContinents(Comparator<Continent> comparator) {
        return comparator == null ? this.conqueredContinents : this.conqueredContinents.stream().sorted(comparator).collect(Collectors.toList());
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
        return this.conqueredCountries.stream()
                .sorted(Comparator.comparing(Country::getUnits).reversed())
                .collect(Collectors.toList()).get(0);
    }

    public Country getLeastFortifiedCountry() {
        return this.conqueredCountries.stream()
                .sorted(Comparator.comparing(Country::getUnits))
                .collect(Collectors.toList()).get(0);
    }

    public Country getWeakestCountry(){
        Country weakestCountry = this.getConqueredCountries().get(0);
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

    public int getTurnAdditionalUnits(){
        // At the start of each turn, the palyer gets a constant bonus for each continent + # of conquered countries / 3 + bonus from last turn conquests.
        return getContinentsBonus() + (int)max(3, floor(conqueredCountries.size() / 3)) + lastTurnBonusUnits;
    }

    private int getContinentsBonus() {
        return this.conqueredContinents.stream().mapToInt(Continent::getContinentBonus).sum();
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
}
