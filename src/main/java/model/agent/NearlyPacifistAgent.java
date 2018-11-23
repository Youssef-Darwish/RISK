package main.java.model.agent;

import main.java.model.game.GameState;
import main.java.model.world.Country;
import main.java.model.world.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NearlyPacifistAgent implements Agent {

    private GameState newState ;
    private Player agentPlayer;

    //TODO Agent : singleton ?

    public NearlyPacifistAgent(){

    }

    @Override
    public GameState getNextState(GameState currentState) {

        //TODO : replace new with copy of GameState

        newState = currentState.copy();
        agentPlayer = currentState.getCurrentPlayer();
        Country country = agentPlayer.getWeakestCountry();
        country.setUnits(country.getUnits() + agentPlayer.getTurnBonus());

        List<Country> opponentsCountries = currentState.getOpponentPlayer().getConqueredCountries();
        Collections.sort(opponentsCountries);

        boolean attacked = false;
        for (Country weakestCountry : opponentsCountries){
            for (Country neighbourCountry : weakestCountry.getNeighbours()){
                if (neighbourCountry.canAttack(weakestCountry)){
                    // update country units + update occupant + update conqueredContinents + updateBonusPoints
                    int diff = neighbourCountry.getUnits()-weakestCountry.getUnits();
                    neighbourCountry.setUnits(1);   //leave 1 unit in the attacking country
                    weakestCountry.setUnits(diff-1);     // move the rest to the attacked country
                    weakestCountry.setOccupant(agentPlayer); //update occupant

                    //TODO : update conquered continents
                    attacked = true;
                    break;
                }
            }
        }

        if (attacked){
            agentPlayer.setlastTurnBonusUnits(2);
        }else {
            agentPlayer.setlastTurnBonusUnits(0);
        }

        // switch players
        newState.setCurrentPlayer(currentState.getOpponentPlayer());
        newState.setOpponentPlayer(currentState.getCurrentPlayer());

        return newState;
    }

}
