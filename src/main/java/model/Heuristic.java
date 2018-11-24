package main.java.model;

import main.java.model.game.GameState;

public interface Heuristic {
    int eval(final GameState gameState);
}
