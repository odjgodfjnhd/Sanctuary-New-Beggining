package com.sanctuary.entity.rest;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.sanctuary.entity.EntityType;
import com.sanctuary.entity.factory.SpawnDataReader;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class RestPointEntityBuilder {

    private static final String DEFAULT_NAME = "Rest Point";

    private static final String DEFAULT_DIALOGUE =
            "Ты отдыхаешь.|Мир вокруг становится спокойнее.|Игра сохранена.";

    public Entity build(SpawnData data) {
        double width = SpawnDataReader.getRequiredDouble(data, "width");
        double height = SpawnDataReader.getRequiredDouble(data, "height");

        String name = SpawnDataReader.getStringOrDefault(data, "name", DEFAULT_NAME);
        String dialogue = SpawnDataReader.getStringOrDefault(data, "dialogue", DEFAULT_DIALOGUE);

        return entityBuilder(data)
                .type(EntityType.REST_POINT)
                .bbox(BoundingShape.box(width, height))
                .with(new RestPointComponent(name, dialogue))
                .build();
    }
}