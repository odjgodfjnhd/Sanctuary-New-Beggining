package com.sanctuary.ui;

import com.sanctuary.settings.SettingsService;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

public class SettingsView {

    private static final String MENU_CSS_PATH = "/assets/ui/css/menu.css";
    private static final String MENU_BUTTON_STYLE_CLASS = "menu-button";
    private static final String SETTINGS_PANEL_STYLE_CLASS = "settings-panel";
    private static final String SETTINGS_LABEL_STYLE_CLASS = "settings-label";
    private static final String SETTINGS_VALUE_LABEL_STYLE_CLASS = "settings-value-label";
    private static final String SETTINGS_CHECKBOX_STYLE_CLASS = "settings-checkbox";
    private static final String SETTINGS_SLIDER_STYLE_CLASS = "settings-slider";

    private static final int BACK_BUTTON_WIDTH = 220;
    private static final int BACK_BUTTON_HEIGHT = 42;

    private static final double PANEL_WIDTH = 460.0;
    private static final double PANEL_HEIGHT = 340.0;

    private static final double VOLUME_MIN = 0.0;
    private static final double VOLUME_MAX = 100.0;

    private final SettingsService settingsService;
    private final Runnable backAction;
    private final StackPane root = new StackPane();

    public SettingsView(SettingsService settingsService, Runnable backAction) {
        this.settingsService = settingsService;
        this.backAction = backAction;

        loadStylesheet();
        buildView();
    }

    public StackPane getRoot() {
        return root;
    }

    private void loadStylesheet() {
        String cssResource = Objects.requireNonNull(
                getClass().getResource(MENU_CSS_PATH),
                "Settings stylesheet not found: " + MENU_CSS_PATH
        ).toExternalForm();

        root.getStylesheets().add(cssResource);
    }

    private void buildView() {
        root.getChildren().clear();
        root.setPrefSize(getAppWidth(), getAppHeight());

        Rectangle background = new Rectangle(getAppWidth(), getAppHeight(), Color.rgb(
                3,
                8,
                24,
                0.88
        ));

        VBox panel = createSettingsPanel();

        root.getChildren().addAll(background, panel);
    }

    private VBox createSettingsPanel() {
        Label title = createTitleLabel();

        CheckBox fullscreenCheckBox = createFullscreenCheckBox();

        HBox volumeRow = createVolumeRow();

        Button backButton = createBackButton();

        VBox panel = new VBox(24, title, fullscreenCheckBox, volumeRow, backButton);
        panel.setAlignment(Pos.CENTER);
        panel.setPrefSize(PANEL_WIDTH, PANEL_HEIGHT);
        panel.setMaxSize(PANEL_WIDTH, PANEL_HEIGHT);
        panel.getStyleClass().add(SETTINGS_PANEL_STYLE_CLASS);

        return panel;
    }

    private Label createTitleLabel() {
        Label title = new Label("Настройки");
        title.setFont(Font.font(34));
        title.getStyleClass().add(SETTINGS_LABEL_STYLE_CLASS);

        return title;
    }

    private CheckBox createFullscreenCheckBox() {
        CheckBox checkBox = new CheckBox("Полноэкранный режим");

        checkBox.setSelected(settingsService.isFullscreen());
        checkBox.getStyleClass().add(SETTINGS_CHECKBOX_STYLE_CLASS);
        checkBox.setOnAction(event -> settingsService.setFullscreen(checkBox.isSelected()));

        return checkBox;
    }

    private HBox createVolumeRow() {
        Label label = new Label("Громкость музыки");
        label.getStyleClass().add(SETTINGS_LABEL_STYLE_CLASS);

        Label valueLabel = new Label(formatVolume(settingsService.getMusicVolume()));
        valueLabel.getStyleClass().add(SETTINGS_VALUE_LABEL_STYLE_CLASS);

        Slider slider = new Slider(
                VOLUME_MIN,
                VOLUME_MAX,
                settingsService.getMusicVolume() * VOLUME_MAX
        );

        slider.getStyleClass().add(SETTINGS_SLIDER_STYLE_CLASS);
        slider.setPrefWidth(190);
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(false);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = toNormalizedVolume(newValue.doubleValue());

            settingsService.previewMusicVolume(volume);
            valueLabel.setText(formatVolume(volume));
        });

        slider.valueChangingProperty().addListener((observable, wasChanging, isChanging) -> {
            if (!isChanging) {
                settingsService.setMusicVolume(toNormalizedVolume(slider.getValue()));
            }
        });

        slider.setOnMouseReleased(event ->
                settingsService.setMusicVolume(toNormalizedVolume(slider.getValue()))
        );

        HBox row = new HBox(16, label, slider, valueLabel);
        row.setAlignment(Pos.CENTER);

        return row;
    }

    private Button createBackButton() {
        Button button = new Button("← Назад");

        button.setFont(Font.font(20));
        button.setPrefSize(BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        button.getStyleClass().add(MENU_BUTTON_STYLE_CLASS);
        button.setOnAction(event -> backAction.run());

        return button;
    }

    private double toNormalizedVolume(double sliderValue) {
        return sliderValue / VOLUME_MAX;
    }

    private String formatVolume(double volume) {
        return Math.round(volume * 100) + "%";
    }
}
