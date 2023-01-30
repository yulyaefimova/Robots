package model;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map;

public class Bfs {
    private int m = 14;
    public Stack<Cell> findPath(Cell start, Cell end, ArrayList<Cell> maze) {
        int cellWidth = 30;
        Cell currentCell = null;
        int n = 14;
        int[][] cost = new int[m][n];
        Cell[][] points = new Cell[m][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                cost[i][j] = -1;
                points[i][j] = null;
            }
        List<Cell> visited = new ArrayList<>();
        Deque<Cell> queue = new ArrayDeque<>();
        cost[start.getCellCoordinates().x / cellWidth][start.getCellCoordinates().y / cellWidth] = 0;
        visited.add(start);
        queue.addLast(start);
        while (queue.size() != 0) {
            currentCell = queue.removeFirst();
            visited.add(currentCell);
            Map<Cell, String> neighbors = getNeighbors(currentCell, maze, cellWidth);
            for (Cell cell : neighbors.keySet()) {
                if (cell != null && neighbors.get(cell).equals("teleport") && !visited.contains(cell)) {
                    int cellIndex_X = cell.getCellCoordinates().x / cellWidth;
                    int cellIndex_Y = cell.getCellCoordinates().y / cellWidth;
                    points[cellIndex_X][cellIndex_Y] = currentCell;
                    cost[cellIndex_X][cellIndex_Y] =
                            cost[cellIndex_X][cellIndex_Y] + 1;
                    visited.add(cell);
                    queue.addFirst(cell);
                }
                else if (cell != null && !visited.contains(cell) && !currentCell.walls.get(neighbors.get(cell))) {
                    int cellIndex_X = cell.getCellCoordinates().x / cellWidth;
                    int cellIndex_Y = cell.getCellCoordinates().y / cellWidth;
                    points[cellIndex_X][cellIndex_Y] = currentCell;
                    cost[cellIndex_X][cellIndex_Y] =
                            cost[cellIndex_X][cellIndex_Y] + 1;
                    visited.add(cell);
                    queue.addLast(cell);
                }
            }
        }

        if (cost[end.getCellCoordinates().x / cellWidth][end.getCellCoordinates().y / cellWidth] != -1) {
            Cell currentPoint = end;
            Stack<Cell> path = new Stack<>();
            while (currentPoint != null) {
                int cellIndex_X = currentPoint.getCellCoordinates().x / cellWidth;
                int cellIndex_Y = currentPoint.getCellCoordinates().y / cellWidth;
                path.push(currentPoint);
                currentPoint = points[cellIndex_X]
                        [cellIndex_Y];
            }
            return path;
        }
        return null;
    }

    private Map<Cell, String> getNeighbors(Cell currentCell, ArrayList<Cell> maze, int cellWidth) {
        int x = currentCell.getCellCoordinates().x;
        int y = currentCell.getCellCoordinates().y;
        Cell top = currentCell.getNeighborCell(x, y - cellWidth, maze);
        Cell right = currentCell.getNeighborCell(x + cellWidth, y, maze);
        Cell bottom = currentCell.getNeighborCell(x, y + cellWidth, maze);
        Cell left = currentCell.getNeighborCell(x - cellWidth, y, maze);
        Map<Cell, String> neighbors = new HashMap<>();
        neighbors.put(top, "top");
        neighbors.put(right, "right");
        neighbors.put(left, "left");
        neighbors.put(bottom, "bottom");
        if (currentCell.getTeleportTo() != null) {
            Point tel = currentCell.getTeleportTo();
            neighbors.put(currentCell.getNeighborCell(tel.x, tel.y, maze), "teleport");
        }

        return neighbors;
    }

    public Cell findRobotPositionCell(Robot robot, ArrayList<Cell> gridCells) {
        double robotPositionX = robot.getRobotPositionX();
        double robotPositionY = robot.getRobotPositionY();
        for (Cell cell : gridCells) {
            int width = cell.getCellWidth();
            Point curr = cell.getCellCoordinates();
            if (curr.x < robotPositionX && curr.x + width > robotPositionX
                    && robotPositionY > curr.y && curr.y + width > robotPositionY)
                return cell;
        }
        return null;
    }

    public Cell findCellToGo(TargetData target, ArrayList<Cell> gridCells) {
        //01bfs
        int targetPositionX = target.getTargetPositionX();
        int targetPositionY = target.getTargetPositionY();
        for (Cell cell : gridCells) {
            int width = cell.getCellWidth();
            Point curr = cell.getCellCoordinates();
            if (curr.x < targetPositionX && curr.x + width > targetPositionX
                    && targetPositionY > curr.y && curr.y + width > targetPositionY)
                return cell;
        }
        return null;
    }
}
