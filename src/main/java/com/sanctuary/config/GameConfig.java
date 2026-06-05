package com.sanctuary.config;

public final class GameConfig {

    private GameConfig() {
    }

    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 600;

    public static final boolean FULLSCREEN_ALLOWED = true;
    public static final boolean FULLSCREEN_FROM_START = true;

    public static final String TITLE = "Sanctuary: The New Beginning";
    public static final String VERSION = "0.4.0";

    public static final boolean MAIN_MENU_ENABLED = true;

    public static final String START_MAP_ID = "Forest";
    public static final String DEFAULT_SPAWN_ID = "player_spawn";
}