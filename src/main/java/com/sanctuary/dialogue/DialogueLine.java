package com.sanctuary.dialogue;

import java.util.Objects;

public record DialogueLine(String speakerName, String text) {

    public DialogueLine {
        speakerName = Objects.requireNonNull(speakerName, "speakerName must not be null");
        text = Objects.requireNonNull(text, "text must not be null");
    }
}