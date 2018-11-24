package main.java.model.agents;

import main.java.model.Heuristic;
import main.java.model.SearchAgent;
import main.java.model.game.GameState;
import main.java.model.world.Player;

public class GreedyAgent extends SearchAgent {
    public GreedyAgent(Heuristic heuristic) {
        super(heuristic);
    }

    @Override
    public GameState getNextState(GameState currentState) {
        Player agentPlayer = currentState.getCurrentPlayer();

        return null;
    }
}
