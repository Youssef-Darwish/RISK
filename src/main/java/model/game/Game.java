package main.java.model.game;

import main.java.model.Agent;
import main.java.model.SearchAgent;
import main.java.model.world.Country;

import java.util.ArrayList;
import java.util.List;

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
            System.out.print(c.getUnits() + "(" + c.getOccupant().getId() + ") ");
        }
        System.out.println();
        boolean turn = false;
        while (!currGameState.isFinalState()) {
            if (currGameState.getCurrentPlayer().getId() == currGameState.getWorld().getPlayerOne().getId()) {
                currGameState = playerOneAgent.getNextState(currGameState);
            } else {
                currGameState = playerTwoAgent.getNextState(currGameState);
            }
            System.out.print("State #" + cnt++ + ": ");
            for (Country c : currGameState.getWorld().getCountries()) {
                System.out.print(c.getUnits() + "(" + c.getOccupant().getId() + ") ");
            }
            System.out.println();
            turn = !turn;
        }
        System.out.println();
        System.out.println("Winner is player with Id: " + currGameState.getWinner().getId());
        System.out.println();
        System.out.println("===============================================================================");
        System.out.println("Final game state: ");
        System.out.println("===============================================================================");
        System.out.println(currGameState.toString());

        if (playerOneAgent instanceof SearchAgent) {
            reportPerformance(((SearchAgent) playerOneAgent).getTurnsToWin(), ((SearchAgent) playerOneAgent).getSearchExpansionSteps());
        } else if (playerTwoAgent instanceof SearchAgent) {
            reportPerformance(((SearchAgent) playerTwoAgent).getTurnsToWin(), ((SearchAgent) playerTwoAgent).getSearchExpansionSteps());
        }
    }

    public void reportPerformance(int turnsToWin, int searchExpansionSteps) {
        System.out.println("Turns taken by agent to win = " + turnsToWin);
        System.out.println("Number of expansion steps = " + searchExpansionSteps);
        System.out.println("Performance measure (for f = 1): " + (turnsToWin + searchExpansionSteps));
        System.out.println("Performance measure (for f = 100): " + (100 * turnsToWin + searchExpansionSteps));
        System.out.println("Performance measure (for f = 10000): " + (10000 * turnsToWin + searchExpansionSteps));
    }
}
