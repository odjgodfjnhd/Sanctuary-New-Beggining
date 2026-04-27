package com.sanctuary.world;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WorldMap {

    private final String mapId;
    private final String mapFileName;
    private final int widthInTiles;
    private final int heightInTiles;
    private final int tileWidth;
    private final int tileHeight;

    private final Map<String, SpawnPoint> spawnPoints = new HashMap<>();

    public WorldMap(
            String mapId,
            String mapFileName,
            int widthInTiles,
            int heightInTiles,
            int tileWidth,
            int tileHeight
    ) {
        this.mapId = mapId;
        this.mapFileName = mapFileName;
        this.widthInTiles = widthInTiles;
        this.heightInTiles = heightInTiles;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public String getMapId() {
        return mapId;
    }

    public String getMapFileName() {
        return mapFileName;
    }

    public int getWidthInTiles() {
        return widthInTiles;
    }

    public int getHeightInTiles() {
        return heightInTiles;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public double getPixelWidth() {
        return (double) widthInTiles * tileWidth;
    }

    public double getPixelHeight() {
        return (double) heightInTiles * tileHeight;
    }

    public void addSpawnPoint(SpawnPoint spawnPoint) {
        spawnPoints.put(spawnPoint.getId(), spawnPoint);
    }

    public SpawnPoint getSpawnPoint(String spawnId) {
        return spawnPoints.get(spawnId);
    }

    public Map<String, SpawnPoint> getSpawnPoints() {
        return Collections.unmodifiableMap(spawnPoints);
    }
}