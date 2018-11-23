package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import main.java.model.agent.Agent;
import main.java.model.agent.AggressiveAgent;
import main.java.model.agent.PassiveAgent;
import main.java.model.game.Game;
import main.java.model.game.GameState;
import main.java.view.GraphView;

public class ViewController {
    /** FXML Variables **/
    @FXML private StackPane root;

    /** Game Variables **/
    private Game game;
    private Agent agent1, agent2;
    private GameState curGameState;

    /** GraphStream Variables **/
    private GraphView graphView;

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
        this.graphView.addAttribute("ui.stylesheet", "url('" + this
                .getClass().getClassLoader().getResource("main/resources/view/css/graph.css") + "')");
        // Add Graph to View
        this.root.getChildren().add(this.graphView.getViewNode());
    }
}
