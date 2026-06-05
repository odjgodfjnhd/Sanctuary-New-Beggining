package com.sanctuary.entity.rest;

import com.sanctuary.dialogue.DialogueScript;

public record RestPointData(
        String displayName,
        DialogueScript dialogueScript
) {
    public RestPointData {
        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("Rest point display name must be not empty");
        }

        if (dialogueScript == null) {
            throw new IllegalArgumentException("Rest point dialogue script must be not empty");
        }
    }
}
