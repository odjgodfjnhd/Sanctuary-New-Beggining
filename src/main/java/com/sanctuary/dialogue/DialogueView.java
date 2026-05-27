package com.sanctuary.dialogue;

import com.almasb.fxgl.dsl.FXGL;
import com.sanctuary.config.DialogueConfig;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class DialogueView {

    private final DialogueConfig config;
    private final StackPane root = new StackPane();
    private final Label speakerLabel = new Label();
    private final Label textLabel = new Label();
    private final Label hintLabel = new Label();

    private boolean attached = false;

    public DialogueView(DialogueConfig config) {
        this.config = config;

        configureRoot();
        configureLabels();
        compose();
    }

    public void show(DialogueLine line, int currentLineIndex, int lineCount) {
        updateLayout();

        speakerLabel.setText(
                line.speakerName()
                        + "  "
                        + currentLineIndex
                        + "/"
                        + lineCount
        );

        textLabel.setText("");
        hintLabel.setText(config.advanceHint());

        if (!attached) {
            FXGL.getGameScene().addUINode(root);
            attached = true;
        }
    }

    public void updateText(String text) {
        textLabel.setText(text);
    }

    public void hide() {
        if (!attached) {
            return;
        }

        FXGL.getGameScene().removeUINode(root);
        attached = false;
    }

    private void configureRoot() {
        root.setMouseTransparent(true);
    }

    private void configureLabels() {
        speakerLabel.setTextFill(config.speakerTextColor());
        speakerLabel.setFont(Font.font(config.speakerFontSize()));

        textLabel.setTextFill(config.dialogueTextColor());
        textLabel.setFont(Font.font(config.dialogueFontSize()));
        textLabel.setWrapText(true);

        hintLabel.setTextFill(config.hintTextColor());
        hintLabel.setFont(Font.font(config.hintFontSize()));
    }

    private void compose() {
        Rectangle background = createBackground();

        VBox content = new VBox(8, speakerLabel, textLabel, hintLabel);
        content.setAlignment(Pos.TOP_LEFT);
        content.setPadding(new Insets(18, 24, 16, 24));

        root.getChildren().addAll(background, content);
    }

    private Rectangle createBackground() {
        Rectangle background = new Rectangle();
        background.widthProperty().bind(root.prefWidthProperty());
        background.heightProperty().bind(root.prefHeightProperty());

        background.setFill(createBackgroundColor());
        background.setStroke(config.borderColor());
        background.setStrokeWidth(config.borderWidth());
        background.setArcWidth(config.cornerRadius());
        background.setArcHeight(config.cornerRadius());

        return background;
    }

    private Color createBackgroundColor() {
        Color baseColor = config.backgroundColor();

        return new Color(
                baseColor.getRed(),
                baseColor.getGreen(),
                baseColor.getBlue(),
                config.backgroundOpacity()
        );
    }

    private void updateLayout() {
        double appWidth = FXGL.getAppWidth();
        double appHeight = FXGL.getAppHeight();

        double width = Math.max(config.minWidth(), appWidth * config.widthRatio());
        double height = Math.max(config.minHeight(), appHeight * config.heightRatio());

        double x = (appWidth - width) / 2.0;
        double y = appHeight - height - config.bottomMargin();

        root.setPrefSize(width, height);
        root.setTranslateX(x);
        root.setTranslateY(y);

        textLabel.setMaxWidth(width - 48);
    }
}