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

        // greedily attempts to attack so as to cause the most damage - i.e. to prevent its opponent getting a continent bonus (the largest possible).
        boolean attacked = false;

        for (Continent continent : newState.getUnconqueredContinents(agentPlayer, Comparator.comparing(Continent::getContinentBonus).reversed())) {
            for (Country country : continent.getUnconqueredCountries(agentPlayer, Comparator.comparing(Country::getUnits).reversed())) {
                if (country.hasOccupant() && country.getOccupant().equals(agentPlayer)) {
                    continue;
                }
                for (Country neighbour : country.getNeighbours()) {
                    if (neighbour.hasOccupant() && neighbour.getOccupant().equals(agentPlayer) && neighbour.canAttack(country)) {
                        // Move units, assumption: when attacking, only leave 1 unit behind and attack with the rest
                        int unitsToMove = neighbour.getUnits() - country.getUnits() - 1;
                        country.setUnits(unitsToMove);
                        neighbour.setUnits(1);

                        // Modify country and player
                        country.setOccupant(agentPlayer);
                        agentPlayer.addConqueredCountry(country);
                        opponentPlayer.removeConqueredCountry(country);

                        // Check if attack removed a continent from opponent
                        if (opponentPlayer.getConqueredContinents().contains(continent)) {
                            opponentPlayer.removeConqueredContinent(continent);
                        }

                        // Check if continent is now conquered
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
            if (attacked)
                break;
        }

        agentPlayer.setLastTurnBonusUnits(attacked ? 2 : 0);

        // switch players
        newState.swapPlayers();
        return newState;
    }
}