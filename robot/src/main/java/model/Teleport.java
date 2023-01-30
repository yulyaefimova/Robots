package model;

import java.awt.*;

public class Teleport {
    private final int x;
    private final int y;

    public Teleport(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point getTeleportToCoordinates() {
        return new Point(x, y);
    }
}
