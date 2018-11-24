package main.java.model.agent;

import main.java.model.game.GameState;

public interface Heuristic {
    int eval(final GameState gameState);
}
