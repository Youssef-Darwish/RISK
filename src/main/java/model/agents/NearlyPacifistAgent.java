package main.java.model.agents;

import main.java.model.Agent;
import main.java.model.game.GameState;
import main.java.model.world.Continent;
import main.java.model.world.Country;
import main.java.model.world.Player;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NearlyPacifistAgent implements Agent {

    public NearlyPacifistAgent(){

    }

    @Override
    public GameState getNextState(GameState currentState) {
        GameState newState = (GameState) currentState.clone();
        Player agentPlayer = newState.getCurrentPlayer();
        Player opponentPlayer = newState.getOpponentPlayer();

        // Placing additional units like a passive agent
        Country leastFortifiedCountry = agentPlayer.getLeastFortifiedCountry();;
        leastFortifiedCountry.setUnits(leastFortifiedCountry.getUnits() + agentPlayer.getTurnAdditionalUnits());

        // Attacking a country (if possible) such that the agent loses the fewest possible # of units.
        boolean attacked = false;
        for (Country opponentCountry : opponentPlayer.getConqueredCountries(Comparator.comparing(Country::getUnits))) {
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

        // Finalizing move
        agentPlayer.setLastTurnBonusUnits(attacked ? 2 : 0);
        newState.swapPlayers();
        newState.setDepth(newState.getDepth() + 1);
        return newState;
    }

}