package com.sanctuary.config;

import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class DialogueConfigLoader {

    private static final String CONFIG_PATH = "/config/dialogue.conf";

    private DialogueConfigLoader() {
    }

    public static DialogueConfig load() {
        Properties properties = loadProperties();

        return new DialogueConfig(
                getDouble(properties, "widthRatio"),
                getDouble(properties, "heightRatio"),
                getDouble(properties, "bottomMargin"),
                getDouble(properties, "minWidth"),
                getDouble(properties, "minHeight"),
                getColor(properties, "backgroundColor"),
                getDouble(properties, "backgroundOpacity"),
                getColor(properties, "borderColor"),
                getDouble(properties, "borderWidth"),
                getDouble(properties, "cornerRadius"),
                getColor(properties, "speakerTextColor"),
                getColor(properties, "dialogueTextColor"),
                getColor(properties, "hintTextColor"),
                getInt(properties, "speakerFontSize"),
                getInt(properties, "dialogueFontSize"),
                getInt(properties, "hintFontSize"),
                Duration.millis(getInt(properties, "typewriterDelayMillis")),
                getRequiredString(properties, "lineSeparator"),
                getRequiredString(properties, "advanceHint")
        );
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream = DialogueConfigLoader.class.getResourceAsStream(CONFIG_PATH)) {
            if (inputStream == null) {
                throw new IllegalStateException("Dialogue config not found: " + CONFIG_PATH);
            }

            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load dialogue config: " + CONFIG_PATH, e);
        }
    }

    private static String getRequiredString(Properties properties, String key) {
        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Dialogue config key '" + key + "' is missing or blank");
        }

        return value.trim();
    }

    private static int getInt(Properties properties, String key) {
        String value = getRequiredString(properties, key);

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(
                    "Dialogue config key '" + key + "' must be an integer",
                    e
            );
        }
    }

    private static double getDouble(Properties properties, String key) {
        String value = getRequiredString(properties, key);

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(
                    "Dialogue config key '" + key + "' must be a number",
                    e
            );
        }
    }

    private static Color getColor(Properties properties, String key) {
        String value = getRequiredString(properties, key);

        try {
            return Color.web(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(
                    "Dialogue config key '" + key + "' must be a valid color",
                    e
            );
        }
    }
}