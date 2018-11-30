package main.java.model.agents;

import main.java.model.Heuristic;
import main.java.model.SearchAgent;
import main.java.model.game.GameState;

import java.util.*;

public class AStarAgent extends SearchAgent {
    private List<GameState> pathStates;
    public AStarAgent(Heuristic heuristic, GameState initState) {
        super(heuristic); // heuristic here should be g + h
        this.pathStates = aStarSearch(initState, heuristic);
    }

    private List<GameState> aStarSearch(GameState initState, Heuristic heuristic) {
        List<GameState> pathStates = new LinkedList<>();
        Queue<GameState> frontier = new PriorityQueue<>(Comparator.comparing(heuristic::eval));
        frontier.add(initState);
        Set<GameState> explored = new HashSet<>();
        while (!frontier.isEmpty()) {
            GameState currState = frontier.poll();
            explored.add(currState);

            if (currState.isFinalState()) {
                pathStates.add(currState);
                break;
            }

            for (GameState neighbour : currState.getAllLegalNextStates()) {
                PassiveAgent pa = new PassiveAgent();
                GameState gs = pa.getNextState(neighbour);
                if (!frontier.contains(gs) && !explored.contains(gs)) {
                    frontier.add(gs);
                } else if (frontier.contains(gs)) {
                    frontier.add(gs); // check this??
                }
            }
        }
        // NOTE: CHECK LAST STATE IN THE LIST TO KNOW IF ASTAR REACHED A SOLUTION OR NOT
        return pathStates;
    }

    @Override
    public GameState getNextState(GameState currentState) {
        return null;
    }
}
