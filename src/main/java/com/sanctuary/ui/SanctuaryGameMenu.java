package com.sanctuary.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

public class SanctuaryGameMenu extends FXGLMenu {

    private static final String MENU_CSS_PATH = "/assets/ui/css/menu.css";

    private static final String MENU_BUTTON_STYLE_CLASS = "menu-button";
    private static final String GAME_MENU_OVERLAY_STYLE_CLASS = "game-menu-overlay";
    private static final String GAME_MENU_CONTAINER_STYLE_CLASS = "game-menu-container";
    private static final String GAME_MENU_TITLE_STYLE_CLASS = "game-menu-title";

    private static final double BUTTON_WIDTH_RATIO = 0.28;
    private static final double BUTTON_HEIGHT_RATIO = 0.065;

    private static final double MIN_BUTTON_WIDTH = 240.0;
    private static final double MAX_BUTTON_WIDTH = 340.0;
    private static final double MIN_BUTTON_HEIGHT = 46.0;
    private static final double MAX_BUTTON_HEIGHT = 58.0;

    private static final double MENU_SPACING_RATIO = 0.03;
    private static final double MIN_MENU_SPACING = 16.0;
    private static final double MAX_MENU_SPACING = 26.0;

    private final Runnable returnToMainMenuAction;

    public SanctuaryGameMenu(Runnable returnToMainMenuAction) {
        super(MenuType.GAME_MENU);

        this.returnToMainMenuAction = returnToMainMenuAction;

        loadStylesheet();
        showGameMenu();
    }

    private void loadStylesheet() {
        String cssResource = Objects.requireNonNull(
                getClass().getResource(MENU_CSS_PATH),
                "Game menu stylesheet not found: " + MENU_CSS_PATH
        ).toExternalForm();

        getContentRoot().getStylesheets().add(cssResource);
    }

    private void showGameMenu() {
        getContentRoot().getChildren().clear();

        Rectangle overlay = createOverlay();

        Text titleText = new Text("Pause");
        titleText.getStyleClass().add(GAME_MENU_TITLE_STYLE_CLASS);

        Button resumeButton = createMenuButton("Продолжить", this::fireResume);
        Button mainMenuButton = createMenuButton("В главное меню", this::returnToMainMenu);
        Button exitButton = createMenuButton("Выход", this::fireExit);

        VBox menuContainer = new VBox(
                getMenuSpacing(),
                titleText,
                resumeButton,
                mainMenuButton,
                exitButton
        );

        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setPrefSize(getAppWidth(), getAppHeight());
        menuContainer.getStyleClass().add(GAME_MENU_CONTAINER_STYLE_CLASS);

        getContentRoot().getChildren().addAll(overlay, menuContainer);
    }

    private Rectangle createOverlay() {
        Rectangle overlay = new Rectangle(getAppWidth(), getAppHeight());
        overlay.getStyleClass().add(GAME_MENU_OVERLAY_STYLE_CLASS);

        return overlay;
    }

    private Button createMenuButton(String text, Runnable action) {
        Button button = new Button(text);

        button.setPrefSize(getButtonWidth(), getButtonHeight());
        button.getStyleClass().add(MENU_BUTTON_STYLE_CLASS);
        button.setOnAction(event -> action.run());

        return button;
    }

    private double getButtonWidth() {
        return clamp(
                getAppWidth() * BUTTON_WIDTH_RATIO,
                MIN_BUTTON_WIDTH,
                MAX_BUTTON_WIDTH
        );
    }

    private double getButtonHeight() {
        return clamp(
                getAppHeight() * BUTTON_HEIGHT_RATIO,
                MIN_BUTTON_HEIGHT,
                MAX_BUTTON_HEIGHT
        );
    }

    private double getMenuSpacing() {
        return clamp(
                getAppHeight() * MENU_SPACING_RATIO,
                MIN_MENU_SPACING,
                MAX_MENU_SPACING
        );
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private void returnToMainMenu() {
        returnToMainMenuAction.run();
        fireExitToMainMenu();
    }
}