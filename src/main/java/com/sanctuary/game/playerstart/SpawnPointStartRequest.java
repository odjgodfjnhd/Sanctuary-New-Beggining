package com.sanctuary.game.playerstart;

public record SpawnPointStartRequest(String spawnId) implements PlayerStartRequest {

    public SpawnPointStartRequest {
        if (spawnId == null || spawnId.isBlank()) {
            throw new IllegalArgumentException("Spawn id must not be blank");
        }
    }
}