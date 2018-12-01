package main.java.model.heuristics;

import main.java.model.Heuristic;
import main.java.model.game.GameState;

public class AStarHeuristic implements Heuristic {
    @Override
    public int eval(GameState gameState) {
        // f = g + h
        if (!gameState.isFinalState())
            return gameState.getDepth() + new GreedyHeuristic().eval(gameState);
        return gameState.getDepth();
    }
}
