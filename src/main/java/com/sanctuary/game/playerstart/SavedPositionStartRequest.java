package com.sanctuary.game.playerstart;

public record SavedPositionStartRequest(
        double x,
        double y
) implements PlayerStartRequest {
}