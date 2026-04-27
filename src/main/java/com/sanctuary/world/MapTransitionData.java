package com.sanctuary.world;

public final class MapTransitionData {

    private final String targetMapId;
    private final String targetSpawnId;

    public MapTransitionData(String targetMapId, String targetSpawnId) {
        this.targetMapId = targetMapId;
        this.targetSpawnId = targetSpawnId;
    }

    public String getTargetMapId() {
        return targetMapId;
    }

    public String getTargetSpawnId() {
        return targetSpawnId;
    }
}