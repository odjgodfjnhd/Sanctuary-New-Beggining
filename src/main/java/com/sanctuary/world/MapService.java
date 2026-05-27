package com.sanctuary.world;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.sanctuary.config.PlayerConfig;
import com.sanctuary.core.GameService;
import com.sanctuary.entity.EntityType;
import com.sanctuary.entity.factory.GameEntityFactory;
import com.sanctuary.entity.player.MovementComponent;
import com.sanctuary.entity.player.Player;
import com.sanctuary.game.GameSession;

import java.util.ArrayList;
import java.util.List;

public class MapService implements GameService {

    private final GameSession session;
    private final MapProvider mapProvider;

    private boolean entityFactoryRegistered = false;

    public MapService(GameSession session, MapProvider mapProvider) {
        this.session = session;
        this.mapProvider = mapProvider;
    }

    public void loadCurrentMap() {
        String mapId = session.getCurrentMapId();
        String spawnId = session.getRequestedSpawnId();

        if (mapId == null || mapId.isBlank()) {
            throw new IllegalStateException("Current map id is not set");
        }

        WorldMap worldMap = mapProvider.loadMap(mapId);
        session.setCurrentWorldMap(worldMap);

        clearWorld();
        ensureEntityFactoryRegistered();
        mapProvider.loadIntoWorld(worldMap);

        SpawnPoint spawnPoint = resolveSpawnPoint(worldMap, spawnId);
        session.setCurrentSpawnPoint(spawnPoint);

        Entity player = session.getPlayer();

        if (player == null || !player.isActive()) {
            player = Player.create(spawnPoint.getX(), spawnPoint.getY());
            session.setPlayer(player);
        } else {
            player.setPosition(spawnPoint.getX(), spawnPoint.getY());
        }

        configurePlayerBounds(player, worldMap);
    }

    public void changeMap(String targetMapId, String targetSpawnId) {
        session.setCurrentMapId(targetMapId);
        session.setRequestedSpawnId(targetSpawnId);
        loadCurrentMap();
    }

    private void configurePlayerBounds(Entity player, WorldMap worldMap) {
        MovementComponent movement = player.getComponent(MovementComponent.class);

        double maxX = Math.max(0, worldMap.getPixelWidth() - PlayerConfig.WIDTH);
        double maxY = Math.max(0, worldMap.getPixelHeight() - PlayerConfig.HEIGHT);

        movement.setWorldBounds(0, 0, maxX, maxY);
    }

    private void ensureEntityFactoryRegistered() {
        if (!entityFactoryRegistered) {
            FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());
            entityFactoryRegistered = true;
        }
    }

    private SpawnPoint resolveSpawnPoint(WorldMap worldMap, String spawnId) {
        if (spawnId != null && !spawnId.isBlank()) {
            SpawnPoint requestedSpawn = worldMap.getSpawnPoint(spawnId);
            if (requestedSpawn != null) {
                return requestedSpawn;
            }
        }

        SpawnPoint defaultSpawn = worldMap.getSpawnPoint("player_spawn");
        if (defaultSpawn != null) {
            return defaultSpawn;
        }

        throw new IllegalStateException(
                "No valid spawn point found for map: " + worldMap.getMapId()
        );
    }

    private void clearWorld() {
        List<Entity> entitiesToRemove = new ArrayList<>(FXGL.getGameWorld().getEntities());

        for (Entity entity : entitiesToRemove) {
            if (entity.getType() != EntityType.PLAYER) {
                entity.removeFromWorld();
            }
        }
    }
}