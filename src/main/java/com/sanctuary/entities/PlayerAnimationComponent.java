package com.sanctuary.entities;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

public class PlayerAnimationComponent extends Component {

    private AnimatedTexture texture;
    private MovementComponent movement;

    // Анимации ходьбы
    private AnimationChannel walkDown;
    private AnimationChannel walkUp;
    private AnimationChannel walkLeft;
    private AnimationChannel walkRight;

    // Анимации бездействия
    private AnimationChannel idleDown;
    private AnimationChannel idleUp;
    private AnimationChannel idleLeft;
    private AnimationChannel idleRight;

    public PlayerAnimationComponent(AnimatedTexture texture,
                                    AnimationChannel walkDown,
                                    AnimationChannel walkUp,
                                    AnimationChannel walkLeft,
                                    AnimationChannel walkRight,
                                    AnimationChannel idleDown,
                                    AnimationChannel idleUp,
                                    AnimationChannel idleLeft,
                                    AnimationChannel idleRight) {
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
        movement = entity.getComponent(MovementComponent.class);
    }

    @Override
    public void onUpdate(double tpf) {
        if (movement == null) return;

        double moveX = movement.getMoveX();
        double moveY = movement.getMoveY();

        boolean isMoving = (moveX != 0 || moveY != 0);

        // Определяем направление и выбираем анимацию
        if (isMoving) {
            // Движение вверх
            if (moveY < 0) {
                setAnimation(walkUp);
            }
            // Движение вниз
            else if (moveY > 0) {
                setAnimation(walkDown);
            }
            // Движение вправо
            else if (moveX > 0) {
                setAnimation(walkRight);
            }
            // Движение влево
            else if (moveX < 0) {
                setAnimation(walkLeft);
            }
        } else {
            // Стоим на месте — показываем соответствующий idle
            AnimationChannel currentIdle = getIdleForCurrentDirection();
            if (currentIdle != null && texture.getAnimationChannel() != currentIdle) {
                texture.loopAnimationChannel(currentIdle);
            }
        }
    }

    private void setAnimation(AnimationChannel channel) {
        if (texture.getAnimationChannel() != channel) {
            texture.loopAnimationChannel(channel);
        }
    }

    private AnimationChannel getIdleForCurrentDirection() {
        // Определяем, в какую сторону сейчас смотрит персонаж
        AnimationChannel current = texture.getAnimationChannel();

        if (current == walkDown || current == idleDown) return idleDown;
        if (current == walkUp || current == idleUp) return idleUp;
        if (current == walkLeft || current == idleLeft) return idleLeft;
        if (current == walkRight || current == idleRight) return idleRight;

        return idleDown; // по умолчанию
    }
}