package main.java.model;

import main.java.model.agent.Agent;
import main.java.model.agent.AggressiveAgent;
import main.java.model.agent.PassiveAgent;
import main.java.model.game.Game;
import main.java.model.game.GameState;

public class Main {

    public static void main(String[] args) {
        // Both agents and filename are taken as input (probably using GUI)
        Agent passiveAgent = new PassiveAgent();
        Agent aggressiveAgent = new AggressiveAgent();
        String fileName = "./risk_game.txt";

        GameState initGameState = new GameState(fileName);
        Game.getInstance().play(initGameState, aggressiveAgent, passiveAgent);
    }

}
