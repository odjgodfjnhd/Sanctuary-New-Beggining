package com.sanctuary.save;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SaveData implements Serializable {
    private static final long serialVersionUID = 1L;

    public String currentMapName;
    public double playerX, playerY;
    public Map<String, Object> flags = new HashMap<>();

    public SaveData(String mapName, double x, double y) {
        this.currentMapName = mapName;
        this.playerX = x;
        this.playerY = y;
    }
}