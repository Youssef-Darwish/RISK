package main.java.model.world;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.floor;
import static java.lang.Math.max;

public class Player {
    private int id;
    private int lastTurnBonusUnits;
    private Set<Country> conqueredCountries;
    private Set<Continent> conqueredContinents;

    public Player(int id) {
        this.id = id;
        this.lastTurnBonusUnits = 0;
        this.conqueredCountries = new TreeSet<>();
        this.conqueredContinents = new TreeSet<>();

    }

    public int getId() {
        return id;
    }

    public int getLastTurnBonusUnits() {
        return lastTurnBonusUnits;
    }

    public Set<Country> getConqueredCountries() {
        return conqueredCountries;
    }

    public Set<Country> getConqueredCountries(Comparator<Country> comparator) {
        return this.conqueredCountries.stream()
                .sorted(comparator)
                .collect(Collectors.toSet());
    }
    public void addConqueredCountry(Country country) {
        this.conqueredCountries.add(country);
    }

    public void removeConqueredCountry(Country country) {
        this.conqueredCountries.remove(country);
    }

    public Set<Continent> getConqueredContinents() {
        return this.conqueredContinents;
    }

    public Set<Continent> getConqueredContinents(Comparator<Continent> comparator) {
        return comparator == null ? this.conqueredContinents : this.conqueredContinents.stream().sorted(comparator).collect(Collectors.toSet());
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
