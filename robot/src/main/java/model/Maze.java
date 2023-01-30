package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


/**
 * класс для генерации лабиринта
 */
public class Maze {
    private ArrayList<Cell> gridCells = new ArrayList<>();

    public Maze(int cols) {
        for (int row = 0; row < cols; row++)
            for (int col = 0; col < cols; col++)
                gridCells.add(new Cell(col * 30, row * 30));
    }

    /**
     * удаляет стены между клетками
     *
     * @param current текущаяя клетка
     * @param next    соседняя клетка
     */
    private void removeWalls(Cell current, Cell next) {
        int dx = current.getCellCoordinates().x - next.getCellCoordinates().x;
        if (dx == 30) {
            current.walls.put("left", Boolean.FALSE);
            next.walls.put("right", Boolean.FALSE);
        } else if (dx == -30) {
            current.walls.put("right", Boolean.FALSE);
            next.walls.put("left", Boolean.FALSE);
        }
        double dy = current.getCellCoordinates().y - next.getCellCoordinates().y;
        if (dy == 30) {
            current.walls.put("top", Boolean.FALSE);
            next.walls.put("bottom", Boolean.FALSE);
        } else if (dy == -30) {
            current.walls.put("bottom", Boolean.FALSE);
            next.walls.put("top", Boolean.FALSE);
        }
    }

    /**
     * генерирует лабиринт
     */
    public ArrayList<Cell> createMaze() {
        Cell currentCell = gridCells.get(0);
        Stack<Cell> stack = new Stack<>();
        for (int i = 0; i < 600; i++) {
            currentCell.visited = true;
            Cell nextCell = currentCell.getRandomNeighbor(gridCells);
            if (nextCell != null) {
                nextCell.visited = true;
                stack.push(currentCell);
                removeWalls(currentCell, nextCell);
                currentCell = nextCell;
            } else if (stack.size() != 0)
                currentCell = stack.pop();
        }
        generateTeleports();
        return gridCells;
    }

    private void generateTeleports() {
        int indexFrom;
        int indexTo;
        int bound = gridCells.size();
        Random rnd = new Random();
        for (int i = 0; i < 2; i++) {
            indexFrom = rnd.nextInt(bound);
            indexTo = rnd.nextInt(bound);
            if (indexFrom != indexTo) {
                Point coordTo = gridCells.get(indexTo).getCellCoordinates();
                Point coordFrom = gridCells.get(indexFrom).getCellCoordinates();
                gridCells.get(indexFrom).setTeleport(coordTo.x, coordTo.y);
                gridCells.get(indexTo).setTeleport(coordFrom.x, coordFrom.y);
            }
        }
    }
}

