package com.sanctuary.entity.npc;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.sanctuary.config.NpcConfig;
import com.sanctuary.entity.EntityType;
import com.sanctuary.entity.common.BlockingComponent;
import com.sanctuary.entity.common.OpacityPulseComponent;
import com.sanctuary.entity.factory.SpawnDataReader;
import com.sanctuary.entity.view.EntityViewFactory;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class NpcEntityBuilder {

    private static final String WIDTH_PROPERTY = "width";
    private static final String HEIGHT_PROPERTY = "height";
    private static final String NAME_PROPERTY = "name";
    private static final String DIALOGUE_PROPERTY = "dialogue";

    public Entity build(SpawnData data) {
        double width = SpawnDataReader.getDoubleOrDefault(
                data,
                WIDTH_PROPERTY,
                NpcConfig.DEFAULT_WIDTH
        );
        double height = SpawnDataReader.getDoubleOrDefault(
                data,
                HEIGHT_PROPERTY,
                NpcConfig.DEFAULT_HEIGHT
        );

        String name = SpawnDataReader.getStringOrDefault(
                data,
                NAME_PROPERTY,
                NpcConfig.DEFAULT_NAME
        );
        String dialogue = SpawnDataReader.getStringOrDefault(
                data,
                DIALOGUE_PROPERTY,
                NpcConfig.DEFAULT_DIALOGUE
        );

        Rectangle outline = EntityViewFactory.createOutlineRectangle(
                width,
                height,
                NpcConfig.OUTLINE_COLOR,
                NpcConfig.OUTLINE_STROKE_WIDTH,
                NpcConfig.OUTLINE_INITIAL_OPACITY
        );

        return entityBuilder(data)
                .type(EntityType.NPC)
                .view(outline)
                .bbox(BoundingShape.box(width, height))
                .collidable()
                .with(new BlockingComponent())
                .with(new NPCComponent(name, dialogue))
                .with(new OpacityPulseComponent(
                        outline,
                        NpcConfig.PULSE_DURATION,
                        NpcConfig.PULSE_FROM_OPACITY,
                        NpcConfig.PULSE_TO_OPACITY
                ))
                .build();
    }
}