package com.sanctuary.input;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.sanctuary.entity.player.MovementComponent;
import com.sanctuary.game.GameSession;
import javafx.scene.input.KeyCode;

import java.util.EnumMap;
import java.util.Map;

public class PlayerInputController {

    private final GameSession session;
    private final Map<KeyCode, Boolean> keyStates = new EnumMap<>(KeyCode.class);

    public PlayerInputController(GameSession session) {
        this.session = session;
        initKeyState(KeyCode.W);
        initKeyState(KeyCode.A);
        initKeyState(KeyCode.S);
        initKeyState(KeyCode.D);
        initKeyState(KeyCode.UP);
        initKeyState(KeyCode.DOWN);
        initKeyState(KeyCode.LEFT);
        initKeyState(KeyCode.RIGHT);
    }

    public void registerInput() {
        FXGL.getInput().addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            KeyCode key = event.getCode();
            if (keyStates.containsKey(key)) {
                keyStates.put(key, true);
                updateMovement();
            }
        });

        FXGL.getInput().addEventHandler(javafx.scene.input.KeyEvent.KEY_RELEASED, event -> {
            KeyCode key = event.getCode();
            if (keyStates.containsKey(key)) {
                keyStates.put(key, false);
                updateMovement();
            }
        });
    }

    private void initKeyState(KeyCode keyCode) {
        keyStates.put(keyCode, false);
    }

    private void updateMovement() {
        Entity player = session.getPlayer();

        if (player == null || !player.isActive()) {
            return;
        }

        MovementComponent movement = player.getComponent(MovementComponent.class);

        double moveX = 0;
        double moveY = 0;

        if (isPressed(KeyCode.A) || isPressed(KeyCode.LEFT)) {
            moveX -= 1;
        }

        if (isPressed(KeyCode.D) || isPressed(KeyCode.RIGHT)) {
            moveX += 1;
        }

        if (isPressed(KeyCode.W) || isPressed(KeyCode.UP)) {
            moveY -= 1;
        }

        if (isPressed(KeyCode.S) || isPressed(KeyCode.DOWN)) {
            moveY += 1;
        }

        movement.setMovement(moveX, moveY);
    }

    private boolean isPressed(KeyCode keyCode) {
        return Boolean.TRUE.equals(keyStates.get(keyCode));
    }
}