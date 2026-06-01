package com.sanctuary.game.playerstart;

public sealed interface PlayerStartRequest
        permits SpawnPointStartRequest, SavedPositionStartRequest {
}