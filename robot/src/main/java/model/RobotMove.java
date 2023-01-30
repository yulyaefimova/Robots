package model;

import java.awt.*;
import java.util.Stack;

public class RobotMove {
    private static final double maxVelocity = 0.5;

    private final TargetData targetData;
    private static Cell newStart = null;

    public RobotMove(TargetData targetData) {
        this.targetData = targetData;
    }

    public TargetData getTargetData() {
        return this.targetData;
    }

    public Point getTargetPosition() {
        return new Point(targetData.getTargetPositionX(), targetData.getTargetPositionY());
    }

    public static int round(double value) {
        return (int) (value + 0.5);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        return Math.atan2(diffY, diffX);
    }

    public static void handle(Robot robot, TargetData target) {
        RobotMove robotMove = new RobotMove(target);
        Bfs bfs = new Bfs();
        boolean isTeleport = false;
        GameState gameState = presenter.GamePresenter.gameState;
        Cell start = bfs.findRobotPositionCell(gameState.getRobot(), gameState.getGridCells());
        Cell end = bfs.findCellToGo(presenter.GamePresenter.getTargetData(), gameState.getGridCells());
        if (start == null)
            start = newStart;

        Stack<Cell> path = bfs.findPath(start, end, gameState.getGridCells());
        if (path == null)
            return;
        if (!path.empty() && start == path.peek())
            path.pop();
        if (!path.empty() && start != null && end != null) {
            Cell cell = path.pop();
            if (cell.getTeleportTo() != null) {
                for (Cell value : path)
                    if (value.getCellCoordinates().x == cell.getTeleportTo().x
                    && value.getCellCoordinates().y == cell.getTeleportTo().y) {
                        isTeleport = true;
                        while (!path.empty() && !path.peek().getCellCoordinates().equals(value.getCellCoordinates()))
                            path.pop();
                        cell = path.pop();
                        break;
                    }
            }
            Point newTarget = cell.getCellCenter();
            if (path.empty())
                newTarget = robotMove.getTargetPosition();
            robot.setRobotDirection(angleTo(robot.getRobotPositionX(), robot.getRobotPositionY(),
                    newTarget.x, newTarget.y));
            if (isTeleport) {
                robot.setRobotPositionX(newTarget.x);
                robot.setRobotPositionY(newTarget.y);
                newStart = cell;
            }
            else
                moveRobot(robot, 5);
        }
    }

    private static void moveRobot(Robot robot, double duration)
    {
        double newX = robot.getRobotPositionX() + RobotMove.maxVelocity * duration * Math.cos(robot.getRobotDirection());
        double newY = robot.getRobotPositionY() + RobotMove.maxVelocity * duration * Math.sin(robot.getRobotDirection());
        robot.setRobotPositionX(newX);
        robot.setRobotPositionY(newY);
    }
}
