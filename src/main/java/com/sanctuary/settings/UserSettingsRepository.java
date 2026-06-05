package com.sanctuary.settings;

import com.sanctuary.config.AudioConfig;
import com.sanctuary.config.GameConfig;
import com.sanctuary.config.SaveConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

public class UserSettingsRepository {

    private static final Logger LOGGER = Logger.getLogger(UserSettingsRepository.class.getName());

    private static final String DISPLAY_MODE_KEY = "displayMode";
    private static final String MUSIC_VOLUME_KEY = "musicVolume";

    public UserSettingsData loadOrDefault() {
        return load().orElseGet(this::createDefaultSettings);
    }

    public Optional<UserSettingsData> load() {
        Path settingsPath = SaveConfig.getUserSettingsPath();

        if (!Files.exists(settingsPath)) {
            return Optional.empty();
        }

        Properties properties = new Properties();

        try (InputStream inputStream = Files.newInputStream(settingsPath)) {
            properties.load(inputStream);

            DisplayMode displayMode = parseDisplayMode(properties);
            double musicVolume = parseMusicVolume(properties);

            return Optional.of(new UserSettingsData(displayMode, musicVolume));
        } catch (IOException | IllegalArgumentException e) {
            LOGGER.warning(() -> "Failed to load user settings: " + e.getMessage());
            return Optional.empty();
        }
    }

    public void save(UserSettingsData settingsData) {
        try {
            Files.createDirectories(SaveConfig.getAppDirectory());

            Properties properties = new Properties();
            properties.setProperty(DISPLAY_MODE_KEY, settingsData.displayMode().name());
            properties.setProperty(MUSIC_VOLUME_KEY, Double.toString(settingsData.musicVolume()));

            try (OutputStream outputStream = Files.newOutputStream(SaveConfig.getUserSettingsPath())) {
                properties.store(outputStream, "Sanctuary user settings");
            }

            LOGGER.info("User settings saved");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to save user settings", e);
        }
    }

    private UserSettingsData createDefaultSettings() {
        DisplayMode defaultDisplayMode = GameConfig.FULLSCREEN_FROM_START
                ? DisplayMode.FULLSCREEN
                : DisplayMode.WINDOWED;

        return new UserSettingsData(
                defaultDisplayMode,
                AudioConfig.DEFAULT_MUSIC_VOLUME
        );
    }

    private DisplayMode parseDisplayMode(Properties properties) {
        String value = properties.getProperty(DISPLAY_MODE_KEY);

        if (value == null || value.isBlank()) {
            return createDefaultSettings().displayMode();
        }

        return DisplayMode.valueOf(value.trim());
    }

    private double parseMusicVolume(Properties properties) {
        String value = properties.getProperty(MUSIC_VOLUME_KEY);

        if (value == null || value.isBlank()) {
            return createDefaultSettings().musicVolume();
        }

        double parsedValue = Double.parseDouble(value.trim());
        return clamp(parsedValue, 0.0, 1.0);
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}