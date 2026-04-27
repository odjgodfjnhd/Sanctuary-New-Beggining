package com.sanctuary.entity.player;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.sanctuary.entity.common.Direction;

public class PlayerAnimationComponent extends Component {

    private final AnimatedTexture texture;

    private final AnimationChannel walkDown;
    private final AnimationChannel walkUp;
    private final AnimationChannel walkLeft;
    private final AnimationChannel walkRight;

    private final AnimationChannel idleDown;
    private final AnimationChannel idleUp;
    private final AnimationChannel idleLeft;
    private final AnimationChannel idleRight;

    private MovementComponent movementComponent;

    public PlayerAnimationComponent(
            AnimatedTexture texture,
            AnimationChannel walkDown,
            AnimationChannel walkUp,
            AnimationChannel walkLeft,
            AnimationChannel walkRight,
            AnimationChannel idleDown,
            AnimationChannel idleUp,
            AnimationChannel idleLeft,
            AnimationChannel idleRight
    ) {
        this.texture = texture;
        this.walkDown = walkDown;
        this.walkUp = walkUp;
        this.walkLeft = walkLeft;
        this.walkRight = walkRight;
        this.idleDown = idleDown;
        this.idleUp = idleUp;
        this.idleLeft = idleLeft;
        this.idleRight = idleRight;
    }

    @Override
    public void onAdded() {
        movementComponent = entity.getComponent(MovementComponent.class);
        texture.loopAnimationChannel(idleDown);
    }

    @Override
    public void onUpdate(double tpf) {
        if (movementComponent == null) {
            return;
        }

        Direction direction = movementComponent.getFacingDirection();

        if (movementComponent.isMoving()) {
            playWalk(direction);
        } else {
            playIdle(direction);
        }
    }

    private void playWalk(Direction direction) {
        switch (direction) {
            case UP -> loopIfNeeded(walkUp);
            case DOWN -> loopIfNeeded(walkDown);
            case LEFT -> loopIfNeeded(walkLeft);
            case RIGHT -> loopIfNeeded(walkRight);
        }
    }

    private void playIdle(Direction direction) {
        switch (direction) {
            case UP -> loopIfNeeded(idleUp);
            case DOWN -> loopIfNeeded(idleDown);
            case LEFT -> loopIfNeeded(idleLeft);
            case RIGHT -> loopIfNeeded(idleRight);
        }
    }

    private void loopIfNeeded(AnimationChannel channel) {
        if (texture.getAnimationChannel() != channel) {
            texture.loopAnimationChannel(channel);
        }
    }
}