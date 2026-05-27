package com.sanctuary.config;

public final class PlayerConfig {

    private PlayerConfig() {
    }

    public static final double WIDTH = GameConstants.TILE_SIZE;
    public static final double HEIGHT = GameConstants.TILE_SIZE;

    public static final double HITBOX_WIDTH = GameConstants.TILE_SIZE * 0.625;
    public static final double HITBOX_HEIGHT = GameConstants.TILE_SIZE * 0.75;

    public static final double HITBOX_OFFSET_X = (WIDTH - HITBOX_WIDTH) / 2.0;
    public static final double HITBOX_OFFSET_Y = (HEIGHT - HITBOX_HEIGHT) / 2.0;

    public static final double SPRITE_SCALE = 2.0;
    public static final double MOVE_SPEED = 200.0;

    public static final int FRAME_WIDTH = GameConstants.TILE_SIZE;
    public static final int FRAME_HEIGHT = GameConstants.TILE_SIZE;
    public static final int FRAMES_PER_ROW = 24;

    public static final double WALK_ANIMATION_DURATION = 0.10;
    public static final double IDLE_ANIMATION_DURATION = 1.00;
}