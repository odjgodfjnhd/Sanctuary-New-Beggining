package com.sanctuary.game;

import com.sanctuary.audio.AudioService;
import com.sanctuary.core.GameService;
import com.sanctuary.dialogue.DialogueService;
import com.sanctuary.interaction.InteractionService;
import com.sanctuary.settings.SettingsService;
import com.sanctuary.world.MapService;
import com.sanctuary.world.TransitionService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GameServices implements GameService {

    private final MapService mapService;
    private final TransitionService transitionService;
    private final InteractionService interactionService;
    private final DialogueService dialogueService;
    private final AudioService audioService;
    private final SettingsService settingsService;

    public GameServices(
            MapService mapService,
            TransitionService transitionService,
            InteractionService interactionService,
            DialogueService dialogueService,
            AudioService audioService,
            SettingsService settingsService
    ) {
        this.mapService = mapService;
        this.transitionService = transitionService;
        this.interactionService = interactionService;
        this.dialogueService = dialogueService;
        this.audioService = audioService;
        this.settingsService = settingsService;
    }

    @Override
    public void initialize() {
        services().forEach(GameService::initialize);
    }

    @Override
    public void dispose() {
        List<GameService> servicesToDispose = new ArrayList<>(services());
        Collections.reverse(servicesToDispose);
        servicesToDispose.forEach(GameService::dispose);
    }

    public MapService getMapService() {
        return mapService;
    }

    public TransitionService getTransitionService() {
        return transitionService;
    }

    public InteractionService getInteractionService() {
        return interactionService;
    }

    public DialogueService getDialogueService() {
        return dialogueService;
    }

    public AudioService getAudioService() {
        return audioService;
    }

    public SettingsService getSettingsService() {
        return settingsService;
    }

    private List<GameService> services() {
        return List.of(
                mapService,
                transitionService,
                interactionService,
                dialogueService,
                settingsService,
                audioService
        );
    }
}