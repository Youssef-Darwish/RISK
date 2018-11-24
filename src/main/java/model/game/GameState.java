package main.java.model.game;


import javafx.util.Pair;
import main.java.model.game.util.Parser;
import main.java.model.world.Continent;
import main.java.model.world.Country;
import main.java.model.world.Player;
import main.java.model.world.WorldMap;

import java.util.List;


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
        // TODO: Creates a new game state by copying given game state (deep copy). -> Copy constructor

    }

    public GameState(String inputFileName){
        this.world = new WorldMap();
        this.init(inputFileName);

        this.printStateToConsole();
    }

    private void printStateToConsole() {
        System.out.println("World countries: ");
        for (Country country : this.world.getCountries()) {
            System.out.println("Id: " + country.getId() + ", Occupant Id: " + (country.hasOccupant() ? country.getOccupant().getId() : -1) + ", Units in country: " + country.getUnits() + ", Continent Id: " + country.getContinent().getId());
            System.out.print("Neighbours ids: ");
            for (Country neighbour : country.getNeighbours()) {
                System.out.print(neighbour.getId() + " ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("World continents: ");
        for (Continent continent : this.world.getContinents()) {
            System.out.println("Id: " + continent.getId() + ", bonus: " + continent.getContinentBonus());
            System.out.print("Countries ids: ");
            for (Country country : continent.getCountries()) {
                System.out.print(country.getId() + " ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Player 1:");
        System.out.println("Id: " + this.world.getPlayerOne().getId() + ", last turn bonus: " + this.world.getPlayerOne().getLastTurnBonusUnits());
        System.out.print("Occupied Countries: ");
        for (Country country : this.world.getPlayerOne().getConqueredCountries()) {
            System.out.print(country.getId() + " ");
        }
        System.out.println();
        System.out.print("Occupied Continents: ");
        for (Continent continent : this.world.getPlayerOne().getConqueredContinents()) {
            System.out.print(continent.getId() + " ");
        }
        System.out.println();

        System.out.println();
        System.out.println("Player 2:");
        System.out.println("Id: " + this.world.getPlayerTwo().getId() + ", last turn bonus: " + this.world.getPlayerTwo().getLastTurnBonusUnits());
        System.out.print("Occupied Countries: ");
        for (Country country : this.world.getPlayerTwo().getConqueredCountries()) {
            System.out.print(country.getId() + " ");
        }
        System.out.println();
        System.out.print("Occupied Continents: ");
        for (Continent continent : this.world.getPlayerTwo().getConqueredContinents()) {
            System.out.print(continent.getId() + " ");
        }
        System.out.println();
    }

    private void init(String inputFileName) {
        Parser parser = new Parser(inputFileName);

        // Creating world countries
        int countries = parser.getCountriesNumber();
        for (int i = 0; i < countries; i++) {
            this.world.addCountry(i);
        }
        for (Pair<Integer, Integer> edge : parser.getEdges()) {
            this.world.addEdge(edge.getKey() - 1, edge.getValue() - 1);
        }

        // Creating continents
        int continentId = 0;
        for (List<Integer> continentSpecification : parser.getContinents()) {
            this.world.addContinent(continentId++, continentSpecification);
        }

        // Adding players' units to countries
        this.world.setPlayerOneUnits(parser.getPlayerOneUnits());
        this.world.setPlayerTwoUnits(parser.getPlayerTwoUnits());

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

    public Player getOpponentPlayer(){
        return opponentPlayer;
    }

    public void setWorld(WorldMap world) {
        this.world = world;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setOpponentPlayer(Player player){
        opponentPlayer = player;
    }

    public List<GameState> getNextStates(){
        return null;
    }

    public List<GameState> getAllSuccessors() {
        return null;
    }

    public boolean isFinalState() {
        return this.currentPlayer.getConqueredCountries().size() == this.world.getCountries().size()
                || this.opponentPlayer.getConqueredCountries().size() == this.world.getCountries().size();
    }

    public Player getWinner() {
        if (isFinalState()) {
            if (this.currentPlayer.getConqueredCountries().size() == this.world.getCountries().size()) {
                return this.currentPlayer; // This is to handle an initial placement of the world with a winner already.
            } else {
                return this.opponentPlayer;
            }
        }
        return null; // Or maybe throw an exception as there isn't a winner yet
    }

    public Country getLeastFortifiedCountry(Player player) {
        return this.world.getLeastFortifiedCountry(player);
    }
}
