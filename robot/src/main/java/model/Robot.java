package model;


public class Robot {
    private volatile double robotPositionX;
    private volatile double robotPositionY;
    private volatile double robotDirection;

    public Robot(double x, double y, double direction) {
        robotPositionX = x;
        robotPositionY = y;
        robotDirection = direction;
    }

    public double getRobotPositionX() {
        return robotPositionX;
    }

    public double getRobotPositionY() {
        return robotPositionY;
    }

    public double getRobotDirection() {
        return robotDirection;
    }

    public void setRobotPositionX(double robotPositionX) {
        this.robotPositionX = robotPositionX;
    }

    public void setRobotPositionY(double robotPositionY) {
        this.robotPositionY = robotPositionY;
    }

    public void setRobotDirection(double robotDirection) {
        this.robotDirection = robotDirection;
    }
}
