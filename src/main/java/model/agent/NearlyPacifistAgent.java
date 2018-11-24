package main.java.model.agent;

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
        GameState newState = new GameState(currentState);

        // Placing additional units like a passive agent
        Player agentPlayer = currentState.getCurrentPlayer();
        Country leastFortifiedCountry = currentState.getLeastFortifiedCountry(agentPlayer);
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

        // Attacking a country(if possible) such that the agent loses the fewest possible # of units.
        boolean attacked = false;
        for (Country unconqueredCountry : currentState.getUnoccupiedCountries(agentPlayer)) {
            for (Country neighbour : unconqueredCountry.getNeighbours()) {
                if (neighbour.hasOccupant() && neighbour.getOccupant().equals(agentPlayer) && neighbour.canAttack(unconqueredCountry)) {
                    // Move units, assumption: when attacking, only leave 1 unit behind and attack with the rest
                    int unitsToMove = neighbour.getUnits() - unconqueredCountry.getUnits() - 1;
                    unconqueredCountry.setUnits(unitsToMove);
                    neighbour.setUnits(1);

                    // Modify country and player
                    unconqueredCountry.setOccupant(agentPlayer);
                    agentPlayer.addConqueredCountry(unconqueredCountry);

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
        newState.setCurrentPlayer(currentState.getOpponentPlayer());
        newState.setOpponentPlayer(currentState.getCurrentPlayer());

        return newState;
    }

}