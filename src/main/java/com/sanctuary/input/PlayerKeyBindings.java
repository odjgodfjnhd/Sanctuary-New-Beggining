package com.sanctuary.input;

import javafx.scene.input.KeyCode;

import java.util.EnumSet;
import java.util.Set;

public final class PlayerKeyBindings {

    private PlayerKeyBindings() {
    }

    public static final Set<KeyCode> MOVE_UP = EnumSet.of(
            KeyCode.W,
            KeyCode.UP
    );

    public static final Set<KeyCode> MOVE_DOWN = EnumSet.of(
            KeyCode.S,
            KeyCode.DOWN
    );

    public static final Set<KeyCode> MOVE_LEFT = EnumSet.of(
            KeyCode.A,
            KeyCode.LEFT
    );

    public static final Set<KeyCode> MOVE_RIGHT = EnumSet.of(
            KeyCode.D,
            KeyCode.RIGHT
    );

    public static final KeyCode INTERACT = KeyCode.E;

    public static boolean isMovementKey(KeyCode keyCode) {
        return MOVE_UP.contains(keyCode)
                || MOVE_DOWN.contains(keyCode)
                || MOVE_LEFT.contains(keyCode)
                || MOVE_RIGHT.contains(keyCode);
    }
}