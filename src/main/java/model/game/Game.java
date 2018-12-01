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

    public GameState playTurn(GameState currGameState, Agent playerOneAgent, Agent playerTwoAgent) {
        return currGameState.getCurrentPlayer().getId()
                == currGameState.getWorld().getPlayerOne().getId() ?
                playerOneAgent.getNextState(currGameState) :
                playerTwoAgent.getNextState(currGameState);
    }

    public void simulateGame(GameState initGameState, Agent playerOneAgent, Agent playerTwoAgent) {
        // Play takes both players' agents and plays the game starting from the given game state
        GameState currGameState = initGameState;
        int cnt = 0;
        System.out.print("State #" + cnt++ + ": ");
        for (Country c : currGameState.getWorld().getCountries()) {
            System.out.print(c.getUnits() + " ");
        }
        System.out.println();
        while (!currGameState.isFinalState()) {
            if (currGameState.getCurrentPlayer().equals(currGameState.getWorld().getPlayerOne())) {
                currGameState = playerOneAgent.getNextState(currGameState);
            } else {
                currGameState = playerTwoAgent.getNextState(currGameState);
            }
            System.out.print("State #" + cnt++ + ": ");
            for (Country c : currGameState.getWorld().getCountries()) {
                System.out.print(c.getUnits() + "(" + c.getOccupant().getId() + ") ");
            }
            System.out.println();
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
