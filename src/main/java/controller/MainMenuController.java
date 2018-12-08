package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import main.java.app.Main;
import main.java.model.Agent;
import main.java.model.agents.*;
import main.java.model.game.GameState;
import main.java.model.heuristics.AStarHeuristic;
import main.java.model.heuristics.GreedyHeuristic;

import java.io.IOException;

public class MainMenuController {
    @FXML private StackPane root;

    private static final String FILE_NAME = "./risk_game.txt";
    private static final int REALTIME_ASTAR_DEPTH = 1;

    public void choosePassive(ActionEvent actionEvent) {
        openGame(new PassiveAgent(), new GameState(FILE_NAME));
    }

    public void chooseHuman(ActionEvent actionEvent) {
        openGame(new HumanAgent(), new GameState(FILE_NAME));
    }

    public void chooseAggressive(ActionEvent actionEvent) {
        openGame(new AggressiveAgent(), new GameState(FILE_NAME));
    }

    public void choosePacifist(ActionEvent actionEvent) {
        openGame(new NearlyPacifistAgent(), new GameState(FILE_NAME));
    }

    public void chooseGreedy(ActionEvent actionEvent) {
        openGame(new GreedyAgent(new GreedyHeuristic()), new GameState(FILE_NAME));
    }

    public void chooseAStar(ActionEvent actionEvent) {
        GameState gameState = new GameState(FILE_NAME);
        openGame(new AStarAgent(new AStarHeuristic(), gameState), gameState);
    }

    public void chooseRealtimeAStar(ActionEvent actionEvent) {
        GameState gameState = new GameState(FILE_NAME);
        openGame(new RealTimeAStarAgent(new AStarHeuristic(), REALTIME_ASTAR_DEPTH), gameState);
    }

    private void openGame(Agent agent, GameState initGameState) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/resources/view/sample.fxml"));
        try {
            Pane pane = loader.load();
            ViewController controller = loader.getController();
            controller.initGame(agent, initGameState);

            root.getChildren().setAll(pane);
            Main.setLoader(loader);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
