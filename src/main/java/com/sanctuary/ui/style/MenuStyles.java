package com.sanctuary.ui.style;

public final class MenuStyles {

    private static final String MENU_BUTTON_BASE =
            "-fx-text-fill: white;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 10;" +
                    "-fx-border-color: gold;" +
                    "-fx-border-radius: 10;";

    private static final String MENU_BUTTON_NORMAL_BACKGROUND =
            "-fx-background-color: linear-gradient(to bottom, #4a4a4a, #2a2a2a);";

    private static final String MENU_BUTTON_HOVER_BACKGROUND =
            "-fx-background-color: linear-gradient(to bottom, #5a5a5a, #3a3a3a);";

    private MenuStyles() {
    }

    public static String menuButtonNormal() {
        return MENU_BUTTON_NORMAL_BACKGROUND +
                MENU_BUTTON_BASE +
                "-fx-border-width: 2;";
    }

    public static String menuButtonHover() {
        return MENU_BUTTON_HOVER_BACKGROUND +
                MENU_BUTTON_BASE +
                "-fx-text-fill: gold;" +
                "-fx-border-width: 3;";
    }
}