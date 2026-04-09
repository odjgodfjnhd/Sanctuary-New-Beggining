package com.sanctuary.entities;

import com.almasb.fxgl.entity.component.Component;

public class MovementComponent extends Component {

    private double speed = 200;
    private Double targetX = null;
    private Double targetY = null;
    private boolean movingToTarget = false;

    private double moveX = 0;
    private double moveY = 0;

    private double width;
    private double height;

    public MovementComponent(double size) {
        this.width = size;
        this.height = size;
    }

    public MovementComponent() {
        this.width = 32;
        this.height = 32;
    }

    @Override
    public void onUpdate(double tpf) {
        double newX = entity.getX();
        double newY = entity.getY();

        if (moveX != 0 || moveY != 0) {
            double distance = speed * tpf;
            newX += moveX * distance;
            newY += moveY * distance;
            entity.setPosition(newX, newY);
        }
        else if (movingToTarget && targetX != null && targetY != null) {
            double currentX = entity.getX();
            double currentY = entity.getY();
            double deltaX = targetX - currentX;
            double deltaY = targetY - currentY;
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (distance < 2) {
                entity.setPosition(targetX, targetY);
                movingToTarget = false;
                targetX = null;
                targetY = null;
            } else {
                double step = speed * tpf;
                double ratio = step / distance;
                if (ratio > 1) ratio = 1;
                newX = currentX + deltaX * ratio;
                newY = currentY + deltaY * ratio;
                entity.setPosition(newX, newY);
            }
        }
    }

    public void moveTo(double x, double y) {
        moveX = 0;
        moveY = 0;
        this.targetX = x;
        this.targetY = y;
        this.movingToTarget = true;
    }

    public void setMoveX(double x) { this.moveX = x; }
    public void setMoveY(double y) { this.moveY = y; }

    public void cancelMoveToTarget() {
        this.movingToTarget = false;
        this.targetX = null;
        this.targetY = null;
    }

    public double getMoveX() { return moveX; }
    public double getMoveY() { return moveY; }
}