package main.java.model.game;


import javafx.util.Pair;
import main.java.model.game.util.Parser;
import main.java.model.world.Continent;
import main.java.model.world.Country;
import main.java.model.world.Player;
import main.java.model.world.WorldMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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

public class GameState implements Cloneable {

    private WorldMap world;
    private Player currentPlayer, opponentPlayer;

    public GameState(GameState gameState) {
        // TODO: Creates a new game state by copying given game state (deep copy). -> Copy constructor

    }

    public GameState(String inputFileName){
        this.world = new WorldMap();
        this.init(inputFileName);
    }

    public GameState() {

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
            continentSpecification = continentSpecification.stream().mapToInt(i -> i - 1)
                    .boxed().collect(Collectors.toList());
            continentSpecification.set(0, continentSpecification.get(0) + 1);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("World countries: \n");
        for (Country country : this.world.getCountries()) {
            sb.append("Id: ").append(country.getId()).append(", Occupant Id: ").append(country.hasOccupant() ? country.getOccupant().getId() : -1).append(", Units in country: ").append(country.getUnits()).append(", Continent Id: ").append(country.getContinent().getId()).append("\n");
            sb.append("Neighbours ids: ");
            for (Country neighbour : country.getNeighbours()) {
                sb.append(neighbour.getId()).append(" ");
            }
            sb.append("\n");
        }

        sb.append("\n");
        sb.append("World continents: \n");
        for (Continent continent : this.world.getContinents()) {
            sb.append("Id: ").append(continent.getId()).append(", bonus: ").append(continent.getContinentBonus()).append("\n");
            sb.append("Countries ids: ");
            for (Country country : continent.getCountries()) {
                sb.append(country.getId()).append(" ");
            }
            sb.append("\n");
        }

        sb.append("\n");
        sb.append("Current player Id: ").append(this.getCurrentPlayer().getId()).append("\n");
        sb.append("Opponent player Id: ").append(this.getOpponentPlayer().getId()).append("\n");
        sb.append("\n");
        sb.append("Player 1:\n");
        sb.append("Id: ").append(this.world.getPlayerOne().getId()).append(", last turn bonus: ").append(this.world.getPlayerOne().getLastTurnBonusUnits()).append("\n");
        sb.append("Occupied Countries: ");
        for (Country country : this.world.getPlayerOne().getConqueredCountries()) {
            sb.append(country.getId()).append(" ");
        }
        sb.append("\n");
        sb.append("Occupied Continents: ");
        for (Continent continent : this.world.getPlayerOne().getConqueredContinents()) {
            sb.append(continent.getId()).append(" ");
        }
        sb.append("\n");

        sb.append("\n");
        sb.append("Player 2:\n");
        sb.append("Id: ").append(this.world.getPlayerTwo().getId()).append(", last turn bonus: ").append(this.world.getPlayerTwo().getLastTurnBonusUnits()).append("\n");
        sb.append("Occupied Countries: ");
        for (Country country : this.world.getPlayerTwo().getConqueredCountries()) {
            sb.append(country.getId() + " ");
        }
        sb.append("\n");
        sb.append("Occupied Continents: ");
        for (Continent continent : this.world.getPlayerTwo().getConqueredContinents()) {
            sb.append(continent.getId()).append(" ");
        }
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public Object clone() {
        GameState clone = new GameState();
        clone.setWorld((WorldMap) this.world.clone());
        if (this.currentPlayer.equals(clone.getWorld().getPlayerOne())) {
            clone.setCurrentPlayer(clone.getWorld().getPlayerOne());
            clone.setOpponentPlayer(clone.getWorld().getPlayerTwo());
        } else {
            clone.setCurrentPlayer(clone.getWorld().getPlayerTwo());
            clone.setOpponentPlayer(clone.getWorld().getPlayerOne());
        }
        return clone;
    }

    public List<Country> getUnoccupiedCountries(Player player) {
        return this.world.getUnoccupiedCountries(player);
    }

    public List<Continent> getUnoccupiedContinents() {
        return this.world.getUnoccupiedContinents();
    }

    public List<Continent> getUnconqueredContinents(Player player, Comparator<Continent> comparator) {
        return this.world.getUnconqueredContinents(player, comparator);
    }

    public void swapPlayers() {
        Player opponent = this.opponentPlayer;
        this.setOpponentPlayer(this.currentPlayer);
        this.setCurrentPlayer(opponent);
    }

    public List<GameState> getAllLegalNextStates() {
        List<GameState> allLegalNextStates = new ArrayList<>();

        // First: placement of extra units on any available country
        for (Country country : this.getWorld().getCountries()) {
            if (country.getOccupant().equals(this.currentPlayer)) {
                GameState passiveNewState = (GameState) this.clone();

                passiveNewState.getWorld().getCountryById(country.getId()).
                        setUnits(passiveNewState.getWorld().getCountryById(country.getId()).
                                getUnits() + passiveNewState.getCurrentPlayer().getTurnAdditionalUnits());
                passiveNewState.getCurrentPlayer().setLastTurnBonusUnits(0);
                passiveNewState.swapPlayers();
                allLegalNextStates.add(passiveNewState);

                // Second: for all countries that can attack, for all countries it can attack, add these states
                for (Country attackingCountry : this.currentPlayer.getConqueredCountries()) {
                    for (Country defendingCountry : attackingCountry.getNeighbours()) {
                        if (attackingCountry.canAttack(defendingCountry)) {
                            GameState attackingNewState = (GameState) passiveNewState.clone();
                            attackingNewState.swapPlayers();

                            int unitsToMove = attackingCountry.getUnits() - defendingCountry.getUnits() - 1;
                            defendingCountry.setUnits(unitsToMove);
                            attackingCountry.setUnits(1);

                            // Modify country and player
                            defendingCountry.setOccupant(attackingNewState.getCurrentPlayer());
                            attackingNewState.getCurrentPlayer().addConqueredCountry(defendingCountry);
                            attackingNewState.getOpponentPlayer().removeConqueredCountry(defendingCountry);

                            // Check if attack removed a continent from opponent
                            if (attackingNewState.getOpponentPlayer().getConqueredContinents().contains(defendingCountry.getContinent())) {
                                attackingNewState.getOpponentPlayer().removeConqueredContinent(defendingCountry.getContinent());
                            }

                            // Check if continent is now conquered
                            if (defendingCountry.getContinent().isConquered(attackingNewState.getCurrentPlayer())) {
                                attackingNewState.getCurrentPlayer().addConqueredContinent(defendingCountry.getContinent());
                            }

                            // Set last turn bonus for current player and then swap players
                            attackingNewState.getCurrentPlayer().setLastTurnBonusUnits(2);
                            attackingNewState.swapPlayers();
                            allLegalNextStates.add(attackingNewState);
                        }
                    }
                }
            }
        }
        return allLegalNextStates;
    }
}
