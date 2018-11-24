package main.java.model.agent;

import main.java.model.game.GameState;
import main.java.model.world.Continent;
import main.java.model.world.Country;
import main.java.model.world.Player;

public class PassiveAgent implements Agent {

    public PassiveAgent(){

    }

    @Override
    public GameState getNextState(GameState currentState) {
        GameState newState = new GameState(currentState);

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
        agentPlayer.setLastTurnBonusUnits(0);

        // switch players
        newState.setCurrentPlayer(currentState.getOpponentPlayer());
        newState.setOpponentPlayer(currentState.getCurrentPlayer());

        return newState;
    }
}