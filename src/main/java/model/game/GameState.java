package main.java.model.game;


import main.java.model.world.Player;
import main.java.model.world.WorldMap;

import java.util.List;

public class GameState {


    private Player currentPlayer, opponentPlayer;
    private WorldMap world;

    //TODO : game constructor , copy state , what else ?
    public GameState(){

    }

    public WorldMap getWorld() {
        return world;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOpponentPlayer(){
        return opponentPlayer;
    }

    public void setWorld(WorldMap world) {
        this.world = world;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setOpponentPlayer(Player player){
        opponentPlayer = player;
    }

    public List<GameState> getNextStates(){
        return null;
    }

    public List<GameState> getAllSuccessors() {
        return null;
    }


    public GameState copyState(){
        return new GameState();
    }

}
