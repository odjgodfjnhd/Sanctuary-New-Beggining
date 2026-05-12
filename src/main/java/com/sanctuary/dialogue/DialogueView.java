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

    private final StackPane root = new StackPane();
    private final Label speakerLabel = new Label();
    private final Label textLabel = new Label();
    private final Label hintLabel = new Label();

    private boolean attached = false;

    public DialogueView() {
        configureRoot();
        configureLabels();
        compose();
    }

    public void show(DialogueLine line, int currentLineIndex, int lineCount) {
        updateLayout();

        speakerLabel.setText(
                line.getSpeakerName()
                        + "  "
                        + currentLineIndex
                        + "/"
                        + lineCount
        );

        textLabel.setText("");
        hintLabel.setText(DialogueConfig.ADVANCE_HINT);

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
        speakerLabel.setTextFill(DialogueConfig.SPEAKER_TEXT_COLOR);
        speakerLabel.setFont(Font.font(DialogueConfig.SPEAKER_FONT_SIZE));

        textLabel.setTextFill(DialogueConfig.DIALOGUE_TEXT_COLOR);
        textLabel.setFont(Font.font(DialogueConfig.DIALOGUE_FONT_SIZE));
        textLabel.setWrapText(true);

        hintLabel.setTextFill(DialogueConfig.HINT_TEXT_COLOR);
        hintLabel.setFont(Font.font(DialogueConfig.HINT_FONT_SIZE));
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

        background.setFill(DialogueConfig.BACKGROUND_COLOR);
        background.setStroke(DialogueConfig.BORDER_COLOR);
        background.setStrokeWidth(DialogueConfig.BORDER_WIDTH);
        background.setArcWidth(DialogueConfig.CORNER_RADIUS);
        background.setArcHeight(DialogueConfig.CORNER_RADIUS);

        return background;
    }

    private void updateLayout() {
        double appWidth = FXGL.getAppWidth();
        double appHeight = FXGL.getAppHeight();

        double width = Math.max(DialogueConfig.MIN_WIDTH, appWidth * DialogueConfig.WIDTH_RATIO);
        double height = Math.max(DialogueConfig.MIN_HEIGHT, appHeight * DialogueConfig.HEIGHT_RATIO);

        double x = (appWidth - width) / 2.0;
        double y = appHeight - height - DialogueConfig.BOTTOM_MARGIN;

        root.setPrefSize(width, height);
        root.setTranslateX(x);
        root.setTranslateY(y);

        textLabel.setMaxWidth(width - 48);
    }
}