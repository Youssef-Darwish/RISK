package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import main.java.model.Agent;
import main.java.model.agents.AggressiveAgent;
import main.java.model.agents.GreedyAgent;
import main.java.model.agents.HumanAgent;
import main.java.model.agents.PassiveAgent;
import main.java.model.game.Game;
import main.java.model.game.GameState;
import main.java.view.GraphView;
import org.graphstream.ui.view.ViewerListener;

import java.io.IOException;

public class ViewController {
    /** FXML Variables **/
    @FXML private StackPane root;
    @FXML private BorderPane gameOverPane;
    @FXML private JFXButton attackButton;
    @FXML private JFXButton nextTurnButton;
    @FXML private ImageView curTurnIcon;

    /** Game Variables **/
    private Game game;
    private Agent player1, player2;
    private GameState curGameState;

    /** GraphStream Variables **/
    private GraphView graphView;

    /** Constant Variables (assets) **/
    private static final String STYLE_SHEET_PATH = "main/resources/view/css/graph.css";
    private static final String BACKGROUND_PATH = "src/main/resources/view/assets/world_1200_800.png";
    private static final String PLAYER_1_ICON = "main/resources/view/assets/circle_1.png";
    private static final String PLAYER_2_ICON = "main/resources/view/assets/circle_2.png";

    /** Testing Variables **/
    private final static String FILE_NAME = "./risk_game.txt";

    @FXML
    public void initialize() {
        this.initGame();
        this.initGraphStream();
        this.initControls();
    }

    private void initGame() {
        this.game = Game.getInstance();
        this.player1 = new PassiveAgent();
        this.player2 = new PassiveAgent();
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

    private void initControls() {
        boolean human = this.player1 instanceof HumanAgent;
        this.attackButton.setDisable(!human);
        this.nextTurnButton.setDisable(human);
    }

    @FXML
    public void nextTurn() {
        this.game.play(this.curGameState, player1, player2);
        this.toggleTurns();
        if (this.curGameState.isFinalState()) {
            this.nextTurnButton.setDisable(true);
//            this.drawGameOver();
        }
    }

    private void toggleTurns() {
        this.curTurnIcon.setImage(new Image(
                this.curGameState.getCurrentPlayer().equals(this.curGameState.getWorld().getPlayerOne()) ?
                        PLAYER_1_ICON : PLAYER_2_ICON));
    }
}
