package com.sanctuary.world;

import com.almasb.fxgl.entity.component.Component;

public class TransitionComponent extends Component {

    private final String targetMapId;
    private final String targetSpawnId;

    public TransitionComponent(String targetMapId, String targetSpawnId) {
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