package com.sanctuary.maps;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.Entity;
import com.sanctuary.SanctuaryApp;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TransitionTrigger extends Component {

    private String targetMap;
    private int spawnX, spawnY;

    public TransitionTrigger(String targetMap, int spawnX, int spawnY) {
        this.targetMap = targetMap;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }

    @Override
    public void onUpdate(double tpf) {
        Entity player = FXGL.getGameWorld().getSingleton(com.sanctuary.entities.EntityType.PLAYER);
        if (player != null && entity.isColliding(player)) {
            System.out.println("Переход на карту: " + targetMap);
            // Очищаем текущую карту
            FXGL.getGameWorld().getEntities().forEach(e -> {
                if (e != player) e.removeFromWorld();
            });
            // Загружаем новую карту
            GameMap newMap = new GameMap(targetMap);
            newMap.load();
            player.setPosition(spawnX, spawnY);
        }
    }
}