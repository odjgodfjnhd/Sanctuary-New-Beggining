package com.sanctuary.config;

import javafx.scene.paint.Color;
import javafx.util.Duration;

public final class NpcConfig {

    private NpcConfig() {
    }

    public static final double DEFAULT_WIDTH = GameConstants.TILE_SIZE;
    public static final double DEFAULT_HEIGHT = GameConstants.TILE_SIZE;

    public static final String DEFAULT_NAME = "NPC";
    public static final String DEFAULT_DIALOGUE = "Hello.";

    public static final Color OUTLINE_COLOR = Color.LIMEGREEN;
    public static final double OUTLINE_STROKE_WIDTH = 2.0;
    public static final double OUTLINE_INITIAL_OPACITY = 0.25;

    public static final Duration PULSE_DURATION = Duration.seconds(0.8);
    public static final double PULSE_FROM_OPACITY = 0.2;
    public static final double PULSE_TO_OPACITY = 1.0;
}