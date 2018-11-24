package main.java.model.agent;

public abstract class SearchAgent implements Agent {
    protected Heuristic heuristic;

    public SearchAgent(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
}
