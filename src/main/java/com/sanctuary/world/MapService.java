package com.sanctuary.world;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.sanctuary.config.GameConfig;
import com.sanctuary.config.PlayerConfig;
import com.sanctuary.core.GameService;
import com.sanctuary.entity.EntityType;
import com.sanctuary.entity.factory.GameEntityFactory;
import com.sanctuary.entity.player.MovementComponent;
import com.sanctuary.entity.player.Player;
import com.sanctuary.game.GameSession;
import com.sanctuary.game.playerstart.PlayerStartRequest;
import com.sanctuary.game.playerstart.SavedPositionStartRequest;
import com.sanctuary.game.playerstart.SpawnPointStartRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MapService implements GameService {

    private static final Logger LOGGER = Logger.getLogger(MapService.class.getName());

    private static final String SAVED_POSITION_SPAWN_ID = "saved_position";

    private final GameSession session;
    private final MapProvider mapProvider;

    private boolean entityFactoryRegistered = false;

    public MapService(GameSession session, MapProvider mapProvider) {
        this.session = session;
        this.mapProvider = mapProvider;
    }

    @Override
    public void initialize() {
        entityFactoryRegistered = false;
        LOGGER.info("MapService initialized. Entity factory registration flag was reset.");
    }

    @Override
    public void dispose() {
        entityFactoryRegistered = false;
        LOGGER.info("MapService disposed. Entity factory registration flag was reset.");
    }

    public void loadCurrentMap() {
        String mapId = session.getCurrentMapId();

        if (mapId == null || mapId.isBlank()) {
            throw new IllegalStateException("Current map id is not set");
        }

        WorldMap worldMap = mapProvider.loadMap(mapId);
        session.setCurrentWorldMap(worldMap);

        clearWorld();
        ensureEntityFactoryRegistered();
        mapProvider.loadIntoWorld(worldMap);

        SpawnPoint spawnPoint = resolveStartPoint(worldMap, session.getPlayerStartRequest());
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
        if (entityFactoryRegistered) {
            return;
        }

        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());
        entityFactoryRegistered = true;

        LOGGER.info("GameEntityFactory was registered in current GameWorld.");
    }

    private SpawnPoint resolveStartPoint(
            WorldMap worldMap,
            PlayerStartRequest playerStartRequest
    ) {
        if (playerStartRequest instanceof SavedPositionStartRequest savedPositionStartRequest) {
            return new SpawnPoint(
                    SAVED_POSITION_SPAWN_ID,
                    savedPositionStartRequest.x(),
                    savedPositionStartRequest.y()
            );
        }

        if (playerStartRequest instanceof SpawnPointStartRequest spawnPointStartRequest) {
            return resolveSpawnPoint(worldMap, spawnPointStartRequest.spawnId());
        }

        throw new IllegalStateException(
                "Unsupported player start request: " + playerStartRequest.getClass().getName()
        );
    }

    private SpawnPoint resolveSpawnPoint(WorldMap worldMap, String spawnId) {
        SpawnPoint requestedSpawn = worldMap.getSpawnPoint(spawnId);

        if (requestedSpawn != null) {
            return requestedSpawn;
        }

        LOGGER.warning(() ->
                "Requested spawn point was not found: "
                        + spawnId
                        + ". Map: "
                        + worldMap.getMapId()
        );

        SpawnPoint defaultSpawn = worldMap.getSpawnPoint(GameConfig.DEFAULT_SPAWN_ID);

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
