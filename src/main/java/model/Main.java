package main.java.model;

import main.java.model.agents.AggressiveAgent;
import main.java.model.agents.PassiveAgent;
import main.java.model.game.Game;
import main.java.model.game.GameState;
import main.java.model.world.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        // Both agents and filename are taken as input (probably using GUI)
//        Agent passiveAgent = new PassiveAgent();
//        Agent aggressiveAgent = new AggressiveAgent();
//        String fileName = "./risk_game.txt";
//
//        GameState initGameState = new GameState(fileName);
//        Game.getInstance().play(initGameState, aggressiveAgent, passiveAgent);
        testClone(args);
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
        Player opponent = originalGameState.getOpponentPlayer();
        originalGameState.setOpponentPlayer(originalGameState.getCurrentPlayer());
        originalGameState.setCurrentPlayer(opponent);

        System.out.println("------ After Change ------ ");
        System.out.println("------ Original -----");
        System.out.println(originalGameState.toString());
        System.out.println("------ Clone -----");
        System.out.println(clonedGameState.toString());

    }

}
