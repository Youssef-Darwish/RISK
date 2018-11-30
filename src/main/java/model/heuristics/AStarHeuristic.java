package main.java.model.heuristics;

import main.java.model.Heuristic;
import main.java.model.game.GameState;

public class AStarHeuristic implements Heuristic {
    @Override
    public int eval(GameState gameState) {
        // f = g + h
        return gameState.getDepth() + new GreedyHeuristic().eval(gameState);
    }
}
