package com.sanctuary.game;

import com.almasb.fxgl.entity.Entity;
import com.sanctuary.world.SpawnPoint;
import com.sanctuary.world.WorldMap;

import java.util.HashMap;
import java.util.Map;

public class GameSession {

    private String currentMapId;
    private String requestedSpawnId;
    private SpawnPoint currentSpawnPoint;
    private WorldMap currentWorldMap;
    private Entity player;
    private final Map<String, Object> flags = new HashMap<>();

    public String getCurrentMapId() {
        return currentMapId;
    }

    public void setCurrentMapId(String currentMapId) {
        this.currentMapId = currentMapId;
    }

    public String getRequestedSpawnId() {
        return requestedSpawnId;
    }

    public void setRequestedSpawnId(String requestedSpawnId) {
        this.requestedSpawnId = requestedSpawnId;
    }

    public SpawnPoint getCurrentSpawnPoint() {
        return currentSpawnPoint;
    }

    public void setCurrentSpawnPoint(SpawnPoint currentSpawnPoint) {
        this.currentSpawnPoint = currentSpawnPoint;
    }

    public WorldMap getCurrentWorldMap() {
        return currentWorldMap;
    }

    public void setCurrentWorldMap(WorldMap currentWorldMap) {
        this.currentWorldMap = currentWorldMap;
    }

    public Entity getPlayer() {
        return player;
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public Map<String, Object> getFlags() {
        return flags;
    }

    public void setFlag(String key, Object value) {
        flags.put(key, value);
    }

    public Object getFlag(String key) {
        return flags.get(key);
    }

    public boolean hasFlag(String key) {
        return flags.containsKey(key);
    }

    public void clear() {
        currentMapId = null;
        requestedSpawnId = null;
        currentSpawnPoint = null;
        currentWorldMap = null;
        player = null;
        flags.clear();
    }
}