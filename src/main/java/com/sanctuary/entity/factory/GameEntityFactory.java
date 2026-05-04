package com.sanctuary.entity.factory;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.sanctuary.entity.EntityType;
import com.sanctuary.world.TransitionComponent;

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

    @Spawns("MAP_TRANSITION")
    public Entity newMapTransition(SpawnData data) {
        double width = getNumber(data, "width");
        double height = getNumber(data, "height");

        String targetMap = getString(data, "targetMap");
        String targetSpawn = getString(data, "targetSpawn");

        return entityBuilder(data)
                .type(EntityType.MAP_TRANSITION)
                .bbox(BoundingShape.box(width, height))
                .collidable()
                .with(new TransitionComponent(targetMap, targetSpawn))
                .build();
    }

    private double getNumber(SpawnData data, String key) {
        Object value = data.get(key);

        if (value instanceof Number number) {
            return number.doubleValue();
        }

        throw new IllegalStateException("SpawnData key '" + key + "' is not numeric: " + value);
    }

    private String getString(SpawnData data, String key) {
        Object value = data.get(key);

        if (value == null) {
            throw new IllegalStateException("SpawnData key '" + key + "' is missing");
        }

        String result = value.toString();

        if (result.isBlank()) {
            throw new IllegalStateException("SpawnData key '" + key + "' is blank");
        }

        return result;
    }
}