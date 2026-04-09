package com.sanctuary.entities;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class GameEntityFactory implements EntityFactory {

    // Для точки спавна с типом PLAYER_SPAWN
    @Spawns("PLAYER_SPAWN")
    public Entity newPlayerSpawn(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.PLAYER_SPAWN)
                .build();
    }

    // Для любых других объектов из слоя spawn
    @Spawns("spawn")
    public Entity newSpawnPoint(SpawnData data) {
        // Просто игнорируем или создаём маркер
        return entityBuilder(data)
                .type(EntityType.PLAYER_SPAWN)
                .build();
    }
}