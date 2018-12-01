package main.java.model.agents;

import main.java.model.Agent;
import main.java.model.game.GameState;
import main.java.model.world.Continent;
import main.java.model.world.Country;
import main.java.model.world.Player;

public class PassiveAgent implements Agent {

    public PassiveAgent(){

    }

    @Override
    public GameState getNextState(GameState currentState) {
        GameState newState = (GameState) currentState.clone();
        Player agentPlayer = newState.getCurrentPlayer();

        // Placing units
        Country leastFortifiedCountry = agentPlayer.getLeastFortifiedCountry();;
        leastFortifiedCountry.setUnits(leastFortifiedCountry.getUnits() + agentPlayer.getTurnAdditionalUnits());

        // Finalizing move
        agentPlayer.setLastTurnBonusUnits(0);
        newState.swapPlayers();
        return newState;
    }
}