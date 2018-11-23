package main.java.model.game.util;

import javafx.util.Pair;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    private List<String> fileLines;

    public Parser(String fileName) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            this.fileLines = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Assumption: the minimum input map must have 2 vertices, no edges, 1 continent. So at least 4 lines.
        // You can add any validations you want for the file here.
        if (this.fileLines.size() < 4) {
            System.err.println("Invalid risk map specification!");
            System.exit(-1);
        }
    }

    public int getCountriesNumber() {
        return Integer.parseInt(fileLines.get(0).trim().split("\\s+")[1]);
    }

    public int getEdgesNumber() {
        return Integer.parseInt(fileLines.get(1).trim().split("\\s+")[1]);
    }
    public int getContinentsNumber() {
        return Integer.parseInt(fileLines.get(2 + getEdgesNumber()).trim().split("\\s+")[1]);
    }

    public List<Pair<Integer, Integer>> getEdges() {
        List<Pair<Integer, Integer>> edges = new ArrayList<>();
        for (int i = 0; i < getEdgesNumber(); i++) {
            String currLine = this.fileLines.get(2 + i);
            int firstVertex = Integer.parseInt(currLine.substring(currLine.indexOf('(') + 1, currLine.indexOf(')')).trim().split("\\s+")[0]);
            int secondVertex = Integer.parseInt(currLine.substring(currLine.indexOf('(') + 1, currLine.indexOf(')')).trim().split("\\s+")[1]);
            edges.add(new Pair<>(firstVertex, secondVertex));
        }
        return edges;
    }

    public List<List<Integer>> getContinents() {
        List<List<Integer>> continentsSpecifications = new ArrayList<>();
        for (int i = 0; i < getContinentsNumber(); i++) {
            String currLine = this.fileLines.get(3 + getEdgesNumber() + i);
            List<Integer> continentSpecs = new ArrayList<>();
            for (String s : currLine.trim().split("\\s+")) {
                continentSpecs.add(Integer.parseInt(s));
            }
            continentsSpecifications.add(continentSpecs);
        }
        return continentsSpecifications;
    }

    public List<Integer> getPlayerOneUnits() {
        List<Integer> playerOneUnits = new ArrayList<>();
        String line = this.fileLines.get(3 + getEdgesNumber() + getContinentsNumber());
        for (String s : line.trim().split("\\s+")) {
            playerOneUnits.add(Integer.parseInt(s));
        }
        return playerOneUnits;
    }

    public List<Integer> getPlayerTwoUnits() {
        List<Integer> playerTwoUnits = new ArrayList<>();
        String line = this.fileLines.get(4 + getEdgesNumber() + getContinentsNumber());
        for (String s : line.trim().split("\\s+")) {
            playerTwoUnits.add(Integer.parseInt(s));
        }
        return playerTwoUnits;
    }
}
