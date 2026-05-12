package com.sanctuary.dialogue;

import com.sanctuary.config.DialogueConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DialogueSequence {

    private final List<DialogueLine> lines;
    private int currentIndex = 0;

    public DialogueSequence(List<DialogueLine> lines) {
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Dialogue sequence must contain at least one line");
        }

        this.lines = Collections.unmodifiableList(new ArrayList<>(lines));
    }

    public static DialogueSequence fromText(String speakerName, String rawDialogueText) {
        List<DialogueLine> lines = new ArrayList<>();

        if (rawDialogueText == null || rawDialogueText.isBlank()) {
            lines.add(new DialogueLine(speakerName, ""));
            return new DialogueSequence(lines);
        }

        String[] parts = rawDialogueText.split(DialogueConfig.LINE_SEPARATOR);

        for (String part : parts) {
            String line = part.trim();

            if (!line.isBlank()) {
                lines.add(new DialogueLine(speakerName, line));
            }
        }

        if (lines.isEmpty()) {
            lines.add(new DialogueLine(speakerName, ""));
        }

        return new DialogueSequence(lines);
    }

    public DialogueLine getCurrentLine() {
        return lines.get(currentIndex);
    }

    public boolean hasNextLine() {
        return currentIndex + 1 < lines.size();
    }

    public void moveToNextLine() {
        if (!hasNextLine()) {
            throw new IllegalStateException("Dialogue sequence has no next line");
        }

        currentIndex++;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getLineCount() {
        return lines.size();
    }
}