package presenter;

import gui.GameVisualizer;
import model.*;

import java.awt.event.MouseEvent;
import java.util.*;

public class GamePresenter {

    public GamePresenter(GameVisualizer visualizer) {
        visualizer.addMouseEventListener(this::setNewTarget);
        visualizer.addTaskOnUpdatePanel(new TimerTask() {
            @Override
            public void run() {
                updateModel();
            }
        });
    }

    public static final GameState gameState = new GameState(100, 100, 0);
    private static TargetData targetData;
    private static Stack<Cell> path;

    public void updateModel() {
        gameState.updateGameState();
    }

    public void setNewTarget(MouseEvent event) {
        Bfs bfs = new Bfs();
        targetData = new TargetData(event.getX(), event.getY());
        
        Cell start = bfs.findRobotPositionCell(gameState.getRobot(), gameState.getGridCells());
        Cell end = bfs.findCellToGo(targetData, gameState.getGridCells());
        path = bfs.findPath(start, end, gameState.getGridCells());
        gameState.setRobotCommand(new RobotMove(targetData));
    }

    public ArrayList<Cell> getGridCellsInfo() {
        return gameState.getGridCells();
    }
    public Robot getRobotInfo() {
        return gameState.getRobot();
    }

    public static TargetData getTargetData() {
        return targetData;
    }
}
