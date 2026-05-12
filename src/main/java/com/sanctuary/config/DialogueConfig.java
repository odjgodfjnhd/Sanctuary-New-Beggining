package com.sanctuary.config;

import javafx.scene.paint.Color;
import javafx.util.Duration;

public final class DialogueConfig {

    private DialogueConfig() {
    }

    public static final double WIDTH_RATIO = 0.90;
    public static final double HEIGHT_RATIO = 0.20;
    public static final double BOTTOM_MARGIN = 24.0;

    public static final double MIN_WIDTH = 560.0;
    public static final double MIN_HEIGHT = 120.0;

    public static final double BACKGROUND_OPACITY = 0.72;
    public static final double BORDER_WIDTH = 3.0;
    public static final double CORNER_RADIUS = 24.0;

    public static final Color BACKGROUND_COLOR = Color.rgb(
            0,
            210,
            255,
            BACKGROUND_OPACITY
    );

    public static final Color BORDER_COLOR = Color.rgb(255, 0, 180);
    public static final Color SPEAKER_TEXT_COLOR = Color.rgb(255, 0, 220);
    public static final Color DIALOGUE_TEXT_COLOR = Color.rgb(20, 0, 45);
    public static final Color HINT_TEXT_COLOR = Color.rgb(70, 0, 90);

    public static final int SPEAKER_FONT_SIZE = 22;
    public static final int DIALOGUE_FONT_SIZE = 20;
    public static final int HINT_FONT_SIZE = 14;

    public static final Duration TYPEWRITER_DELAY = Duration.millis(18);

    public static final String LINE_SEPARATOR = "\\|";
    public static final String ADVANCE_HINT = "E / Space / Enter";
}