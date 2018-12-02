package main.java.model;

public abstract class SearchAgent implements Agent {
    private Heuristic heuristic;
    protected int searchExpansionSteps;
    protected int turnsToWin;

    public SearchAgent(Heuristic heuristic) {
        this.heuristic = heuristic;
        this.searchExpansionSteps = 0;
        this.turnsToWin = 0;
    }

    public int getSearchExpansionSteps() {
        return this.searchExpansionSteps;
    }

    public int getTurnsToWin() {
        return this.turnsToWin;
    }

    public Heuristic getHeuristic() {
        return this.heuristic;
    }
}
