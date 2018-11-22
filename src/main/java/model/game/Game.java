package main.java.model.game;

import main.java.model.agent.Agent;

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
            // Proceed with the game
        }
    }
}
