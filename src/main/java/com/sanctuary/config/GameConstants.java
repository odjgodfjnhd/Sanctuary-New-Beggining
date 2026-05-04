package com.sanctuary.config;

public final class GameConstants {

    private GameConstants() {
    }

    public static final int TILE_SIZE = 32;

    public static final double PLAYER_WIDTH = 32;
    public static final double PLAYER_HEIGHT = 32;
    public static final double PLAYER_HITBOX_WIDTH = 20;
    public static final double PLAYER_HITBOX_HEIGHT = 24;

    public static final double PLAYER_SPRITE_SCALE = 2.0;
    public static final double PLAYER_MOVE_SPEED = 200.0;

    public static final int PLAYER_FRAME_WIDTH = 32;
    public static final int PLAYER_FRAME_HEIGHT = 32;
    public static final int PLAYER_FRAMES_PER_ROW = 24;

    public static final double PLAYER_WALK_ANIMATION_DURATION = 0.10;
    public static final double PLAYER_IDLE_ANIMATION_DURATION = 1.00;

    public static final double SPAWN_FALLBACK_X = 400;
    public static final double SPAWN_FALLBACK_Y = 300;
}