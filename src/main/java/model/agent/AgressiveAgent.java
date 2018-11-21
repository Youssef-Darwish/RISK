package main.java.model.agent;

import main.java.model.game.GameState;
import main.java.model.world.Continent;
import main.java.model.world.Country;
import main.java.model.world.Player;

import java.util.ArrayList;
import java.util.List;

public class AgressiveAgent implements Agent {
    private GameState newState ;
    private Player agentPlayer;

    // move the needed function in Player Class to state class ? and call them by ID ?
    public AgressiveAgent(){

        newState = new GameState();
    }

    @Override
    public GameState getNextState(GameState currentState) {

        /*
        3. An aggressive agent, that always places all its bonus armies on the vertex with the most
           armies, and greedily attempts to attack so as to cause the most damage - i.e. to prevent
           its opponent getting a continent bonus (the largest possible).
         */

        //Steps for aggressive Agent : or this is the nearly pacifist ? :'D

        //  0-add units on the most fortified country
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

        //step 0

        Country country = agentPlayer.getMostFortifiedCountry();
        country.setUnits(country.getUnits()+agentPlayer.getlastTurnBonusUnits());
        List<Continent> continents = agentPlayer.getConqueredContinents();
        for (Continent continent : continents){
            // get sorted list of countries
            // loop , can attack ? attack
        }


        //update bonus units



        // make new state : add copy function to GameState class

        return newState;
    }
}
