package com.sanctuary.game;

import com.sanctuary.camera.CameraController;
import com.sanctuary.input.PlayerInputController;
import com.sanctuary.world.MapService;

public class GameContext {

    private final GameSession session;
    private final MapService mapService;
    private final PlayerInputController playerInputController;
    private final CameraController cameraController;

    public GameContext(
            GameSession session,
            MapService mapService,
            PlayerInputController playerInputController,
            CameraController cameraController
    ) {
        this.session = session;
        this.mapService = mapService;
        this.playerInputController = playerInputController;
        this.cameraController = cameraController;
    }

    public GameSession getSession() {
        return session;
    }

    public MapService getMapService() {
        return mapService;
    }

    public PlayerInputController getPlayerInputController() {
        return playerInputController;
    }

    public CameraController getCameraController() {
        return cameraController;
    }
}