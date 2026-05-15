package com.sanctuary.dialogue;

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