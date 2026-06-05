package com.sanctuary.entity.rest;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.sanctuary.config.InteractionConfig;
import com.sanctuary.interaction.Interactable;

import java.util.logging.Logger;

public class RestPointComponent extends Component implements Interactable {

    private static final Logger LOGGER = Logger.getLogger(RestPointComponent.class.getName());

    @Override
    public boolean canInteract(Entity player) {
        return distanceBetween(player, entity) <= InteractionConfig.DEFAULT_INTERACTION_DISTANCE;
    }

    @Override
    public void interact(Entity player) {
        RestPointData restPointData = RestPointProperties.getData(entity);

        LOGGER.info(() -> "Interaction requested with rest point: "
                + restPointData.displayName());
    }

    @Override
    public String getInteractionPrompt() {
        RestPointData restPointData = RestPointProperties.getData(entity);

        return "Rest at " + restPointData.displayName();
    }

    private double distanceBetween(Entity first, Entity second) {
        double dx = first.getCenter().getX() - second.getCenter().getX();
        double dy = first.getCenter().getY() - second.getCenter().getY();

        return Math.sqrt(dx * dx + dy * dy);
    }
}
