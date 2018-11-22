package main.java.model.agent;

import main.java.model.game.GameState;
import main.java.model.world.Continent;
import main.java.model.world.Country;
import main.java.model.world.Player;

import java.util.ArrayList;
import java.util.List;

public class AggressiveAgent implements Agent {
    private GameState newState ;
    private Player agentPlayer;

    // move the needed function in Player Class to state class ? and call them by ID ?
    public AggressiveAgent(){
    }

    @Override
    public GameState getNextState(GameState currentState) {

        //Steps for aggressive Agent
        //  0-add units on the most fortified country  -> OK
        //  1-get conquered continents                 -> OK
        //  2-for each continent:
        //  3-get most fortified country
        //  4-if (canAttack) --> attack
        //  5-else : got to 3
        //  6-if opponentPlayer.getConqueredContinents.size==0
        //  7-get weakest country                       -->some issues in loop
        //  8-if (canAttack) -->attack
        //  9-else : go to 7

        newState = new GameState(currentState);
        agentPlayer = currentState.getCurrentPlayer();
        Country country = agentPlayer.getMostFortifiedCountry();
        country.setUnits(country.getUnits()+agentPlayer.getTurnBonus());
        List<Continent> continents = agentPlayer.getConqueredContinents();


        // if the opponent has conquered continents
        if (continents.size()!=0){

            for (Continent continent : continents){

                Country strongest = continent.getMostFortifiedCountry();
                List<Country> neighbours = strongest.getNeighbours();

                for (Country neighbourCountry : neighbours){
                    if (neighbourCountry.canAttack(strongest)){
                        // update country units + update occupant + update conqueredContinents + updateBonusPoints
                        int diff = neighbourCountry.getUnits()-strongest.getUnits();
                        neighbourCountry.setUnits(1);   //leave 1 unit in the attacking country
                        strongest.setUnits(diff-1);     // move the rest to the attacked country
                        strongest.setOccupant(agentPlayer); //update occupant

                        // update conquered continents
                        agentPlayer.setLastTurnBonusUnits(2);


                    }
                }
                // loop , can attack ? attack
            }
        }else {

            //repeated code : pack it in one function
            //we may return the nearest conquered as list
            Continent continent = agentPlayer.getNearestConqueredContinent();

            Country strongest = continent.getMostFortifiedCountry();
            List<Country> neighbours = strongest.getNeighbours();

            for (Country neighbourCountry : neighbours){
                if (neighbourCountry.canAttack(strongest)){
                    // update country units + update occupant + update conqueredContinents + updateBonusPoints
                    int diff = neighbourCountry.getUnits()-strongest.getUnits();
                    neighbourCountry.setUnits(1);   //leave 1 unit in the attacking country
                    strongest.setUnits(diff-1);     // move the rest to the attacked country
                    strongest.setOccupant(agentPlayer); //update occupant

                    // update conquered continents
                    agentPlayer.setLastTurnBonusUnits(2);

                    //TODO : Incomplete Logic

                }
            }
        }

        return newState;
    }
}