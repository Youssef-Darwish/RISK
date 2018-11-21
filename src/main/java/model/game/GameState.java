package main.java.model.game;


import main.java.model.world.Player;
import main.java.model.world.WorldMap;

import java.util.List;

public class GameState {


    private Player currentPlayer, opponentPlayer;
    private WorldMap world;

    public GameState(){

    }

    public WorldMap getWorld() {
        return world;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    public void setWorld(WorldMap world) {
        this.world = world;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    public List<GameState> getNextStates(){
        return null;
    }

    public List<GameState> getAllSuccessors() {
        return null;
    }



}
