package com.sanctuary.world;

public final class SpawnPoint {

    private final String id;
    private final double x;
    private final double y;

    public SpawnPoint(String id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}