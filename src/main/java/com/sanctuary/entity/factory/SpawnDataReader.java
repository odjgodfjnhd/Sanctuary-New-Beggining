package com.sanctuary.entity.factory;

import com.almasb.fxgl.entity.SpawnData;

public final class SpawnDataReader {

    private SpawnDataReader() {
    }

    public static double getRequiredDouble(SpawnData data, String key) {
        if (!data.hasKey(key)) {
            throw new IllegalStateException("SpawnData key '" + key + "' is missing");
        }

        try {
            Number value = data.get(key);
            return value.doubleValue();
        } catch (ClassCastException e) {
            throw new IllegalStateException(
                    "SpawnData key '" + key + "' must be a Number",
                    e
            );
        }
    }

    public static double getDoubleOrDefault(SpawnData data, String key, double defaultValue) {
        if (!data.hasKey(key)) {
            return defaultValue;
        }

        try {
            Number value = data.get(key);
            return value.doubleValue();
        } catch (ClassCastException e) {
            throw new IllegalStateException(
                    "SpawnData key '" + key + "' must be a Number",
                    e
            );
        }
    }

    public static String getRequiredString(SpawnData data, String key) {
        if (!data.hasKey(key)) {
            throw new IllegalStateException("SpawnData key '" + key + "' is missing");
        }

        try {
            String value = data.get(key);

            if (value.isBlank()) {
                throw new IllegalStateException("SpawnData key '" + key + "' is blank");
            }

            return value;
        } catch (ClassCastException e) {
            throw new IllegalStateException(
                    "SpawnData key '" + key + "' must be a String",
                    e
            );
        }
    }

    public static String getStringOrDefault(SpawnData data, String key, String defaultValue) {
        if (!data.hasKey(key)) {
            return defaultValue;
        }

        try {
            String value = data.get(key);

            if (value.isBlank()) {
                return defaultValue;
            }

            return value;
        } catch (ClassCastException e) {
            throw new IllegalStateException(
                    "SpawnData key '" + key + "' must be a String",
                    e
            );
        }
    }
}