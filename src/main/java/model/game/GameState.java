package main.java.model.game;


import javafx.util.Pair;
import main.java.model.game.util.Parser;
import main.java.model.world.Continent;
import main.java.model.world.Country;
import main.java.model.world.Player;
import main.java.model.world.WorldMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A GameState specifies the full game state, including the continents, capsules,
 * agent configurations and score changes.
 *
 * GameStates are used by the Game object to capture the actual state of the game and
 * can be used by agents to reason about the game.A GameState specifies the full game state, including the food, capsules,
 * agent configurations and score changes.
 *
 * GameStates are used by the Game object to capture the actual state of the game and
 * can be used by agents to reason about the game.
 */

public class GameState {

    private WorldMap world;
    private Player currentPlayer, opponentPlayer;

    public GameState(GameState gameState) {
        // TODO: Creates a new game state by copying given game state (deep copy).
    }

    public GameState(String inputFileName){
        this.world = new WorldMap();
        this.init(inputFileName);
    }

    private void init(String inputFileName) {
        Parser parser = new Parser(inputFileName);

        // Creating world countries
        int countries = parser.getCountriesNumber();
        for (int i = 0; i < countries; i++) {
            this.world.addCountry(i);
        }
        for (Pair<Integer, Integer> edge : parser.getEdges()) {
            int countryOneId = edge.getKey() - 1;
            int countryTwoId = edge.getValue() - 1;
            this.world.addEdge(countryOneId, countryTwoId);
        }

        // Creating continents
        int continentId = 0;
        for (List<Integer> continentSpecification : parser.getContinents()) {
            this.world.addContinent(continentId++, continentSpecification);
        }

        // Adding players' units to countries
        List<Integer> playerOneUnits = parser.getPlayerOneUnits();
        for (int i = 0; i < countries; i++) {
            if (playerOneUnits.get(i) > 0) {
                this.world.getCountryById(i).setUnits(playerOneUnits.get(i));
                this.world.getCountryById(i).setOccupant(this.world.getPlayerOne());
            }
        }

        List<Integer> playerTwoUnits = parser.getPlayerTwoUnits();
        for (int i = 0; i < countries; i++) {
            if (playerTwoUnits.get(i) > 0) {
                this.world.getCountryById(i).setUnits(playerTwoUnits.get(i));
                this.world.getCountryById(i).setOccupant(this.world.getPlayerTwo());
            }
        }

        // Initializing current and opponent players
        this.currentPlayer = this.world.getPlayerOne();
        this.opponentPlayer = this.world.getPlayerTwo();
    }

    public WorldMap getWorld() {
        return world;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    public void setWorld(WorldMap world) {
        this.world = world;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    public List<GameState> getNextStates(){
        return null;
    }

    public List<GameState> getAllSuccessors() {
        return null;
    }

    public boolean isFinalState() {
        // Returns true if this game state is a final game state (a player has won the game by conquering all countries)
        return false;
    }

}
