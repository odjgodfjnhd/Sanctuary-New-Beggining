package com.sanctuary.config;

import java.nio.file.Path;

public final class SaveConfig {

    private SaveConfig() {
    }

    public static final String APP_DIRECTORY_NAME = "Sanctuary";

    public static final String GAME_SAVE_FILE_NAME = "save.properties";
    public static final String USER_SETTINGS_FILE_NAME = "settings.properties";

    public static Path getAppDirectory() {
        return Path.of(
                System.getProperty("user.home"),
                APP_DIRECTORY_NAME
        );
    }

    public static Path getGameSavePath() {
        return getAppDirectory().resolve(GAME_SAVE_FILE_NAME);
    }

    public static Path getUserSettingsPath() {
        return getAppDirectory().resolve(USER_SETTINGS_FILE_NAME);
    }
}
