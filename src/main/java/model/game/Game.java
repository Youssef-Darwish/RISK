package main.java.model.game;

import main.java.model.Agent;
import main.java.model.world.Country;

public class Game {
    private static Game instance;

    private Game() { }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void play(GameState initialState, Agent playerOneAgent, Agent playerTwoAgent) {
        // Play takes both players' agents and plays the game starting from the given game state
        GameState currGameState = initialState;
        int cnt = 0;
        while (!currGameState.isFinalState()) {
            System.out.print("State #" + cnt++ + ": ");
            for (Country c : currGameState.getWorld().getCountries()) {
                System.out.print(c.getUnits() + "(" + c.getOccupant().getId() + ")" + " ");
            }
            System.out.println();
            if (currGameState.getCurrentPlayer().equals(currGameState.getWorld().getPlayerOne())) {
                currGameState = playerOneAgent.getNextState(currGameState);
            } else {
                currGameState = playerTwoAgent.getNextState(currGameState);
            }
        }
        System.out.print("State #" + cnt++ + ": ");
        for (Country c : currGameState.getWorld().getCountries()) {
            System.out.print(c.getUnits() + "(" + c.getOccupant().getId() + ")" + " ");
        }
        System.out.println();
        System.out.println("Winner is player with Id: " + currGameState.getWinner().getId());
        System.out.println();
        System.out.println("===============================================================================");
        System.out.println("Final game state: ");
        System.out.println("===============================================================================");
        System.out.println(currGameState.toString());
    }
}
