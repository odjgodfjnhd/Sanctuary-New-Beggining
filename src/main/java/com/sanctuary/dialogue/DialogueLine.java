package com.sanctuary.dialogue;

public class DialogueLine {

    private final String speakerName;
    private final String text;

    public DialogueLine(String speakerName, String text) {
        this.speakerName = speakerName;
        this.text = text;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public String getText() {
        return text;
    }
}