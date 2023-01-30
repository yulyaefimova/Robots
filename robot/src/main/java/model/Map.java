package model;

import model.Maze;

import java.util.ArrayList;

/**
 * класс клеточного поля
 */
public class Map {
    Maze maze;
    private ArrayList<Cell> gridCells;

    private final int linesCount;

    public Map(int linesCount) {
        maze = new Maze(linesCount);
        gridCells = maze.createMaze();
        this.linesCount = linesCount;
    }

    public ArrayList<Cell> getGridCells() {
        return gridCells;
    }
}
