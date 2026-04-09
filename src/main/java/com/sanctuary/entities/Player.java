package com.sanctuary.entities;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;
import com.almasb.fxgl.physics.BoundingShape;
import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class Player {

    private static final int FRAME_SIZE = 32;
    private static final int FRAMES_PER_ROW = 24;

    // Размер персонажа после масштабирования
    private static final double PLAYER_SIZE = 32; // 32 * 2 = 64

    private static AnimationChannel walkDown;
    private static AnimationChannel walkRight;
    private static AnimationChannel walkUp;
    private static AnimationChannel walkLeft;
    private static AnimationChannel idleDown;
    private static AnimationChannel idleRight;
    private static AnimationChannel idleUp;
    private static AnimationChannel idleLeft;

    private static AnimatedTexture animatedTexture;

    public static Entity create(double x, double y) {
        var spriteSheet = FXGL.image("Archer-Green.png");

        Duration walkDuration = Duration.seconds(0.1);
        Duration idleDuration = Duration.seconds(1);

        walkDown = new AnimationChannel(spriteSheet, FRAMES_PER_ROW, FRAME_SIZE, FRAME_SIZE, walkDuration, 0, 3);
        idleDown = new AnimationChannel(spriteSheet, FRAMES_PER_ROW, FRAME_SIZE, FRAME_SIZE, idleDuration, 0, 0);

        walkRight = new AnimationChannel(spriteSheet, FRAMES_PER_ROW, FRAME_SIZE, FRAME_SIZE, walkDuration, 3, 6);
        idleRight = new AnimationChannel(spriteSheet, FRAMES_PER_ROW, FRAME_SIZE, FRAME_SIZE, idleDuration, 3, 3);

        walkUp = new AnimationChannel(spriteSheet, FRAMES_PER_ROW, FRAME_SIZE, FRAME_SIZE, walkDuration, 5, 8);
        idleUp = new AnimationChannel(spriteSheet, FRAMES_PER_ROW, FRAME_SIZE, FRAME_SIZE, idleDuration, 5, 5);

        walkLeft = new AnimationChannel(spriteSheet, FRAMES_PER_ROW, FRAME_SIZE, FRAME_SIZE, walkDuration, 7, 10);
        idleLeft = new AnimationChannel(spriteSheet, FRAMES_PER_ROW, FRAME_SIZE, FRAME_SIZE, idleDuration, 7, 7);

        animatedTexture = new AnimatedTexture(idleDown);
        animatedTexture.loop();

        // Увеличиваем масштаб
        animatedTexture.setScaleX(2.0);
        animatedTexture.setScaleY(2.0);

        return entityBuilder()
                .at(x, y)
                .type(EntityType.PLAYER)
                .view(animatedTexture)                                    // отображаем текстуру
                .bbox(BoundingShape.box(PLAYER_SIZE, PLAYER_SIZE))   // коллизия 64x64
                .with(new MovementComponent(PLAYER_SIZE))                 // передаём размер в компонент движения
                .with(new PlayerComponent())
                .with(new PlayerAnimationComponent(animatedTexture,
                        walkDown, walkUp, walkLeft, walkRight,
                        idleDown, idleUp, idleLeft, idleRight))
                .buildAndAttach();
    }
}