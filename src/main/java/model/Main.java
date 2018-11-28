package main.java.model;

import main.java.model.agents.AggressiveAgent;
import main.java.model.agents.NearlyPacifistAgent;
import main.java.model.agents.PassiveAgent;
import main.java.model.game.Game;
import main.java.model.game.GameState;
import main.java.model.world.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
//        // Both agents and filename are taken as input (probably using GUI)
        String fileName = "./risk_game.txt";

        GameState initGameState = new GameState(fileName);
        System.out.println("Initial game state: ");
        System.out.println("===============================================================================");
        System.out.println(initGameState.toString());
        Game.getInstance().play(initGameState, new PassiveAgent(), new NearlyPacifistAgent());
//        testClone(args);
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
