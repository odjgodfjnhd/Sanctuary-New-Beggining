package com.sanctuary.audio;

import com.sanctuary.config.AssetPaths;

public enum MusicTrack {

    MAIN_THEME("main_theme.mp3");

    private final String fileName;

    MusicTrack(String fileName) {
        this.fileName = fileName;
    }

    public String getResourcePath() {
        return AssetPaths.AUDIO_MUSIC_RESOURCE_DIR + fileName;
    }
}
