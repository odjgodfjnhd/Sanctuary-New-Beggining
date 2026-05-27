package com.sanctuary.camera;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.sanctuary.world.WorldMap;

public class CameraController {

    public void bindToPlayer(Entity player, WorldMap worldMap) {
        if (player == null || !player.isActive() || worldMap == null) {
            return;
        }

        var viewport = FXGL.getGameScene().getViewport();

        double viewportCenterX = FXGL.getAppWidth() / 2.0;
        double viewportCenterY = FXGL.getAppHeight() / 2.0;

        viewport.bindToEntity(
                player,
                viewportCenterX,
                viewportCenterY
        );

        int worldWidth = (int) worldMap.getPixelWidth();
        int worldHeight = (int) worldMap.getPixelHeight();

        viewport.setBounds(0, 0, worldWidth, worldHeight);
    }
}