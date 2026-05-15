package com.sanctuary.config;

import javafx.scene.paint.Color;
import javafx.util.Duration;

public record DialogueConfig(
        double widthRatio,
        double heightRatio,
        double bottomMargin,
        double minWidth,
        double minHeight,
        Color backgroundColor,
        double backgroundOpacity,
        Color borderColor,
        double borderWidth,
        double cornerRadius,
        Color speakerTextColor,
        Color dialogueTextColor,
        Color hintTextColor,
        int speakerFontSize,
        int dialogueFontSize,
        int hintFontSize,
        Duration typewriterDelay,
        String lineSeparator,
        String advanceHint
) {
}