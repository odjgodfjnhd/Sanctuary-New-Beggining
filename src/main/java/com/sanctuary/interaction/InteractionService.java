package com.sanctuary.interaction;

import com.almasb.fxgl.entity.Entity;
import com.sanctuary.core.GameService;
import com.sanctuary.dialogue.DialogueService;
import com.sanctuary.entity.EntityType;
import com.sanctuary.entity.npc.NPCComponent;
import com.sanctuary.game.GameSession;

import java.util.Comparator;
import java.util.logging.Logger;

public class InteractionService implements GameService {

    private static final Logger LOGGER = Logger.getLogger(InteractionService.class.getName());

    private final GameSession session;
    private final DialogueService dialogueService;

    public InteractionService(GameSession session, DialogueService dialogueService) {
        this.session = session;
        this.dialogueService = dialogueService;
    }

    public void interact() {
        Entity player = session.getPlayer();

        if (player == null || !player.isActive()) {
            return;
        }

        findNearestInteractableNpc(player)
                .ifPresentOrElse(
                        npc -> startNpcDialogue(player, npc),
                        () -> LOGGER.info("Nothing to interact with")
                );
    }

    private java.util.Optional<NPCComponent> findNearestInteractableNpc(Entity player) {
        return player.getWorld()
                .getEntitiesByType(EntityType.NPC)
                .stream()
                .filter(Entity::isActive)
                .filter(npc -> npc.hasComponent(NPCComponent.class))
                .map(npc -> npc.getComponent(NPCComponent.class))
                .filter(npcComponent -> npcComponent.canInteract(player))
                .min(Comparator.comparingDouble(npcComponent ->
                        distanceBetween(player, npcComponent.getEntity())
                ));
    }

    private void startNpcDialogue(Entity player, NPCComponent npcComponent) {
        npcComponent.interact(player);

        dialogueService.startDialogue(
                npcComponent.getName(),
                npcComponent.getDialogue()
        );
    }

    private double distanceBetween(Entity first, Entity second) {
        double dx = first.getCenter().getX() - second.getCenter().getX();
        double dy = first.getCenter().getY() - second.getCenter().getY();

        return Math.sqrt(dx * dx + dy * dy);
    }
}