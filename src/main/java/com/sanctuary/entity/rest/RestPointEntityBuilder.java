package com.sanctuary.entity.rest;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.sanctuary.config.DialogueConfig;
import com.sanctuary.config.DialogueConfigLoader;
import com.sanctuary.dialogue.DialogueScript;
import com.sanctuary.dialogue.DialogueScriptParser;
import com.sanctuary.entity.EntityType;
import com.sanctuary.entity.factory.SpawnDataReader;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class RestPointEntityBuilder {

    private static final String DEFAULT_DISPLAY_NAME = "Rest Point";

    private static final String DEFAULT_DIALOGUE =
            "Ты отдыхаешь.|Мир вокруг становится спокойнее.|Игра сохранена.";

    private final DialogueConfig dialogueConfig = DialogueConfigLoader.load();

    public Entity build(SpawnData data) {
        double width = SpawnDataReader.getRequiredDouble(data, "width");
        double height = SpawnDataReader.getRequiredDouble(data, "height");

        String displayName = SpawnDataReader.getStringOrDefault(
                data,
                "name",
                DEFAULT_DISPLAY_NAME
        );

        String rawDialogue = SpawnDataReader.getStringOrDefault(
                data,
                "dialogue",
                DEFAULT_DIALOGUE
        );

        DialogueScript dialogueScript = DialogueScriptParser.parse(
                displayName,
                rawDialogue,
                dialogueConfig.lineSeparator()
        );

        return entityBuilder(data)
                .type(EntityType.REST_POINT)
                .bbox(BoundingShape.box(width, height))
                .with(new RestPointComponent(displayName, dialogueScript))
                .build();
    }
}
