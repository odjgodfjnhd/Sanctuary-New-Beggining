package com.sanctuary.interaction;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.sanctuary.core.GameService;
import com.sanctuary.dialogue.DialogueService;
import com.sanctuary.entity.EntityType;
import com.sanctuary.entity.npc.NPCComponent;
import com.sanctuary.entity.rest.RestPointComponent;
import com.sanctuary.game.GameSession;
import com.sanctuary.save.SaveService;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.logging.Logger;

public class InteractionService implements GameService {

    private static final Logger LOGGER = Logger.getLogger(InteractionService.class.getName());

    private final GameSession session;
    private final DialogueService dialogueService;
    private final SaveService saveService;

    public InteractionService(
            GameSession session,
            DialogueService dialogueService,
            SaveService saveService
    ) {
        this.session = session;
        this.dialogueService = dialogueService;
        this.saveService = saveService;
    }

    public void interact() {
        Entity player = session.getPlayer();

        if (player == null || !player.isActive()) {
            return;
        }

        findNearestInteractable(player)
                .ifPresentOrElse(
                        interactionTarget -> interactWithTarget(player, interactionTarget),
                        () -> LOGGER.info("Nothing to interact with")
                );
    }

    private Optional<InteractionTarget> findNearestInteractable(Entity player) {
        return Stream.concat(
                        player.getWorld().getEntitiesByType(EntityType.NPC).stream(),
                        player.getWorld().getEntitiesByType(EntityType.REST_POINT).stream()
                )
                .filter(Entity::isActive)
                .map(this::toInteractionTarget)
                .flatMap(Optional::stream)
                .filter(target -> target.interactable().canInteract(player))
                .min(Comparator.comparingDouble(target ->
                        distanceBetween(player, target.component().getEntity())
                ));
    }

    private Optional<InteractionTarget> toInteractionTarget(Entity entity) {
        if (entity.hasComponent(NPCComponent.class)) {
            NPCComponent component = entity.getComponent(NPCComponent.class);
            return Optional.of(new InteractionTarget(component, component));
        }

        if (entity.hasComponent(RestPointComponent.class)) {
            RestPointComponent component = entity.getComponent(RestPointComponent.class);
            return Optional.of(new InteractionTarget(component, component));
        }

        return Optional.empty();
    }

    private void interactWithTarget(Entity player, InteractionTarget target) {
        target.interactable().interact(player);

        if (target.interactable() instanceof NPCComponent npcComponent) {
            startNpcDialogue(npcComponent);
            return;
        }

        if (target.interactable() instanceof RestPointComponent restPointComponent) {
            restAtPoint(restPointComponent);
        }
    }

    private void startNpcDialogue(NPCComponent npcComponent) {
        dialogueService.startDialogue(
                npcComponent.getName(),
                npcComponent.getDialogue()
        );
    }

    private void restAtPoint(RestPointComponent restPointComponent) {
        saveService.saveCurrentGame();

        dialogueService.startDialogue(
                restPointComponent.getName(),
                restPointComponent.getDialogue()
        );
    }

    private double distanceBetween(Entity first, Entity second) {
        double dx = first.getCenter().getX() - second.getCenter().getX();
        double dy = first.getCenter().getY() - second.getCenter().getY();

        return Math.sqrt(dx * dx + dy * dy);
    }

    private record InteractionTarget(Component component, Interactable interactable) {
    }
}
