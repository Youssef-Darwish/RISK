package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import main.java.model.Agent;
import main.java.model.agents.HumanAgent;
import main.java.model.agents.PassiveAgent;
import main.java.model.game.Game;
import main.java.model.game.GameState;
import main.java.model.world.Player;
import main.java.view.GraphView;
import org.graphstream.ui.view.ViewerListener;

import java.io.IOException;

public class ViewController {
    /** FXML Variables **/
    @FXML private StackPane root;
    @FXML private BorderPane gameOverPane;
    @FXML private JFXButton humanButton;
    @FXML private JFXButton nextTurnButton;
    @FXML private ImageView curTurnIcon;
    @FXML private ImageView winningPlayerIcon;
    @FXML private Label troopsCount1, troopsCount2;
    @FXML private Label conqCountriesCount1, conqCountriesCount2;
    @FXML private Label conqContinentsCount1, conqContinentsCount2;

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

    /** HumanAgent state **/
    enum HumanState {
        IDLE,
        REINFORCING,
        IDLE_ATTACKING,
        SELECTING,
        ATTACKING,
        FINISHED
    }
    HumanState curHumanState = HumanState.IDLE_ATTACKING;
    int curCountrySelected = -1;

    @FXML
    public void initialize() {
        this.initGame();
        this.initGraphStream();
        this.initControls();

        this.gameOverPane.toBack();
    }

    private void initGame() {
        this.game = Game.getInstance();
//        this.player1 = new GreedyAgent(new GreedyHeuristic());
        this.player1 = new HumanAgent();
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
                if (!(player1 instanceof HumanAgent))
                    return;
                handleHumanNodeClick(Integer.valueOf(id));
            }
        });
    }

    private void initControls() {
        boolean human = this.curGameState.getCurrentPlayer().getId() == 0 ?
                player1 instanceof HumanAgent : player2 instanceof HumanAgent;
        this.humanButton.setDisable(!human);
        this.nextTurnButton.setDisable(human);

        this.updateInfo();
    }

    @FXML
    public void nextTurn() {
        this.curGameState = this.game.playTurn(this.curGameState, player1, player2);
        this.graphView.updateFromGameState(this.curGameState);

        this.setTurns();

        this.curHumanState = HumanState.IDLE;
        this.initControls();

        if (this.curGameState.isFinalState()) {
            this.nextTurnButton.setDisable(true);
            this.drawGameOver();
        }
    }

    @FXML
    public void handleHumanButtonClick() {
        switch (curHumanState) {
            case IDLE:
                curHumanState = HumanState.REINFORCING;
                humanButton.setDisable(true);
                break;
            case IDLE_ATTACKING:
                curHumanState = HumanState.SELECTING;
                humanButton.setText("C A N C E L");
                humanButton.setDisable(false);
                break;
            case SELECTING:
                curHumanState = HumanState.FINISHED;
                humanButton.setText("R E I N F O R C E");
                humanButton.setDisable(true);
                nextTurnButton.setDisable(false);
            case ATTACKING:
                curHumanState = HumanState.FINISHED;
                humanButton.setText("R E I N F O R C E");
                humanButton.setDisable(true);
                nextTurnButton.setDisable(false);
            default:
                break;
        }
    }

    private void setTurns() {
        this.curTurnIcon.setImage(new Image(
                this.curGameState.getCurrentPlayer().equals(this.curGameState.getWorld().getPlayerOne()) ?
                        PLAYER_1_ICON : PLAYER_2_ICON));
    }

    private void updateInfo() {
        Player player1 = this.curGameState.getWorld().getPlayerOne();
        Player player2 = this.curGameState.getWorld().getPlayerTwo();

        this.troopsCount1.setText(
                String.valueOf(player1.getUnitsCount()) + "(+" +
                        String.valueOf(player1.getTurnAdditionalUnits()) + ")");
        this.troopsCount2.setText(
                String.valueOf(player2.getUnitsCount()) + "(+" +
                        String.valueOf(player2.getTurnAdditionalUnits() + ")"));

        this.conqCountriesCount1.setText(
                String.valueOf(player1.getConqueredCountries().size()));
        this.conqCountriesCount2.setText(
                String.valueOf(player2.getConqueredCountries().size()));

        this.conqContinentsCount1.setText(
                String.valueOf(player1.getConqueredContinents().size()));
        this.conqContinentsCount2.setText(
                String.valueOf(player2.getConqueredContinents().size()));
    }

    private void drawGameOver() {
        this.humanButton.setDisable(true);
        this.nextTurnButton.setDisable(true);
        this.gameOverPane.toFront();

        this.winningPlayerIcon.setImage(new Image(
                this.curGameState.getWinner().equals(this.curGameState.getWorld().getPlayerOne()) ?
                        PLAYER_1_ICON: PLAYER_2_ICON));
    }

    private void handleHumanNodeClick(int countryId) {
        HumanAgent human = (HumanAgent) player1;

        switch (curHumanState) {
            case REINFORCING:
                System.out.println("Reinforcing country: " + countryId);
                curGameState = human.reinforceCountry(curGameState, countryId);
                curHumanState = HumanState.IDLE_ATTACKING;
                break;

            case SELECTING:
                if (curGameState.getWorld().getCountryById(countryId).getOccupant()
                        != curGameState.getCurrentPlayer())
                    break;
                System.out.println("Selected country: " + countryId);
                curCountrySelected = countryId;
                curHumanState = HumanState.ATTACKING;
                break;

            case ATTACKING:
                if (countryId == curCountrySelected) {
                    curCountrySelected = -1;
                    curHumanState = HumanState.SELECTING;
                    break;
                }
                System.out.println("Attacked country: " + countryId);
                curGameState = human.attack(curGameState, curCountrySelected, countryId);
                curHumanState = HumanState.FINISHED;
                break;

            default:
                break;
        }

        Platform.runLater(() -> {
            this.graphView.updateFromGameState(this.curGameState);
            this.updateInfo();

            switch (curHumanState) {
                case IDLE:
                    humanButton.setDisable(false);
                    nextTurnButton.setDisable(true);
                    humanButton.setText("R E I N F O R C E");
                    break;
                case IDLE_ATTACKING:
                    humanButton.setDisable(false);
                    humanButton.setText("A T T A C K");
                    break;
                case FINISHED:
                    humanButton.setDisable(true);
                    nextTurnButton.setDisable(false);
                    humanButton.setText("R E I N F O R C E");
                    break;
            }
        });
    }
}
