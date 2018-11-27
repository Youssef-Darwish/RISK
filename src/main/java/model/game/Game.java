package main.java.model.game;

import main.java.model.Agent;

public class Game {
    private static Game instance;

    private Game() { }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void play(GameState initialState, Agent playerOneAgent, Agent playerTwoAgent) {
        // Play takes both players' agents and plays the game starting from the given game state
        GameState currGameState = initialState;
        while (!currGameState.isFinalState()) {
            if (currGameState.getCurrentPlayer().equals(currGameState.getWorld().getPlayerOne())) {
                currGameState = playerOneAgent.getNextState(currGameState);
            } else {
                currGameState = playerTwoAgent.getNextState(currGameState);
            }
        }
        System.out.println("Winner is player with Id: " + currGameState.getWinner().getId());
    }
}
