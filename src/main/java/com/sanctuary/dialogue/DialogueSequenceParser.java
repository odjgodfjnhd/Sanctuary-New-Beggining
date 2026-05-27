package com.sanctuary.dialogue;

import com.sanctuary.config.DialogueConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DialogueSequenceParser {

    private final DialogueConfig config;

    public DialogueSequenceParser(DialogueConfig config) {
        this.config = config;
    }

    public DialogueSequence parse(String speakerName, String rawDialogueText) {
        List<String> textParts = splitRawText(rawDialogueText);
        List<DialogueLine> lines = createLines(speakerName, textParts);

        return new DialogueSequence(lines);
    }

    private List<String> splitRawText(String rawDialogueText) {
        if (rawDialogueText == null || rawDialogueText.isBlank()) {
            return List.of("");
        }

        String[] parts = rawDialogueText.split(Pattern.quote(config.lineSeparator()));
        List<String> result = new ArrayList<>();

        for (String part : parts) {
            String normalizedLine = part.trim();

            if (!normalizedLine.isBlank()) {
                result.add(normalizedLine);
            }
        }

        if (result.isEmpty()) {
            result.add("");
        }

        return result;
    }

    private List<DialogueLine> createLines(String speakerName, List<String> textParts) {
        List<DialogueLine> lines = new ArrayList<>();

        for (String textPart : textParts) {
            lines.add(new DialogueLine(speakerName, textPart));
        }

        return lines;
    }
}