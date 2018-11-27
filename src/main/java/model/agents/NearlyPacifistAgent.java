package main.java.model.agents;

import main.java.model.Agent;
import main.java.model.game.GameState;
import main.java.model.world.Continent;
import main.java.model.world.Country;
import main.java.model.world.Player;

import java.util.Collections;
import java.util.List;

public class NearlyPacifistAgent implements Agent {

    public NearlyPacifistAgent(){

    }

    @Override
    public GameState getNextState(GameState currentState) {
        GameState newState = (GameState) currentState.clone();

        // Placing additional units like a passive agent
        Player agentPlayer = newState.getCurrentPlayer();
        Player opponentPlayer = newState.getOpponentPlayer();

        Country leastFortifiedCountry = newState.getLeastFortifiedCountry(agentPlayer);
        leastFortifiedCountry.setUnits(leastFortifiedCountry.getUnits() + agentPlayer.getTurnAdditionalUnits());
        if (!leastFortifiedCountry.hasOccupant()) {
            leastFortifiedCountry.setOccupant(agentPlayer);
            agentPlayer.addConqueredCountry(leastFortifiedCountry);

            // Check if this country formed a continent
            Continent continent = leastFortifiedCountry.getContinent();
            if (continent.isConquered(agentPlayer)) {
                agentPlayer.addConqueredContinent(continent);
            }
        }

        // Attacking a country (if possible) such that the agent loses the fewest possible # of units.
        boolean attacked = false;
        for (Country unconqueredCountry : newState.getUnoccupiedCountries(agentPlayer)) {
            for (Country neighbour : unconqueredCountry.getNeighbours()) {
                if (neighbour.hasOccupant() && neighbour.getOccupant().equals(agentPlayer) && neighbour.canAttack(unconqueredCountry)) {
                    // Move units, assumption: when attacking, only leave 1 unit behind and attack with the rest
                    int unitsToMove = neighbour.getUnits() - unconqueredCountry.getUnits() - 1;
                    unconqueredCountry.setUnits(unitsToMove);
                    neighbour.setUnits(1);

                    // Modify country and player
                    unconqueredCountry.setOccupant(agentPlayer);
                    agentPlayer.addConqueredCountry(unconqueredCountry);
                    opponentPlayer.removeConqueredCountry(unconqueredCountry);

                    // Check if attack removed a continent from opponent
                    if (opponentPlayer.getConqueredContinents().contains(unconqueredCountry)) {
                        opponentPlayer.removeConqueredContinent(unconqueredCountry.getContinent());
                    }

                    // Check if continent is now conquered
                    Continent continent = unconqueredCountry.getContinent();
                    if (continent.isConquered(agentPlayer)) {
                        agentPlayer.addConqueredContinent(continent);
                    }
                    attacked = true;
                    break;
                }
            }
            if (attacked)
                break;
        }

        // Update Bonus Units for next turn
        agentPlayer.setLastTurnBonusUnits(attacked ? 2 : 0);

        // switch players
        newState.swapPlayers();
        return newState;
    }

}