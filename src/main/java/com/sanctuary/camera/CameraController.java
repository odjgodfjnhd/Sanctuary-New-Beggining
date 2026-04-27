package com.sanctuary.camera;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.sanctuary.config.GameConfig;
import com.sanctuary.world.WorldMap;

public class CameraController {

    public void bindToPlayer(Entity player, WorldMap worldMap) {
        if (player == null || !player.isActive() || worldMap == null) {
            return;
        }

        var viewport = FXGL.getGameScene().getViewport();

        viewport.bindToEntity(
                player,
                GameConfig.APP_WIDTH / 2.0,
                GameConfig.APP_HEIGHT / 2.0
        );

        int worldWidth = (int) worldMap.getPixelWidth();
        int worldHeight = (int) worldMap.getPixelHeight();

        viewport.setBounds(0, 0, worldWidth, worldHeight);
    }
}