package model;

import java.awt.*;
import java.util.*;
import java.util.Map;


/**
 * класс клетки поля
 */
public class Cell {
    public boolean visited;
    private int rows = 14;
    private int cols = 14;
    private Teleport teleport;

    /**
     * ширина клетки
     */
    private final int cellWidth = 30;
    private final int x;
    private final int y;
    public Map<String, Boolean> walls = new HashMap<>();

    {
        walls.put("top", true);
        walls.put("right", true);
        walls.put("bottom", true);
        walls.put("left", true);
    }

    public void setTeleport(int x, int y) {
        if (teleport == null)
            teleport = new Teleport(x, y);
    }

    public Point getTeleportTo() {
        return teleport == null ? null : teleport.getTeleportToCoordinates();
    }

    public Point getCellCoordinates() {
        return new Point(x, y);
    }

    public Point getCellCenter() {
        return new Point(x + 15, y + 15);
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        visited = false;
    }

    /**
     * @return есть ли соседняя клетка, если есть вернет ее, если нет то null
     */
    public Cell getNeighborCell(int x, int y, ArrayList<Cell> gridCells) {
        if (x < 0 || x > cols * cellWidth - 1 || y < 0 || y > rows * cellWidth - 1)
            return null;
        int index = x / cellWidth + y / cellWidth * cols;
        return gridCells.get(index);
    }

    /**
     * @return рандомная соседняя не посещенная  клетка
     */
    public Cell getRandomNeighbor(ArrayList<Cell> gridCells) {
        Stack<Cell> neighbors = new Stack<>();
        Cell top = getNeighborCell(x, y - cellWidth, gridCells);
        Cell right = getNeighborCell(x + cellWidth, y, gridCells);
        Cell bottom = getNeighborCell(x, y + cellWidth, gridCells);
        Cell left = getNeighborCell(x - cellWidth, y, gridCells);
        if (top != null && !top.visited)
            neighbors.push(top);
        if (right != null && !right.visited)
            neighbors.push(right);
        if (bottom != null && !bottom.visited)
            neighbors.push(bottom);
        if (left != null && !left.visited)
            neighbors.push(left);
        Random rnd = new Random();
        if (neighbors.size() > 0) {
            int index = rnd.nextInt(neighbors.size());
            return neighbors.get(index);
        } else return null;
    }
}
