package com.sanctuary.entities;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.sanctuary.core.GameState;

public class PlayerSpawnComponent extends Component {

    @Override
    public void onAdded() {
        // Сохраняем координаты в GameState
        GameState.spawnX = entity.getX();
        GameState.spawnY = entity.getY();

        // Создаём игрока, если его ещё нет
        if (FXGL.getGameWorld().getSingleton(EntityType.PLAYER) == null) {
            Player.create(GameState.spawnX, GameState.spawnY);
        }

        // Удаляем объект спавна
        entity.removeFromWorld();
    }
}