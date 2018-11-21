package main.java.model.agent;

import main.java.model.game.GameState;
import main.java.model.world.Country;
import main.java.model.world.Player;

public class AgressiveAgent implements Agent {
    private GameState newState ;
    private Player agentPlayer;

    // move the needed function in Player Class to state class ? and call them by ID ?
    public AgressiveAgent(){

        newState = new GameState();
    }

    @Override
    public GameState getNextState(GameState currentState) {

        //Steps for aggressive Agent

        //  1-get conquered continents
        //  2-for each continent:
        //  3-get least fortified country
        //  4-if (canAttack) --> attack
        //  5-else : got to 3
        //  6-if opponentPlayer.getConqueredContinents.size==0
        //  7-get weakest country
        //  8-if (canAttack) -->attack
        //  9-else : go to 7




        agentPlayer = currentState.getCurrentPlayer();
        Country country = agentPlayer.getWeakestCountry();
        country.setUnits(country.getUnits() + agentPlayer.getlastTurnBonusUnits());
        agentPlayer.setlastTurnBonusUnits(agentPlayer.getTurnBonus()); // check logic

        // switch players
        newState.setCurrentPlayer(currentState.getOpponentPlayer());
        newState.setOpponentPlayer(currentState.getCurrentPlayer());

        // make new state : add copy function to GameState class

        return newState;
    }
}
