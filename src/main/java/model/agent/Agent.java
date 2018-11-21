package main.java.model.agent;

import main.java.model.game.GameState;

public interface Agent {
    public GameState getNextState(GameState currentState);
}
