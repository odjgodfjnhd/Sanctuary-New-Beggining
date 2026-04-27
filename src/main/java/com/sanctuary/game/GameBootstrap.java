package com.sanctuary.game;

import com.sanctuary.camera.CameraController;
import com.sanctuary.config.GameConfig;
import com.sanctuary.input.PlayerInputController;
import com.sanctuary.world.MapLoader;
import com.sanctuary.world.MapService;

public class GameBootstrap {

    public GameContext bootstrapNewGame() {
        GameSession session = new GameSession();

        session.setCurrentMapId(GameConfig.START_MAP_ID);
        session.setRequestedSpawnId(GameConfig.DEFAULT_SPAWN_ID);

        MapLoader mapLoader = new MapLoader();
        MapService mapService = new MapService(session, mapLoader);
        PlayerInputController playerInputController = new PlayerInputController(session);
        CameraController cameraController = new CameraController();

        return new GameContext(
                session,
                mapService,
                playerInputController,
                cameraController
        );
    }
}