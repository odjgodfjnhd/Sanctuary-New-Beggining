package com.sanctuary.entity.player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.sanctuary.config.AssetPaths;
import com.sanctuary.config.GameConstants;
import com.sanctuary.entity.EntityType;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public final class Player {

    private Player() {
    }

    public static Entity create(double x, double y) {
        var spriteSheet = FXGL.image(AssetPaths.PLAYER_TEXTURE);

        Duration walkDuration = Duration.seconds(GameConstants.PLAYER_WALK_ANIMATION_DURATION);
        Duration idleDuration = Duration.seconds(GameConstants.PLAYER_IDLE_ANIMATION_DURATION);

        AnimationChannel walkDown = new AnimationChannel(
                spriteSheet,
                GameConstants.PLAYER_FRAMES_PER_ROW,
                GameConstants.PLAYER_FRAME_WIDTH,
                GameConstants.PLAYER_FRAME_HEIGHT,
                walkDuration,
                0,
                3
        );

        AnimationChannel idleDown = new AnimationChannel(
                spriteSheet,
                GameConstants.PLAYER_FRAMES_PER_ROW,
                GameConstants.PLAYER_FRAME_WIDTH,
                GameConstants.PLAYER_FRAME_HEIGHT,
                idleDuration,
                0,
                0
        );

        AnimationChannel walkRight = new AnimationChannel(
                spriteSheet,
                GameConstants.PLAYER_FRAMES_PER_ROW,
                GameConstants.PLAYER_FRAME_WIDTH,
                GameConstants.PLAYER_FRAME_HEIGHT,
                walkDuration,
                3,
                6
        );

        AnimationChannel idleRight = new AnimationChannel(
                spriteSheet,
                GameConstants.PLAYER_FRAMES_PER_ROW,
                GameConstants.PLAYER_FRAME_WIDTH,
                GameConstants.PLAYER_FRAME_HEIGHT,
                idleDuration,
                3,
                3
        );

        AnimationChannel walkUp = new AnimationChannel(
                spriteSheet,
                GameConstants.PLAYER_FRAMES_PER_ROW,
                GameConstants.PLAYER_FRAME_WIDTH,
                GameConstants.PLAYER_FRAME_HEIGHT,
                walkDuration,
                5,
                8
        );

        AnimationChannel idleUp = new AnimationChannel(
                spriteSheet,
                GameConstants.PLAYER_FRAMES_PER_ROW,
                GameConstants.PLAYER_FRAME_WIDTH,
                GameConstants.PLAYER_FRAME_HEIGHT,
                idleDuration,
                5,
                5
        );

        AnimationChannel walkLeft = new AnimationChannel(
                spriteSheet,
                GameConstants.PLAYER_FRAMES_PER_ROW,
                GameConstants.PLAYER_FRAME_WIDTH,
                GameConstants.PLAYER_FRAME_HEIGHT,
                walkDuration,
                7,
                10
        );

        AnimationChannel idleLeft = new AnimationChannel(
                spriteSheet,
                GameConstants.PLAYER_FRAMES_PER_ROW,
                GameConstants.PLAYER_FRAME_WIDTH,
                GameConstants.PLAYER_FRAME_HEIGHT,
                idleDuration,
                7,
                7
        );

        AnimatedTexture texture = new AnimatedTexture(idleDown);
        texture.setScaleX(GameConstants.PLAYER_SPRITE_SCALE);
        texture.setScaleY(GameConstants.PLAYER_SPRITE_SCALE);
        texture.loop();

        return entityBuilder()
                .type(EntityType.PLAYER)
                .at(x, y)
                .view(texture)
                .bbox(BoundingShape.box(
                        GameConstants.PLAYER_WIDTH,
                        GameConstants.PLAYER_HEIGHT
                ))
                .collidable()
                .with(new MovementComponent())
                .with(new PlayerComponent())
                .with(new PlayerAnimationComponent(
                        texture,
                        walkDown,
                        walkUp,
                        walkLeft,
                        walkRight,
                        idleDown,
                        idleUp,
                        idleLeft,
                        idleRight
                ))
                .buildAndAttach();
    }
}