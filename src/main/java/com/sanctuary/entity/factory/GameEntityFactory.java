package com.sanctuary.entity.factory;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.sanctuary.entity.EntityType;
import com.sanctuary.entity.npc.NPCComponent;
import com.sanctuary.world.TransitionComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

    @Spawns("NPC")
    public Entity newNpc(SpawnData data) {
        double width = getNumberOrDefault(data, "width", 32.0);
        double height = getNumberOrDefault(data, "height", 32.0);

        String name = getStringOrDefault(data, "name", "NPC");
        String dialogue = getStringOrDefault(data, "dialogue", "Hello.");

        // Рамка без заливки
        javafx.scene.shape.Rectangle outline = new javafx.scene.shape.Rectangle(width, height);
        outline.setFill(javafx.scene.paint.Color.TRANSPARENT);
        outline.setStroke(javafx.scene.paint.Color.LIMEGREEN);
        outline.setStrokeWidth(2);
        outline.setOpacity(0.25);

        // Пульсация прозрачности
        javafx.animation.FadeTransition pulse = new javafx.animation.FadeTransition(
                javafx.util.Duration.seconds(0.8),
                outline
        );
        pulse.setFromValue(0.2);
        pulse.setToValue(1.0);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(javafx.animation.Animation.INDEFINITE);
        pulse.play();

        return entityBuilder(data)
                .type(EntityType.NPC)
                .view(outline)
                .bbox(BoundingShape.box(width, height))
                .collidable()
                .with(new NPCComponent(name, dialogue))
                .build();
    }

    private double getNumber(SpawnData data, String key) {
        Object value = data.get(key);

        if (value instanceof Number number) {
            return number.doubleValue();
        }

        throw new IllegalStateException("SpawnData key '" + key + "' is not numeric: " + value);
    }

    private double getNumberOrDefault(SpawnData data, String key, double defaultValue) {
        Object value = data.get(key);

        if (value instanceof Number number) {
            return number.doubleValue();
        }

        return defaultValue;
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

    private String getStringOrDefault(SpawnData data, String key, String defaultValue) {
        Object value = data.get(key);

        if (value == null) {
            return defaultValue;
        }

        String result = value.toString();

        if (result.isBlank()) {
            return defaultValue;
        }

        return result;
    }
}