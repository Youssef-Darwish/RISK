package main.java.model.agents;

import main.java.model.Heuristic;
import main.java.model.SearchAgent;
import main.java.model.game.GameState;

import java.util.*;

public class AStarAgent extends SearchAgent {
    private List<GameState> pathStates;
    public AStarAgent(Heuristic heuristic, GameState initState) {
        super(heuristic);
        this.pathStates = aStarSearch(initState, heuristic);
    }

    private List<GameState> aStarSearch(GameState initState, Heuristic heuristic) {
        // NOTE: CHECK LAST STATE IN THE LIST TO KNOW IF ASTAR REACHED A SOLUTION OR NOT
        List<GameState> pathStates = new LinkedList<>();
        Queue<GameState> frontier = new PriorityQueue<>(Comparator.comparing(heuristic::eval));
        frontier.add(initState);
        Set<GameState> explored = new HashSet<>();
        while (!frontier.isEmpty()) {
            GameState currState = frontier.poll();
            explored.add(currState);

            if (currState.isFinalState()) {
                // TODO: return reconstruct path
                pathStates.add(currState);
                break;
            }

            for (GameState neighbour : currState.getAllLegalNextStates()) {
                PassiveAgent passiveAgent = new PassiveAgent();
                GameState gameState = passiveAgent.getNextState(neighbour);

                if (!frontier.contains(gameState) && !explored.contains(gameState)) {
                    frontier.add(gameState);
                } else if (frontier.contains(gameState)) {
                    frontier.add(gameState); // here i should only insert the gameState with less heuristic value.
                }
            }
        }
        return pathStates;
    }

    @Override
    public GameState getNextState(GameState currentState) {
        return null;
    }
}
