package com.sanctuary.interaction;

import com.almasb.fxgl.entity.Entity;
import com.sanctuary.core.GameService;
import com.sanctuary.entity.EntityType;
import com.sanctuary.entity.npc.NPCComponent;
import com.sanctuary.game.GameSession;

import java.util.Comparator;
import java.util.logging.Logger;

public class InteractionService implements GameService {

    private static final Logger LOGGER = Logger.getLogger(InteractionService.class.getName());

    private final GameSession session;

    public InteractionService(GameSession session) {
        this.session = session;
    }

    public void interact() {
        Entity player = session.getPlayer();

        if (player == null || !player.isActive()) {
            return;
        }

        player.getWorld()
                .getEntitiesByType(EntityType.NPC)
                .stream()
                .filter(Entity::isActive)
                .filter(npc -> npc.hasComponent(NPCComponent.class))
                .map(npc -> npc.getComponent(NPCComponent.class))
                .filter(interactable -> interactable.canInteract(player))
                .min(Comparator.comparingDouble(interactable ->
                        distanceBetween(player, interactable.getEntity())
                ))
                .ifPresentOrElse(
                        interactable -> interactable.interact(player),
                        () -> LOGGER.info("Nothing to interact with")
                );
    }

    private double distanceBetween(Entity first, Entity second) {
        double dx = first.getCenter().getX() - second.getCenter().getX();
        double dy = first.getCenter().getY() - second.getCenter().getY();

        return Math.sqrt(dx * dx + dy * dy);
    }
}