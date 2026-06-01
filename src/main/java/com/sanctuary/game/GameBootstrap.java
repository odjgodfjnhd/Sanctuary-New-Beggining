package com.sanctuary.game;

import com.sanctuary.audio.AudioService;
import com.sanctuary.camera.CameraController;
import com.sanctuary.config.DialogueConfig;
import com.sanctuary.config.DialogueConfigLoader;
import com.sanctuary.config.GameConfig;
import com.sanctuary.dialogue.DialogueService;
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

        DialogueConfig dialogueConfig = DialogueConfigLoader.load();

        MapProvider mapProvider = new TiledMapProvider();
        MapService mapService = new MapService(session, mapProvider);
        TransitionService transitionService = new TransitionService();
        DialogueService dialogueService = new DialogueService(dialogueConfig);
        AudioService audioService = new AudioService();

        InteractionService interactionService = new InteractionService(
                session,
                dialogueService
        );

        PlayerInputController playerInputController = new PlayerInputController(
                session,
                interactionService,
                dialogueService
        );
        CameraController cameraController = new CameraController();

        GameServices services = new GameServices(
                mapService,
                transitionService,
                interactionService,
                dialogueService,
                audioService
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
