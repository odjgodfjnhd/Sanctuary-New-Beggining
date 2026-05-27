package com.sanctuary.interaction;

import com.almasb.fxgl.entity.Entity;

public interface Interactable {

    boolean canInteract(Entity player);

    void interact(Entity player);

    String getInteractionPrompt();
}