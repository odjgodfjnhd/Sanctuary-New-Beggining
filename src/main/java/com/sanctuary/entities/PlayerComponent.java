package com.sanctuary.entities;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.input.MouseButton;

public class PlayerComponent extends Component {

    private MovementComponent movement;

    @Override
    public void onAdded() {
        movement = entity.getComponent(MovementComponent.class);

        FXGL.getInput().addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double clickX = event.getX();
                double clickY = event.getY();
                if (movement != null) {
                    movement.moveTo(clickX, clickY);
                }
            }
        });
    }
}