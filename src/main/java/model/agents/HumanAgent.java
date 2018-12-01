package main.java.model.agents;

import main.java.model.Agent;
import main.java.model.game.GameState;
import main.java.model.world.Country;

public class HumanAgent implements Agent {

    @Override
    public GameState getNextState(GameState currentState) {
        currentState.swapPlayers();
        return currentState;
    }

    public GameState reinforceCountry(GameState currentState, int countryId) {
        GameState newState = (GameState) currentState.clone();
        Country reinforcedCountry = newState.getWorld().getCountryById(countryId);
        if (reinforcedCountry.getOccupant().getId() == newState.getCurrentPlayer().getId()) {
            reinforcedCountry.setUnits(reinforcedCountry.getUnits() + newState.getCurrentPlayer().getTurnAdditionalUnits());
        }
        return newState;
    }

    public GameState attack(GameState currentState, int attackingCountryId, int defendingCountryId) {
        Country attackingCountry = currentState.getWorld().getCountryById(attackingCountryId);
        Country defendingCountry = currentState.getWorld().getCountryById(defendingCountryId);
        if (attackingCountry.getOccupant().getId() == currentState.getCurrentPlayer().getId() && attackingCountry.canAttack(defendingCountry)) {
            currentState.performAttack(currentState.getWorld().getCountryById(attackingCountryId), currentState.getWorld().getCountryById(defendingCountryId));
            currentState.getCurrentPlayer().setLastTurnBonusUnits(2);
        }
        return currentState;
    }
}
