package main.java.model.game.util;

import javafx.util.Pair;
import scala.Int;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    private List<String> fileLines;

    public Parser(String fileName) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            //br returns as stream and convert it into a List
            this.fileLines = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Assumption: the minimum input map must have 2 vertices, no edges, 1 continent. So at least 4 lines.
        // You can add any validations you want for the file here.
        if (this.fileLines.size() < 4) {
            System.err.println("Invalid risk map specification!");
            System.exit(1);
        }
    }

    public int getCountriesNumber() {
        int countriesNumber = Integer.parseInt(fileLines.get(0).trim().split("\\s")[1]);
        int edges = Integer.parseInt(fileLines.get(1).trim().split("\\s")[1]);
        return 0;
    }

    public int getEdgesNumber() {
        return 0;
    }
    public int getContinentsNumber() {
        return 0;
    }

    public List<Pair<Integer, Integer>> getEdges() {
        return null;
    }

    public List<List<Integer>> getContinents() {
        return null;
    }

    public List<Integer> getPlayerOneUnits() {
        return null;
    }
    public List<Integer> getPlayerTwoUnits() {
        return null;
    }
}
