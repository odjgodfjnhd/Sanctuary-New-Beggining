package com.sanctuary.entity.player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.sanctuary.config.PlayerConfig;
import com.sanctuary.entity.common.BlockingComponent;
import com.sanctuary.entity.common.Direction;

public class MovementComponent extends Component {

    private double moveX;
    private double moveY;
    private double speed = PlayerConfig.MOVE_SPEED;
    private Direction facingDirection = Direction.DOWN;

    private double minX = 0;
    private double minY = 0;
    private double maxX = Double.MAX_VALUE;
    private double maxY = Double.MAX_VALUE;

    @Override
    public void onUpdate(double tpf) {
        if (moveX == 0 && moveY == 0) {
            return;
        }

        double length = Math.sqrt(moveX * moveX + moveY * moveY);
        double normalizedX = moveX;
        double normalizedY = moveY;

        if (length > 0) {
            normalizedX /= length;
            normalizedY /= length;
        }

        double stepX = normalizedX * speed * tpf;
        double stepY = normalizedY * speed * tpf;

        moveAlongX(stepX);
        moveAlongY(stepY);

        updateFacingDirection();
    }

    private void moveAlongX(double stepX) {
        if (stepX == 0) {
            return;
        }

        double oldX = entity.getX();
        double nextX = clamp(oldX + stepX, minX, maxX);

        entity.setX(nextX);

        if (isCollidingWithBlockingEntity()) {
            entity.setX(oldX);
        }
    }

    private void moveAlongY(double stepY) {
        if (stepY == 0) {
            return;
        }

        double oldY = entity.getY();
        double nextY = clamp(oldY + stepY, minY, maxY);

        entity.setY(nextY);

        if (isCollidingWithBlockingEntity()) {
            entity.setY(oldY);
        }
    }

    private boolean isCollidingWithBlockingEntity() {
        return FXGL.getGameWorld()
                .getEntities()
                .stream()
                .filter(other -> other != entity)
                .filter(other -> other.isActive())
                .filter(other -> other.hasComponent(BlockingComponent.class))
                .anyMatch(other -> entity.isColliding(other));
    }

    public void setMovement(double moveX, double moveY) {
        this.moveX = moveX;
        this.moveY = moveY;

        if (moveX != 0 || moveY != 0) {
            updateFacingDirection();
        }
    }

    public void stop() {
        moveX = 0;
        moveY = 0;
    }

    public boolean isMoving() {
        return moveX != 0 || moveY != 0;
    }

    public double getMoveX() {
        return moveX;
    }

    public double getMoveY() {
        return moveY;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setWorldBounds(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = Math.max(minX, maxX);
        this.maxY = Math.max(minY, maxY);

        double clampedX = clamp(entity.getX(), this.minX, this.maxX);
        double clampedY = clamp(entity.getY(), this.minY, this.maxY);
        entity.setPosition(clampedX, clampedY);
    }

    private void updateFacingDirection() {
        if (Math.abs(moveX) > Math.abs(moveY)) {
            facingDirection = moveX > 0 ? Direction.RIGHT : Direction.LEFT;
        } else if (moveY != 0) {
            facingDirection = moveY > 0 ? Direction.DOWN : Direction.UP;
        }
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}