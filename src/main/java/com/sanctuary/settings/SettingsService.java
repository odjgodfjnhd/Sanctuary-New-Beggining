package com.sanctuary.settings;

import com.almasb.fxgl.dsl.FXGL;
import com.sanctuary.audio.AudioService;
import com.sanctuary.config.GameConfig;
import com.sanctuary.core.GameService;
import javafx.application.Platform;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class SettingsService implements GameService {

    private static final Logger LOGGER = Logger.getLogger(SettingsService.class.getName());

    private static final String DISABLED_FULLSCREEN_EXIT_HINT = "";

    private final AudioService audioService;
    private final UserSettingsRepository userSettingsRepository;

    private DisplayMode displayMode;

    public SettingsService(
            AudioService audioService,
            UserSettingsRepository userSettingsRepository
    ) {
        this.audioService = audioService;
        this.userSettingsRepository = userSettingsRepository;

        UserSettingsData settingsData = userSettingsRepository.loadOrDefault();

        displayMode = settingsData.displayMode();
        audioService.setMusicVolume(settingsData.musicVolume());
    }

    @Override
    public void initialize() {
        applyDisplayModeSafely();
    }

    public void setDisplayMode(DisplayMode displayMode) {
        if (displayMode == null) {
            throw new IllegalArgumentException("Display mode must not be null");
        }

        this.displayMode = displayMode;
        applyDisplayModeSafely();
        saveUserSettings();

        LOGGER.info(() -> "Display mode changed to: " + displayMode);
    }

    public void setFullscreen(boolean enabled) {
        setDisplayMode(enabled ? DisplayMode.FULLSCREEN : DisplayMode.WINDOWED);
    }

    public boolean isFullscreen() {
        return displayMode == DisplayMode.FULLSCREEN;
    }

    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    public void previewMusicVolume(double volume) {
        audioService.setMusicVolume(volume);
    }

    public void setMusicVolume(double volume) {
        audioService.setMusicVolume(volume);
        saveUserSettings();

        LOGGER.info(() -> "Music volume changed to: " + audioService.getMusicVolume());
    }

    public double getMusicVolume() {
        return audioService.getMusicVolume();
    }

    public boolean shouldStartFullscreen() {
        if (displayMode == null) {
            return GameConfig.FULLSCREEN_FROM_START;
        }

        return isFullscreen();
    }

    private void applyDisplayModeSafely() {
        if (Platform.isFxApplicationThread()) {
            applyDisplayMode();
            return;
        }

        Platform.runLater(this::applyDisplayMode);
    }

    private void applyDisplayMode() {
        Stage stage = FXGL.getPrimaryStage();

        stage.setFullScreenExitHint(DISABLED_FULLSCREEN_EXIT_HINT);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreen(isFullscreen());
    }

    private void saveUserSettings() {
        userSettingsRepository.save(new UserSettingsData(
                displayMode,
                audioService.getMusicVolume()
        ));
    }
}
