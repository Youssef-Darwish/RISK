package main.java.model.agent;

import main.java.model.game.GameState;
import main.java.model.world.Continent;
import main.java.model.world.Country;
import main.java.model.world.Player;

import java.util.List;

public interface Agent {
    public GameState getNextState(GameState currentState);
}
