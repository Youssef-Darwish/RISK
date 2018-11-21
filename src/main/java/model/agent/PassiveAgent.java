package main.java.model.agent;

import main.java.model.game.GameState;
import main.java.model.world.Country;
import main.java.model.world.Player;

public class PassiveAgent implements Agent {

    private GameState newState ;
    private Player agentPlayer;

    //TODO Agent : singleton ?

    // move the needed function in Player Class to state class ? and call them by ID ?
    public PassiveAgent(){

    }

    @Override
    public GameState getNextState(GameState currentState) {
        newState = currentState.copy();

        agentPlayer = currentState.getCurrentPlayer();
        Country country = agentPlayer.getWeakestCountry();
        country.setUnits(country.getUnits() + agentPlayer.getTurnBonus());
        agentPlayer.setlastTurnBonusUnits(0);

        // switch players
        newState.setCurrentPlayer(currentState.getOpponentPlayer());
        newState.setOpponentPlayer(currentState.getCurrentPlayer());

        // update bonus points
        return newState;
    }
}
