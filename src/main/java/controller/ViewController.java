package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import main.java.model.Agent;
import main.java.model.agents.AggressiveAgent;
import main.java.model.agents.PassiveAgent;
import main.java.model.game.Game;
import main.java.model.game.GameState;
import main.java.view.GraphView;
import org.graphstream.ui.view.ViewerListener;

import java.io.IOException;

public class ViewController {
    /** FXML Variables **/
    @FXML private StackPane root;

    /** Game Variables **/
    private Game game;
    private Agent agent1, agent2;
    private GameState curGameState;

    /** GraphStream Variables **/
    private GraphView graphView;

    /** Constant Variables **/
    private static final String STYLE_SHEET_PATH = "main/resources/view/css/graph.css";
    private static final String BACKGROUND_PATH = "src/main/resources/view/assets/world_1200_800.png";

    /** Testing Variables **/
    private final static String FILE_NAME = "./risk_game.txt";

    @FXML
    public void initialize() {
        this.initGame();
        this.initGraphStream();
    }

    private void initGame() {
        this.game = Game.getInstance();
        this.agent1 = new AggressiveAgent();
        this.agent2 = new PassiveAgent();
        this.curGameState = new GameState(FILE_NAME);

    }

    private void initGraphStream() {
        // Init Graph
        this.graphView = GraphView.fromGameState(this.curGameState);
        this.graphView.addStyleSheet(STYLE_SHEET_PATH);
        // Add Graph to View
        this.root.getChildren().add(this.graphView.newViewNode());

        // Add background
        try {
            this.graphView.setNodeBackground(BACKGROUND_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Add View Pipe Listener
        this.graphView.addViewerPipeEventListener(new ViewerListener() {
            @Override
            public void viewClosed(String viewName) {
                // Do nothing
            }

            @Override
            public void buttonPushed(String id) {
                // Do nothing
            }

            @Override
            public void buttonReleased(String id) {
                System.out.println("Button released on node: " + id);
            }
        });
    }
}
