package com.sanctuary.interaction;

import com.almasb.fxgl.entity.Entity;
import com.sanctuary.entity.EntityType;
import com.sanctuary.entity.npc.NPCComponent;
import com.sanctuary.game.GameSession;

import java.util.Comparator;

public class InteractionService {

    private static final double INTERACTION_DISTANCE = 48.0;

    private final GameSession session;

    public InteractionService(GameSession session) {
        this.session = session;
    }

    public void interact() {
        Entity player = session.getPlayer();

        if (player == null || !player.isActive()) {
            return;
        }

        session.getPlayer()
                .getWorld()
                .getEntitiesByType(EntityType.NPC)
                .stream()
                .filter(Entity::isActive)
                .filter(npc -> distanceBetween(player, npc) <= INTERACTION_DISTANCE)
                .min(Comparator.comparingDouble(npc -> distanceBetween(player, npc)))
                .ifPresentOrElse(
                        this::interactWithNpc,
                        () -> System.out.println("Nothing to interact with")
                );
    }

    private void interactWithNpc(Entity npc) {
        NPCComponent npcComponent = npc.getComponent(NPCComponent.class);

        System.out.println(npcComponent.getName() + ": " + npcComponent.getDialogue());
    }

    private double distanceBetween(Entity first, Entity second) {
        double dx = first.getCenter().getX() - second.getCenter().getX();
        double dy = first.getCenter().getY() - second.getCenter().getY();

        return Math.sqrt(dx * dx + dy * dy);
    }
}