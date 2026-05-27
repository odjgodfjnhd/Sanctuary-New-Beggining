package com.sanctuary.input;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.sanctuary.entity.player.MovementComponent;
import com.sanctuary.game.GameSession;
import com.sanctuary.interaction.InteractionService;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PlayerInputController implements InputHandler {

    private final GameSession session;
    private final InteractionService interactionService;
    private final PlayerMovementInputState movementInputState;

    public PlayerInputController(GameSession session, InteractionService interactionService) {
        this.session = session;
        this.interactionService = interactionService;
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

        if (movementInputState.handles(key)) {
            movementInputState.press(key);
            updateMovement();
        }

        if (key == PlayerKeyBindings.INTERACT) {
            interactionService.interact();
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        KeyCode key = event.getCode();

        if (movementInputState.handles(key)) {
            movementInputState.release(key);
            updateMovement();
        }
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