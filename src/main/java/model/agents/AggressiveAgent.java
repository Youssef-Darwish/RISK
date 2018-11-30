package main.java.model.agents;

import main.java.model.Agent;
import main.java.model.game.GameState;
import main.java.model.world.Continent;
import main.java.model.world.Country;
import main.java.model.world.Player;

import java.util.Comparator;
import java.util.List;

public class AggressiveAgent implements Agent {
    // move the needed function in Player Class to state class ? and call them by ID ?
    public AggressiveAgent() { }

    @Override
    public GameState getNextState(GameState currentState) {
        GameState newState = (GameState) currentState.clone();
        Player agentPlayer = newState.getCurrentPlayer();
        Player opponentPlayer = newState.getOpponentPlayer();

        // Place additional units on the vertex with the most units
        Country mostFortifiedCountry = agentPlayer.getMostFortifiedCountry();
        mostFortifiedCountry.setUnits(mostFortifiedCountry.getUnits() + agentPlayer.getTurnAdditionalUnits());

        // Greedily attempts to attack so as to cause the most damage - i.e. to prevent its opponent getting a continent bonus (the largest possible).
        boolean attacked = false;
        for (Continent continent : newState.getUnconqueredContinents(agentPlayer, Comparator.comparing(Continent::getContinentBonus).reversed())) {
            for (Country opponentCountry : continent.getCountries(Comparator.comparing(Country::getUnits).reversed())) {
                if (opponentCountry.getOccupant().getId() == agentPlayer.getId()) {
                    continue;
                }
                for (Country agentCountry : opponentCountry.getNeighbours()) {
                    if (agentCountry.canAttack(opponentCountry)) {
                        newState.performAttack(agentCountry, opponentCountry);
                        attacked = true;
                        break;
                    }
                }
                if (attacked)
                    break;
            }
            if (attacked)
                break;
        }

        // Finalizing move
        agentPlayer.setLastTurnBonusUnits(attacked ? 2 : 0);
        newState.swapPlayers();
        newState.setDepth(newState.getDepth() + 1);
        return newState;
    }
}