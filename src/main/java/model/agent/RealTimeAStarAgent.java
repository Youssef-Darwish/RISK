package main.java.model.agent;

import main.java.model.game.GameState;

public class RealTimeAStarAgent extends SearchAgent {
    public RealTimeAStarAgent(Heuristic heuristic) {
        super(heuristic);
    }

    @Override
    public GameState getNextState(GameState currentState) {
        return null;
    }
}
