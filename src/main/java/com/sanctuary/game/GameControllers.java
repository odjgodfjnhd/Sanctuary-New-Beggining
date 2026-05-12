package com.sanctuary.game;

import com.sanctuary.camera.CameraController;
import com.sanctuary.input.InputHandler;
import com.sanctuary.input.PlayerInputController;

import java.util.List;

public class GameControllers {

    private final PlayerInputController playerInputController;
    private final CameraController cameraController;

    public GameControllers(
            PlayerInputController playerInputController,
            CameraController cameraController
    ) {
        this.playerInputController = playerInputController;
        this.cameraController = cameraController;
    }

    public void registerInput() {
        inputHandlers().forEach(InputHandler::register);
    }

    public void unregisterInput() {
        inputHandlers().forEach(InputHandler::unregister);
    }

    public PlayerInputController getPlayerInputController() {
        return playerInputController;
    }

    public CameraController getCameraController() {
        return cameraController;
    }

    private List<InputHandler> inputHandlers() {
        return List.of(playerInputController);
    }
}