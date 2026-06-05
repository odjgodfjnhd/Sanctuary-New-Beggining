package com.sanctuary.dialogue;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class DialogueScriptParser {

    private DialogueScriptParser() {
    }

    public static DialogueScript parse(
            String speakerName,
            String rawDialogueText,
            String lineSeparator
    ) {
        if (rawDialogueText == null || rawDialogueText.isBlank()) {
            return new DialogueScript(speakerName, List.of(""));
        }

        String safeSeparator = Pattern.quote(lineSeparator);

        List<String> lines = Arrays.stream(rawDialogueText.split(safeSeparator))
                .map(String::trim)
                .filter(line -> !line.isBlank())
                .toList();

        if (lines.isEmpty()) {
            return new DialogueScript(speakerName, List.of(""));
        }

        return new DialogueScript(speakerName, lines);
    }
}
