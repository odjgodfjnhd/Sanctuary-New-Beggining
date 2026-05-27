package com.sanctuary.entity.player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.sanctuary.config.AssetPaths;
import com.sanctuary.config.PlayerConfig;
import com.sanctuary.entity.EntityType;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public final class Player {

    private Player() {
    }

    public static Entity create(double x, double y) {
        var spriteSheet = FXGL.image(AssetPaths.PLAYER_TEXTURE);

        Duration walkDuration = Duration.seconds(PlayerConfig.WALK_ANIMATION_DURATION);
        Duration idleDuration = Duration.seconds(PlayerConfig.IDLE_ANIMATION_DURATION);

        AnimationChannel walkDown = createAnimationChannel(spriteSheet, walkDuration, 0, 3);
        AnimationChannel idleDown = createAnimationChannel(spriteSheet, idleDuration, 0, 0);

        AnimationChannel walkRight = createAnimationChannel(spriteSheet, walkDuration, 3, 6);
        AnimationChannel idleRight = createAnimationChannel(spriteSheet, idleDuration, 3, 3);

        AnimationChannel walkUp = createAnimationChannel(spriteSheet, walkDuration, 5, 8);
        AnimationChannel idleUp = createAnimationChannel(spriteSheet, idleDuration, 5, 5);

        AnimationChannel walkLeft = createAnimationChannel(spriteSheet, walkDuration, 7, 10);
        AnimationChannel idleLeft = createAnimationChannel(spriteSheet, idleDuration, 7, 7);

        AnimatedTexture texture = new AnimatedTexture(idleDown);
        texture.setScaleX(PlayerConfig.SPRITE_SCALE);
        texture.setScaleY(PlayerConfig.SPRITE_SCALE);
        texture.loop();

        return entityBuilder()
                .type(EntityType.PLAYER)
                .at(x, y)
                .view(texture)
                .bbox(new HitBox(
                        "PLAYER_BODY",
                        new Point2D(
                                PlayerConfig.HITBOX_OFFSET_X,
                                PlayerConfig.HITBOX_OFFSET_Y
                        ),
                        BoundingShape.box(
                                PlayerConfig.HITBOX_WIDTH,
                                PlayerConfig.HITBOX_HEIGHT
                        )
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

    private static AnimationChannel createAnimationChannel(
            javafx.scene.image.Image spriteSheet,
            Duration duration,
            int startFrame,
            int endFrame
    ) {
        return new AnimationChannel(
                spriteSheet,
                PlayerConfig.FRAMES_PER_ROW,
                PlayerConfig.FRAME_WIDTH,
                PlayerConfig.FRAME_HEIGHT,
                duration,
                startFrame,
                endFrame
        );
    }
}