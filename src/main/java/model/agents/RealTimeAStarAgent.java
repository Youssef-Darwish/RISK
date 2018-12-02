package main.java.model.agents;

import main.java.model.Heuristic;
import main.java.model.SearchAgent;
import main.java.model.game.GameState;

import java.util.*;

public class RealTimeAStarAgent extends SearchAgent {
    private int depthLimit;
    public RealTimeAStarAgent(Heuristic heuristic, int depthLimit) {
        super(heuristic);
        this.depthLimit = depthLimit;
    }

    @Override
    public GameState getNextState(GameState currentState) {
        Queue<GameState> frontier = new PriorityQueue<>(Comparator.comparing(getHeuristic()::eval));
        Map<GameState, Integer> heuristicMap = new HashMap<>();
        Map<GameState, GameState> parentsMap = new HashMap<>();
        PassiveAgent passiveAgent = new PassiveAgent();
        currentState.setSearchDepth(0);

        frontier.add(currentState);
        heuristicMap.put(currentState, getHeuristic().eval(currentState));
        parentsMap.put(currentState, null);

        Set<GameState> explored = new HashSet<>();
        while (!frontier.isEmpty()) {
            GameState currState = frontier.poll();
            explored.add(currState);
            heuristicMap.remove(currState);

            if (currState.isFinalState() || currentState.getSearchDepth() >= this.depthLimit) {
                this.turnsToWin++;
                return reconstructPath(parentsMap, currState);
            }
            this.searchExpansionSteps++;
            for (GameState passiveNeighbourState : currState.getAllLegalNextStates()) {
                GameState neighbourState = passiveNeighbourState.isFinalState() ? passiveNeighbourState : passiveAgent.getNextState(passiveNeighbourState);

                if (explored.contains(neighbourState)) {
                    continue;
                }

                int neighbourStateHValue = getHeuristic().eval(neighbourState);
                if (!frontier.contains(neighbourState)) {
                    frontier.add(neighbourState);
                    heuristicMap.put(neighbourState, neighbourStateHValue);
                    parentsMap.put(neighbourState, currState);
                } else if (neighbourStateHValue < heuristicMap.get(neighbourState)) {
                    heuristicMap.remove(neighbourState);
                    heuristicMap.put(neighbourState, neighbourStateHValue);
                    frontier.remove(neighbourState);
                    frontier.add(neighbourState);
                    parentsMap.remove(neighbourState);
                    parentsMap.put(neighbourState, currState);
                }
            }
        }
        return null;
    }

    private GameState reconstructPath(Map<GameState,GameState> parentsMap, GameState currState) {
        List<GameState> path = new ArrayList<>();
        while (currState != null) {
            path.add(currState);
            currState = parentsMap.get(currState);
        }
        Collections.reverse(path);
        if (path.size() == 1)
            return path.get(0);
        return path.get(1);
    }
}
