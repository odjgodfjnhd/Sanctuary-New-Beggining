package com.sanctuary.input;

import javafx.scene.input.KeyCode;

import java.util.EnumMap;
import java.util.Map;

public class PlayerMovementInputState {

    private final Map<KeyCode, Boolean> pressedKeys = new EnumMap<>(KeyCode.class);

    public PlayerMovementInputState() {
        registerMovementKeys(PlayerKeyBindings.MOVE_UP);
        registerMovementKeys(PlayerKeyBindings.MOVE_DOWN);
        registerMovementKeys(PlayerKeyBindings.MOVE_LEFT);
        registerMovementKeys(PlayerKeyBindings.MOVE_RIGHT);
    }

    public boolean handles(KeyCode keyCode) {
        return pressedKeys.containsKey(keyCode);
    }

    public void press(KeyCode keyCode) {
        if (handles(keyCode)) {
            pressedKeys.put(keyCode, true);
        }
    }

    public void release(KeyCode keyCode) {
        if (handles(keyCode)) {
            pressedKeys.put(keyCode, false);
        }
    }

    public double getMoveX() {
        double moveX = 0;

        if (isAnyPressed(PlayerKeyBindings.MOVE_LEFT)) {
            moveX -= 1;
        }

        if (isAnyPressed(PlayerKeyBindings.MOVE_RIGHT)) {
            moveX += 1;
        }

        return moveX;
    }

    public double getMoveY() {
        double moveY = 0;

        if (isAnyPressed(PlayerKeyBindings.MOVE_UP)) {
            moveY -= 1;
        }

        if (isAnyPressed(PlayerKeyBindings.MOVE_DOWN)) {
            moveY += 1;
        }

        return moveY;
    }

    private void registerMovementKeys(Iterable<KeyCode> keyCodes) {
        for (KeyCode keyCode : keyCodes) {
            pressedKeys.put(keyCode, false);
        }
    }

    private boolean isAnyPressed(Iterable<KeyCode> keyCodes) {
        for (KeyCode keyCode : keyCodes) {
            if (Boolean.TRUE.equals(pressedKeys.get(keyCode))) {
                return true;
            }
        }

        return false;
    }
}