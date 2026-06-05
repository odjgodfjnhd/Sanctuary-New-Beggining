package com.sanctuary.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.sanctuary.game.GameSession;
import com.sanctuary.save.SaveService;
import com.sanctuary.settings.SettingsService;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

public class SanctuaryMenu extends FXGLMenu {

    private static final String MENU_CSS_PATH = "/assets/ui/css/menu.css";

    private static final String MENU_BUTTON_STYLE_CLASS = "menu-button";
    private static final String MAIN_MENU_BACKGROUND_STYLE_CLASS = "main-menu-background";
    private static final String MAIN_MENU_CONTAINER_STYLE_CLASS = "main-menu-container";
    private static final String MAIN_MENU_TITLE_STYLE_CLASS = "menu-title";
    private static final String MAIN_MENU_SUBTITLE_STYLE_CLASS = "menu-subtitle";

    private static final double BUTTON_WIDTH_RATIO = 0.28;
    private static final double BUTTON_HEIGHT_RATIO = 0.065;

    private static final double MIN_BUTTON_WIDTH = 220.0;
    private static final double MAX_BUTTON_WIDTH = 340.0;
    private static final double MIN_BUTTON_HEIGHT = 48.0;
    private static final double MAX_BUTTON_HEIGHT = 58.0;

    private static final double MENU_SPACING_RATIO = 0.03;
    private static final double MIN_MENU_SPACING = 16.0;
    private static final double MAX_MENU_SPACING = 28.0;

    private final GameSession session;
    private final SettingsService settingsService;
    private final SaveService saveService;

    public SanctuaryMenu(
            GameSession session,
            SettingsService settingsService,
            SaveService saveService
    ) {
        super(MenuType.MAIN_MENU);

        this.session = session;
        this.settingsService = settingsService;
        this.saveService = saveService;

        loadStylesheet();
        showMainMenu();
    }

    private void loadStylesheet() {
        String cssResource = Objects.requireNonNull(
                getClass().getResource(MENU_CSS_PATH),
                "Main menu stylesheet not found: " + MENU_CSS_PATH
        ).toExternalForm();

        getContentRoot().getStylesheets().add(cssResource);
    }

    private void showMainMenu() {
        getContentRoot().getChildren().clear();

        StackPane root = createRoot();

        Rectangle background = createBackground();

        Text titleText = new Text("Sanctuary");
        titleText.getStyleClass().add(MAIN_MENU_TITLE_STYLE_CLASS);

        Text subtitleText = new Text("The New Beginning");
        subtitleText.getStyleClass().add(MAIN_MENU_SUBTITLE_STYLE_CLASS);

        Button continueButton = createMenuButton("Продолжить", this::continueGame);
        continueButton.setDisable(!saveService.hasSave());

        Button newGameButton = createMenuButton("Новая игра", this::startNewGame);
        Button settingsButton = createMenuButton("Настройки", this::showSettings);
        Button exitButton = createMenuButton("Выход", this::fireExit);

        VBox menuContent = new VBox(
                getMenuSpacing(),
                titleText,
                subtitleText,
                continueButton,
                newGameButton,
                settingsButton,
                exitButton
        );

        menuContent.setAlignment(Pos.CENTER);
        menuContent.getStyleClass().add(MAIN_MENU_CONTAINER_STYLE_CLASS);

        root.getChildren().addAll(background, menuContent);
        getContentRoot().getChildren().add(root);
    }

    private StackPane createRoot() {
        StackPane root = new StackPane();

        root.setPrefSize(getAppWidth(), getAppHeight());
        root.setAlignment(Pos.CENTER);

        return root;
    }

    private Rectangle createBackground() {
        Rectangle background = new Rectangle(getAppWidth(), getAppHeight());

        background.getStyleClass().add(MAIN_MENU_BACKGROUND_STYLE_CLASS);

        return background;
    }

    private Button createMenuButton(String text, Runnable action) {
        Button button = new Button(text);

        button.setPrefSize(getButtonWidth(), getButtonHeight());
        button.getStyleClass().add(MENU_BUTTON_STYLE_CLASS);
        button.setOnAction(event -> action.run());

        return button;
    }

    private void startNewGame() {
        session.prepareNewGame();
        fireNewGame();
    }

    private void continueGame() {
        saveService.prepareSessionForSavedGame();
        fireNewGame();
    }

    private void showSettings() {
        getContentRoot().getChildren().clear();

        SettingsView settingsView = new SettingsView(
                settingsService,
                this::showMainMenu
        );

        getContentRoot().getChildren().add(settingsView.getRoot());
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
}
