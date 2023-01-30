package model;

public class TargetData {
    private volatile int targetPositionX;
    private volatile int targetPositionY;

    public TargetData(int x, int y) {
        targetPositionX = x;
        targetPositionY = y;
    }

    public int getTargetPositionX() {
        return targetPositionX;
    }

    public int getTargetPositionY() {
        return targetPositionY;
    }
}