package com.sanctuary.config;

public final class GameConfig {

    private GameConfig() {
    }

    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 600;

    public static final String TITLE = "Sanctuary: The New Beginning";
    public static final String VERSION = "0.3.0";

    public static final boolean MAIN_MENU_ENABLED = true;

    public static final String START_MAP_ID = "level1";
    public static final String DEFAULT_SPAWN_ID = "player_spawn";

    public static final String SAVE_FILE_NAME = "sanctuary_save.dat";
}