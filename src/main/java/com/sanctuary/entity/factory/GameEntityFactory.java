package com.sanctuary.entity.factory;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.sanctuary.entity.EntityType;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class GameEntityFactory implements EntityFactory {

    @Spawns("PLAYER_SPAWN")
    public Entity newPlayerSpawnMarker(SpawnData data) {
        return entityBuilder(data).build();
    }

    @Spawns("WALL")
    public Entity newWall(SpawnData data) {
        double width = getNumber(data, "width");
        double height = getNumber(data, "height");

        return entityBuilder(data)
                .type(EntityType.WALL)
                .bbox(BoundingShape.box(width, height))
                .collidable()
                .build();
    }

    private double getNumber(SpawnData data, String key) {
        Object value = data.get(key);

        if (value instanceof Number number) {
            return number.doubleValue();
        }

        throw new IllegalStateException("SpawnData key '" + key + "' is not numeric: " + value);
    }
}