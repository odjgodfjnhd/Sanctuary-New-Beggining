package com.sanctuary.game;

import com.almasb.fxgl.entity.Entity;
import com.sanctuary.config.GameConfig;
import com.sanctuary.save.GameSaveData;
import com.sanctuary.world.SpawnPoint;
import com.sanctuary.world.WorldMap;

import java.util.HashMap;
import java.util.Map;

public class GameSession {

    private String currentMapId;
    private String requestedSpawnId;
    private Double requestedPlayerX;
    private Double requestedPlayerY;
    private SpawnPoint currentSpawnPoint;
    private WorldMap currentWorldMap;
    private Entity player;
    private final Map<String, Object> flags = new HashMap<>();

    public void prepareNewGame() {
        clear();

        currentMapId = GameConfig.START_MAP_ID;
        requestedSpawnId = GameConfig.DEFAULT_SPAWN_ID;
        requestedPlayerX = null;
        requestedPlayerY = null;
    }

    public void prepareSavedGame(GameSaveData saveData) {
        clear();

        currentMapId = saveData.mapId();
        requestedSpawnId = null;
        requestedPlayerX = saveData.playerX();
        requestedPlayerY = saveData.playerY();
    }

    public boolean hasRequestedPlayerPosition() {
        return requestedPlayerX != null && requestedPlayerY != null;
    }

    public void clearRequestedPlayerPosition() {
        requestedPlayerX = null;
        requestedPlayerY = null;
    }

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
        this.requestedPlayerX = null;
        this.requestedPlayerY = null;
    }

    public Double getRequestedPlayerX() {
        return requestedPlayerX;
    }

    public Double getRequestedPlayerY() {
        return requestedPlayerY;
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
        requestedPlayerX = null;
        requestedPlayerY = null;
        currentSpawnPoint = null;
        currentWorldMap = null;
        player = null;
        flags.clear();
    }
}