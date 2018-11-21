package main.java.model.agent;

import main.java.model.game.GameState;
import main.java.model.world.Country;
import main.java.model.world.Player;

public class PassiveAgent implements Agent {

    private GameState newState ;
    private Player agentPlayer;

    // move the needed function in Player Class to state class ? and call them by ID ?
    public PassiveAgent(){
       newState = new GameState();
    }

    @Override
    public GameState getNextState(GameState currentState) {

        agentPlayer = currentState.getCurrentPlayer();
        Country country = agentPlayer.getWeakestCountry();
        country.setUnits(country.getUnits() + agentPlayer.getlastTurnBonusUnits());
        agentPlayer.setlastTurnBonusUnits(agentPlayer.getTurnBonus()); // check logic

        // switch players
        newState.setCurrentPlayer(currentState.getOpponentPlayer());
        newState.setOpponentPlayer(currentState.getCurrentPlayer());

        // update bonus points

        // make new state : add copy function to GameState class

        return newState;
    }
}
