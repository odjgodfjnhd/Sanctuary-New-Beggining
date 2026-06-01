package com.sanctuary.dialogue;

import java.util.List;

public record DialogueScript(
        String speakerName,
        List<String> lines
) {
    public DialogueScript {
        if (speakerName == null || speakerName.isBlank()) {
            throw new IllegalArgumentException("Dialogue speaker name must not be blank");
        }

        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Dialogue lines must not be empty");
        }

        lines = List.copyOf(lines);
    }

    public String toRawText(String lineSeparator) {
        return String.join(lineSeparator, lines);
    }
}
