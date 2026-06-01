package com.sanctuary.game;

import com.almasb.fxgl.entity.Entity;
import com.sanctuary.config.GameConfig;
import com.sanctuary.game.playerstart.PlayerStartRequest;
import com.sanctuary.game.playerstart.SavedPositionStartRequest;
import com.sanctuary.game.playerstart.SpawnPointStartRequest;
import com.sanctuary.save.GameSaveData;
import com.sanctuary.world.SpawnPoint;
import com.sanctuary.world.WorldMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameSession {

    private String currentMapId = GameConfig.START_MAP_ID;
    private PlayerStartRequest playerStartRequest =
            new SpawnPointStartRequest(GameConfig.DEFAULT_SPAWN_ID);

    private SpawnPoint currentSpawnPoint;
    private WorldMap currentWorldMap;
    private Entity player;
    private final Map<String, Object> flags = new HashMap<>();

    public void prepareNewGame() {
        resetRuntimeState();

        currentMapId = GameConfig.START_MAP_ID;
        playerStartRequest = new SpawnPointStartRequest(GameConfig.DEFAULT_SPAWN_ID);
    }

    public void prepareSavedGame(GameSaveData saveData) {
        resetRuntimeState();

        currentMapId = saveData.mapId();
        playerStartRequest = new SavedPositionStartRequest(
                saveData.playerX(),
                saveData.playerY()
        );
    }

    public String getCurrentMapId() {
        return currentMapId;
    }

    public void setCurrentMapId(String currentMapId) {
        if (currentMapId == null || currentMapId.isBlank()) {
            throw new IllegalArgumentException("Current map id must not be blank");
        }

        this.currentMapId = currentMapId;
    }

    public PlayerStartRequest getPlayerStartRequest() {
        return playerStartRequest;
    }

    public void setPlayerStartRequest(PlayerStartRequest playerStartRequest) {
        if (playerStartRequest == null) {
            throw new IllegalArgumentException("Player start request must not be null");
        }

        this.playerStartRequest = playerStartRequest;
    }

    public void setRequestedSpawnId(String spawnId) {
        setPlayerStartRequest(new SpawnPointStartRequest(spawnId));
    }

    public Optional<String> getRequestedSpawnId() {
        if (playerStartRequest instanceof SpawnPointStartRequest spawnPointStartRequest) {
            return Optional.of(spawnPointStartRequest.spawnId());
        }

        return Optional.empty();
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
        currentMapId = GameConfig.START_MAP_ID;
        playerStartRequest = new SpawnPointStartRequest(GameConfig.DEFAULT_SPAWN_ID);
        resetRuntimeState();
        flags.clear();
    }

    private void resetRuntimeState() {
        currentSpawnPoint = null;
        currentWorldMap = null;
        player = null;
    }
}
