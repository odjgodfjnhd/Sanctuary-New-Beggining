package com.sanctuary.world;

public enum TileObjectType {
    PLAYER_SPAWN,
    MAP_TRANSITION,
    WALL,
    UNKNOWN;

    public static TileObjectType fromString(String value) {
        if (value == null || value.isBlank()) {
            return UNKNOWN;
        }

        String normalized = value.trim()
                .replace(' ', '_')
                .replace('-', '_')
                .toUpperCase();

        return switch (normalized) {
            case "PLAYER_SPAWN" -> PLAYER_SPAWN;
            case "MAP_TRANSITION" -> MAP_TRANSITION;
            case "WALL" -> WALL;
            default -> UNKNOWN;
        };
    }
}