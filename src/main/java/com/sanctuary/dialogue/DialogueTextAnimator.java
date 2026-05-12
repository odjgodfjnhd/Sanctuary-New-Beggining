package com.sanctuary.dialogue;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.function.Consumer;

public class DialogueTextAnimator {

    private Timeline timeline;
    private String fullText = "";
    private int visibleCharacters = 0;
    private boolean printing = false;

    public void start(
            String text,
            Duration delay,
            Consumer<String> onTextChanged,
            Runnable onFinished
    ) {
        stop();

        fullText = text == null ? "" : text;
        visibleCharacters = 0;
        printing = true;

        onTextChanged.accept("");

        timeline = new Timeline(new KeyFrame(delay, event -> {
            visibleCharacters++;

            if (visibleCharacters >= fullText.length()) {
                onTextChanged.accept(fullText);
                finish(onFinished);
                return;
            }

            onTextChanged.accept(fullText.substring(0, visibleCharacters));
        }));

        timeline.setCycleCount(Math.max(fullText.length(), 1));
        timeline.play();
    }

    public void completeImmediately(Consumer<String> onTextChanged) {
        if (!printing) {
            return;
        }

        stop();
        onTextChanged.accept(fullText);
    }

    public boolean isPrinting() {
        return printing;
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }

        printing = false;
    }

    private void finish(Runnable onFinished) {
        stop();
        onFinished.run();
    }
}