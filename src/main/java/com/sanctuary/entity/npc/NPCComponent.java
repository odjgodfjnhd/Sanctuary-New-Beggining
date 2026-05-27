package com.sanctuary.entity.npc;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.sanctuary.config.InteractionConfig;
import com.sanctuary.interaction.Interactable;

import java.util.logging.Logger;

public class NPCComponent extends Component implements Interactable {

    private static final Logger LOGGER = Logger.getLogger(NPCComponent.class.getName());

    private final String name;
    private final String dialogue;

    public NPCComponent(String name, String dialogue) {
        this.name = name;
        this.dialogue = dialogue;
    }

    @Override
    public boolean canInteract(Entity player) {
        return distanceBetween(player, entity) <= InteractionConfig.DEFAULT_INTERACTION_DISTANCE;
    }

    @Override
    public void interact(Entity player) {
        LOGGER.info(() -> name + ": " + dialogue);
    }

    @Override
    public String getInteractionPrompt() {
        return "Talk to " + name;
    }

    public String getName() {
        return name;
    }

    public String getDialogue() {
        return dialogue;
    }

    private double distanceBetween(Entity first, Entity second) {
        double dx = first.getCenter().getX() - second.getCenter().getX();
        double dy = first.getCenter().getY() - second.getCenter().getY();

        return Math.sqrt(dx * dx + dy * dy);
    }
}