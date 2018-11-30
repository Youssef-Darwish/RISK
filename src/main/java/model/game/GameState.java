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
import java.util.Objects;
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
    private int depth;

    public GameState(String inputFileName){
        this.world = new WorldMap();
        this.depth = 0;
        this.init(inputFileName);
    }

    public GameState() {
        this.depth = 0;
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
        this.world.setPlayerOneCountries(parser.getPlayerOneUnits());
        this.world.setPlayerTwoCountries(parser.getPlayerTwoUnits());

        // Check if players have countries after initial placement of units
        this.world.setPlayerOneContinents();
        this.world.setPlayerTwoContinents();

        // Initializing current and opponent players
        this.currentPlayer = this.world.getPlayerOne();
        this.opponentPlayer = this.world.getPlayerTwo();
    }

    public int getDepth() {
        return this.depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("World countries: \n");
        for (Country country : this.world.getCountries()) {
            sb.append("Id: ").append(country.getId()).append(", Occupant Id: ").append(country.getOccupant().getId()).append(", Units in country: ").append(country.getUnits()).append(", Continent Id: ").append(country.getContinent().getId()).append("\n");
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
        clone.setDepth(this.depth);
        clone.setWorld((WorldMap) this.world.clone());
        if (this.currentPlayer.getId() == clone.getWorld().getPlayerOne().getId()) {
            clone.setCurrentPlayer(clone.getWorld().getPlayerOne());
            clone.setOpponentPlayer(clone.getWorld().getPlayerTwo());
        } else {
            clone.setCurrentPlayer(clone.getWorld().getPlayerTwo());
            clone.setOpponentPlayer(clone.getWorld().getPlayerOne());
        }
        return clone;
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

        for (Country country : this.getCurrentPlayer().getConqueredCountries()) {
            GameState passiveNewState = (GameState) this.clone();

            // First: placement of extra units on any available country
            passiveNewState.getWorld().getCountryById(country.getId()).
                    setUnits(passiveNewState.getWorld().getCountryById(country.getId()).
                            getUnits() + passiveNewState.getCurrentPlayer().getTurnAdditionalUnits());

            // Creating a state that would attack after initial placement of armies
            GameState attackingNewState = (GameState) passiveNewState.clone();

            // Finalize passive new state (skip attack state)
            passiveNewState.getCurrentPlayer().setLastTurnBonusUnits(0);
            passiveNewState.swapPlayers();
            passiveNewState.setDepth(passiveNewState.getDepth() + 1);
            allLegalNextStates.add(passiveNewState);

            // Second: for all countries that can attack, for all countries it can attack, add these states
            for (Country attackingCountry : attackingNewState.getWorld().getCountries()) {
                if (attackingCountry.getOccupant().getId() == attackingNewState.getCurrentPlayer().getId()) {
                    for (Country defendingCountry : attackingCountry.getNeighbours()) {
                        if (attackingCountry.canAttack(defendingCountry)) {
                            GameState newState = (GameState) attackingNewState.clone();

                            // Performing attack
                            newState.performAttack(newState.getWorld().getCountryById(attackingCountry.getId()),
                                    newState.getWorld().getCountryById(defendingCountry.getId()));

                            // Finalize new state
                            newState.getCurrentPlayer().setLastTurnBonusUnits(2);
                            newState.swapPlayers();
                            newState.setDepth(newState.getDepth() + 1);
                            allLegalNextStates.add(newState);
                        }
                    }
                }
            }
        }
        return allLegalNextStates;
    }

    public void performAttack(Country attackingCountry, Country defendingCountry) {
        Player attackingPlayer = attackingCountry.getOccupant();
        Player defendingPlayer = defendingCountry.getOccupant();

        // Move units, assumption: when attacking, only leave 1 unit behind and attack with the rest
        int unitsToMove = attackingCountry.getUnits() - defendingCountry.getUnits() - 1;
        defendingCountry.setUnits(unitsToMove);
        attackingCountry.setUnits(1);

        // Modify country and player
        defendingCountry.setOccupant(attackingPlayer);
        attackingPlayer.addConqueredCountry(defendingCountry);
        defendingPlayer.removeConqueredCountry(defendingCountry);

        // Check if attack removed a continent from opponent
        Continent continent = defendingCountry.getContinent();
        if (defendingPlayer.getConqueredContinents().contains(continent)) {
            defendingPlayer.removeConqueredContinent(continent);
        }

        // Check if continent is now conquered
        if (continent.isConquered(attackingPlayer)) {
            attackingPlayer.addConqueredContinent(continent);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameState)) return false;
        GameState gameState = (GameState) o;
        return Objects.equals(world, gameState.world) &&
                Objects.equals(currentPlayer, gameState.currentPlayer) &&
                Objects.equals(opponentPlayer, gameState.opponentPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, currentPlayer, opponentPlayer);
    }
}
