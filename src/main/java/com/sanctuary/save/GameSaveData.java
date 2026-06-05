package com.sanctuary.save;

import java.time.LocalDateTime;

public record GameSaveData(
        String mapId,
        double playerX,
        double playerY,
        LocalDateTime savedAt
) {
}