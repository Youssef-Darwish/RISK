package main.java.model;

import main.java.model.agents.AStarAgent;
import main.java.model.agents.GreedyAgent;
import main.java.model.agents.PassiveAgent;
import main.java.model.game.Game;
import main.java.model.game.GameState;
import main.java.model.heuristics.AStarHeuristic;
import main.java.model.heuristics.GreedyHeuristic;
import main.java.model.world.Country;

import java.util.*;

public class Main {

    public static void main(String[] args) {
//        // Both agents and filename are taken as input (probably using GUI)
        String fileName = "./risk_game.txt";

        GameState initGameState = new GameState(fileName);
//        System.out.println("Initial game state: ");
//        System.out.println("===============================================================================");
//        System.out.println(initGameState.toString());
        Game.getInstance().simulateGame(initGameState, new GreedyAgent(new GreedyHeuristic()), new PassiveAgent());

//        testClone(args);
//        testSuccessorStates(initGameState);
    }

    private static void testSuccessorStates(GameState gameState) {
        List<GameState> allLegalNextStates = gameState.getAllLegalNextStates();
        System.out.println("# of next states = " + allLegalNextStates.size());
        for (GameState gs: allLegalNextStates) {
            System.out.println("******************************************************");
            System.out.println(gs.toString());
            System.out.println("******************************************************");
        }
    }
    private static void testClone(String[] args) {
        String fileName = "./risk_game.txt";
        GameState originalGameState = new GameState(fileName);
        GameState clonedGameState = (GameState) originalGameState.clone();

        System.out.println("------ Before Change ------ ");
        System.out.println("------ Original -----");
        System.out.println(originalGameState.toString());
        System.out.println("------ Clone -----");
        System.out.println(clonedGameState.toString());

        originalGameState.getWorld().addCountry(4);
        originalGameState.getWorld().addCountry(5);
        originalGameState.getWorld().addContinent(2,
                Arrays.asList(5, 4, 5));
        originalGameState.swapPlayers();
        clonedGameState = (GameState) originalGameState.clone();
        System.out.println("------ After Change ------ ");
        System.out.println("------ Original -----");
        System.out.println(originalGameState.toString());
        System.out.println("------ Clone -----");
        System.out.println(clonedGameState.toString());

    }
}
