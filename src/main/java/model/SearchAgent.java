package main.java.model;

public abstract class SearchAgent implements Agent {
    private Heuristic heuristic;

    public SearchAgent(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public Heuristic getHeuristic() {
        return heuristic;
    }
}
