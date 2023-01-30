package model;

import java.util.ArrayList;

public class GameState {

    private final Robot robot;
    private RobotMove robotCommand;
    private Map map;

    /**
     * список клеток на поле
     */

    private ArrayList<Cell> gridCells = new ArrayList<>();

    public GameState(double robotX, double robotY, double robotDir) {
        map = new Map(14);
        gridCells = map.getGridCells();
        robot = new Robot(robotX, robotY, robotDir);
    }

    public void updateGameState() {
        if (robotCommand != null)
            RobotMove.handle(robot, robotCommand.getTargetData());
    }

    public void setRobotCommand(RobotMove robotCommand) {
        this.robotCommand = robotCommand;
    }

    public ArrayList<Cell> getGridCells() {
        return this.gridCells;
    }

    public Robot getRobot() {
        return this.robot;
    }
}
