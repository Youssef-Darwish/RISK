package main.java.model.agents;

import main.java.model.Agent;
import main.java.model.Heuristic;
import main.java.model.SearchAgent;
import main.java.model.game.GameState;
import main.java.model.world.Continent;
import main.java.model.world.Country;
import main.java.model.world.Player;

public class GreedyAgent extends SearchAgent {
    public GreedyAgent(Heuristic heuristic) {
        super(heuristic);
    }

    @Override
    public GameState getNextState(GameState currentState) {
        GameState newState = null;
        Agent passiveAgent = new PassiveAgent();
        int best_h = Integer.MAX_VALUE;
        for (GameState passiveState : currentState.getAllLegalNextStates()) {
            GameState nextState = passiveState.isFinalState() ? passiveState : passiveAgent.getNextState(passiveState);
            int curr_h = getHeuristic().eval(nextState);
            if (curr_h < best_h) {
                best_h = curr_h;
                newState = nextState;
            }
        }
        return newState;
    }
}
