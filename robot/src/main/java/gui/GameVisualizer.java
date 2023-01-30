package gui;

import model.*;
import model.Robot;
import presenter.GamePresenter;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import javax.swing.*;

public class GameVisualizer extends JPanel {
    private final Timer m_timer = initTimer();
    private final GamePresenter presenter;

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    public GameVisualizer() {
        setBackground(Color.WHITE);

        presenter = new GamePresenter(this);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                presenter.updateModel();
                repaint();
            }
        }, 0, 10);
        setDoubleBuffered(true);
    }

    public void addTaskOnUpdatePanel(TimerTask task) {
        m_timer.schedule(task, 0, 100);
    }

    public void addMouseEventListener(Consumer<MouseEvent> listener) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getPoint());
                listener.accept(e);
                repaint();
            }
        });
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Cell cell : presenter.getGridCellsInfo()) {
            drawCell(g, cell);
        }
        Robot robot = presenter.getRobotInfo();
        TargetData target = presenter.getTargetData();

        drawRobot(g2d, RobotMove.round(robot.getRobotPositionX()),
                RobotMove.round(robot.getRobotPositionY()), robot.getRobotDirection());
        if (target != null) {
            drawTarget(g2d, target.getTargetPositionX(), target.getTargetPositionY());
        }
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    public void drawCell(Graphics g, Cell currentCell) {
        int x = currentCell.getCellCoordinates().x;
        int y = currentCell.getCellCoordinates().y;
        int width = currentCell.getCellWidth();
        ArrayList<Color> colors = new ArrayList<Color>();
        if (currentCell.getTeleportTo() != null) {
            Point coord = currentCell.getTeleportTo();
            int centerX = (int)(coord.x + 0.5) + 15;
            int centerY = (int)(coord.y + 0.5) + 15 ;
            g.setColor(Color.BLUE);
            drawOval(g, centerX, centerY, 15, 15);
            fillOval(g, centerX, centerY , 15, 15);
        }
        g.setColor(Color.GREEN);
        if (currentCell.visited)
            g.drawRect(x, y, width, width);
        g.setColor(Color.BLACK);
        if (currentCell.walls.get("top"))
            g.drawLine(x, y, x + width, y);
        if (currentCell.walls.get("right"))
            g.drawLine(x + width, y, x + width, y + width);
        if (currentCell.walls.get("bottom"))
            g.drawLine(x + width, y + width, x, y + width);
        if (currentCell.walls.get("left"))
            g.drawLine(x, y + width, x, y);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        int robotCenterX = RobotMove.round(x);
        int robotCenterY = RobotMove.round(y);
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
}
