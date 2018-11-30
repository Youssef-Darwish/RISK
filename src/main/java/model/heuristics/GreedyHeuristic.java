package main.java.model.heuristics;

import main.java.model.Heuristic;
import main.java.model.game.GameState;
import main.java.model.world.Continent;

/**
 * H(S) = - sum(continents bonus for player) - sum(player countries)
 *        + sum(continents bonus for opponent) + sum(opponent countries).
 */
public class GreedyHeuristic implements Heuristic {
    @Override
    public int eval(GameState gameState) {
//        int res = 0;
//        res += gameState.getCurrentPlayer().getConqueredCountries().size();
//        res += gameState.getCurrentPlayer()
//                .getConqueredContinents().stream()
//                .mapToInt(Continent::getContinentBonus).sum();
//        res -= gameState.getOpponentPlayer()
//                .getConqueredCountries().size();
//        res -= gameState.getOpponentPlayer()
//                .getConqueredContinents().stream()
//                .mapToInt(Continent::getContinentBonus).sum();
//        return res;
        return gameState.getCurrentPlayer().getConqueredCountries().size();
    }
}
