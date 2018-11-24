package main.java.model.agent;

import main.java.model.game.GameState;

public class AStarAgent extends SearchAgent {
    public AStarAgent(Heuristic heuristic) {
        super(heuristic);
    }

    @Override
    public GameState getNextState(GameState currentState) {
        return null;
    }
}
