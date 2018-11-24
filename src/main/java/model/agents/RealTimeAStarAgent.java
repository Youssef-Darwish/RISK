package main.java.model.agents;

import main.java.model.Heuristic;
import main.java.model.SearchAgent;
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
