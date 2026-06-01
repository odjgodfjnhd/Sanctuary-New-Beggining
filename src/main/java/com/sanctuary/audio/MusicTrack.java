package com.sanctuary.audio;

import com.sanctuary.config.AssetPaths;
import com.sanctuary.config.AudioConfig;

public enum MusicTrack {

    MAIN_THEME(AudioConfig.MAIN_THEME_FILE_NAME);

    private final String fileName;

    MusicTrack(String fileName) {
        this.fileName = fileName;
    }

    public String getResourcePath() {
        return AssetPaths.AUDIO_MUSIC_RESOURCE_DIR + fileName;
    }
}
