package com.sanctuary.game;

import com.sanctuary.camera.CameraController;
import com.sanctuary.config.GameConfig;
import com.sanctuary.input.PlayerInputController;
import com.sanctuary.interaction.InteractionService;
import com.sanctuary.world.MapProvider;
import com.sanctuary.world.MapService;
import com.sanctuary.world.TiledMapProvider;
import com.sanctuary.world.TransitionService;

public class GameBootstrap {

    public GameContext bootstrapNewGame() {
        GameSession session = new GameSession();

        session.setCurrentMapId(GameConfig.START_MAP_ID);
        session.setRequestedSpawnId(GameConfig.DEFAULT_SPAWN_ID);

        MapProvider mapProvider = new TiledMapProvider();
        MapService mapService = new MapService(session, mapProvider);
        TransitionService transitionService = new TransitionService();
        InteractionService interactionService = new InteractionService(session);

        PlayerInputController playerInputController = new PlayerInputController(
                session,
                interactionService
        );
        CameraController cameraController = new CameraController();

        GameServices services = new GameServices(
                mapService,
                transitionService,
                interactionService
        );

        GameControllers controllers = new GameControllers(
                playerInputController,
                cameraController
        );

        return new GameContext(
                session,
                services,
                controllers
        );
    }
}