package com.sanctuary.input;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.sanctuary.dialogue.DialogueService;
import com.sanctuary.entity.player.MovementComponent;
import com.sanctuary.game.GameSession;
import com.sanctuary.interaction.InteractionService;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PlayerInputController implements InputHandler {

    private final GameSession session;
    private final InteractionService interactionService;
    private final DialogueService dialogueService;
    private final PlayerMovementInputState movementInputState;

    public PlayerInputController(
            GameSession session,
            InteractionService interactionService,
            DialogueService dialogueService
    ) {
        this.session = session;
        this.interactionService = interactionService;
        this.dialogueService = dialogueService;
        this.movementInputState = new PlayerMovementInputState();
    }

    @Override
    public void register() {
        FXGL.getInput().addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
        FXGL.getInput().addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyReleased);
    }

    public void registerInput() {
        register();
    }

    private void handleKeyPressed(KeyEvent event) {
        KeyCode key = event.getCode();

        if (dialogueService.isDialogueOpen()) {
            handleDialogueInput(key);
            return;
        }

        if (movementInputState.handles(key)) {
            movementInputState.press(key);
            updateMovement();
        }

        if (key == PlayerKeyBindings.INTERACT) {
            interactionService.interact();
            stopMovementIfDialogueOpened();
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        KeyCode key = event.getCode();

        if (dialogueService.isDialogueOpen()) {
            return;
        }

        if (movementInputState.handles(key)) {
            movementInputState.release(key);
            updateMovement();
        }
    }

    private void handleDialogueInput(KeyCode key) {
        if (key == PlayerKeyBindings.INTERACT
                || key == KeyCode.SPACE
                || key == KeyCode.ENTER) {
            dialogueService.advance();
        }
    }

    private void stopMovementIfDialogueOpened() {
        if (!dialogueService.isDialogueOpen()) {
            return;
        }

        movementInputState.clear();
        updateMovement();
    }

    private void updateMovement() {
        Entity player = session.getPlayer();

        if (player == null || !player.isActive()) {
            return;
        }

        MovementComponent movement = player.getComponent(MovementComponent.class);

        movement.setMovement(
                movementInputState.getMoveX(),
                movementInputState.getMoveY()
        );
    }
}