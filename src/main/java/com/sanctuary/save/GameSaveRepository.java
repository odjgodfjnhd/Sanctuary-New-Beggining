package com.sanctuary.save;

import com.sanctuary.config.SaveConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

public class GameSaveRepository {

    private static final Logger LOGGER = Logger.getLogger(GameSaveRepository.class.getName());

    private static final String MAP_ID_KEY = "mapId";
    private static final String PLAYER_X_KEY = "playerX";
    private static final String PLAYER_Y_KEY = "playerY";
    private static final String SAVED_AT_KEY = "savedAt";

    public boolean exists() {
        return Files.exists(SaveConfig.getGameSavePath());
    }

    public Optional<GameSaveData> load() {
        Path savePath = SaveConfig.getGameSavePath();

        if (!Files.exists(savePath)) {
            return Optional.empty();
        }

        Properties properties = new Properties();

        try (InputStream inputStream = Files.newInputStream(savePath)) {
            properties.load(inputStream);

            String mapId = getRequiredString(properties, MAP_ID_KEY);
            double playerX = getRequiredDouble(properties, PLAYER_X_KEY);
            double playerY = getRequiredDouble(properties, PLAYER_Y_KEY);
            LocalDateTime savedAt = LocalDateTime.parse(getRequiredString(properties, SAVED_AT_KEY));

            return Optional.of(new GameSaveData(
                    mapId,
                    playerX,
                    playerY,
                    savedAt
            ));
        } catch (IOException | IllegalArgumentException e) {
            LOGGER.warning(() -> "Failed to load game save: " + e.getMessage());
            return Optional.empty();
        }
    }

    public void save(GameSaveData saveData) {
        try {
            Files.createDirectories(SaveConfig.getAppDirectory());

            Properties properties = new Properties();
            properties.setProperty(MAP_ID_KEY, saveData.mapId());
            properties.setProperty(PLAYER_X_KEY, Double.toString(saveData.playerX()));
            properties.setProperty(PLAYER_Y_KEY, Double.toString(saveData.playerY()));
            properties.setProperty(SAVED_AT_KEY, saveData.savedAt().toString());

            try (OutputStream outputStream = Files.newOutputStream(SaveConfig.getGameSavePath())) {
                properties.store(outputStream, "Sanctuary game save");
            }

            LOGGER.info(() -> "Game saved: " + saveData.mapId());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to save game", e);
        }
    }

    private String getRequiredString(Properties properties, String key) {
        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Save key '" + key + "' is missing or blank");
        }

        return value.trim();
    }

    private double getRequiredDouble(Properties properties, String key) {
        return Double.parseDouble(getRequiredString(properties, key));
    }
}