package com.sanctuary.entity.factory;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.sanctuary.entity.EntityType;
import com.sanctuary.entity.common.BlockingComponent;
import com.sanctuary.entity.npc.NpcEntityBuilder;
import com.sanctuary.entity.rest.RestPointEntityBuilder;
import com.sanctuary.world.TransitionComponent;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class GameEntityFactory implements EntityFactory {

    private final NpcEntityBuilder npcEntityBuilder = new NpcEntityBuilder();
    private final RestPointEntityBuilder restPointEntityBuilder = new RestPointEntityBuilder();

    @Spawns("PLAYER_SPAWN")
    public Entity newPlayerSpawnMarker(SpawnData data) {
        return entityBuilder(data).build();
    }

    @Spawns("WALL")
    public Entity newWall(SpawnData data) {
        double width = SpawnDataReader.getRequiredDouble(data, "width");
        double height = SpawnDataReader.getRequiredDouble(data, "height");

        return entityBuilder(data)
                .type(EntityType.WALL)
                .bbox(BoundingShape.box(width, height))
                .collidable()
                .with(new BlockingComponent())
                .build();
    }

    @Spawns("MAP_TRANSITION")
    public Entity newMapTransition(SpawnData data) {
        double width = SpawnDataReader.getRequiredDouble(data, "width");
        double height = SpawnDataReader.getRequiredDouble(data, "height");

        String targetMap = SpawnDataReader.getRequiredString(data, "targetMap");
        String targetSpawn = SpawnDataReader.getRequiredString(data, "targetSpawn");

        return entityBuilder(data)
                .type(EntityType.MAP_TRANSITION)
                .bbox(BoundingShape.box(width, height))
                .collidable()
                .with(new TransitionComponent(targetMap, targetSpawn))
                .build();
    }

    @Spawns("NPC")
    public Entity newNpc(SpawnData data) {
        return npcEntityBuilder.build(data);
    }

    @Spawns("REST_POINT")
    public Entity newRestPoint(SpawnData data) {
        return restPointEntityBuilder.build(data);
    }
}
