package com.sanctuary.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.sanctuary.settings.SettingsService;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

public class SanctuaryMenu extends FXGLMenu {

    private static final String MENU_CSS_PATH = "/assets/ui/css/menu.css";
    private static final String MENU_BUTTON_STYLE_CLASS = "menu-button";

    private static final int MENU_BUTTON_WIDTH = 220;
    private static final int MENU_BUTTON_HEIGHT = 52;

    private final SettingsService settingsService;

    public SanctuaryMenu(SettingsService settingsService) {
        super(MenuType.MAIN_MENU);

        this.settingsService = settingsService;

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

        Rectangle background = new Rectangle(getAppWidth(), getAppHeight(), Color.rgb(
                4,
                8,
                28
        ));

        Text titleText = new Text("Sanctuary");
        titleText.setFont(Font.font(54));
        titleText.getStyleClass().add("menu-title");
        titleText.setTranslateX((double) getAppWidth() / 2 - 155);
        titleText.setTranslateY(140);

        Text subtitleText = new Text("The New Beginning");
        subtitleText.setFont(Font.font(24));
        subtitleText.getStyleClass().add("menu-subtitle");
        subtitleText.setTranslateX((double) getAppWidth() / 2 - 112);
        subtitleText.setTranslateY(178);

        Button newGameButton = createMenuButton("Новая игра", this::fireNewGame);
        Button settingsButton = createMenuButton("Настройки", this::showSettings);
        Button exitButton = createMenuButton("Выход", this::fireExit);

        VBox menuBox = new VBox(20, newGameButton, settingsButton, exitButton);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setTranslateX((double) getAppWidth() / 2 - 110);
        menuBox.setTranslateY((double) getAppHeight() / 2 - 75);

        getContentRoot().getChildren().addAll(background, titleText, subtitleText, menuBox);
    }

    private Button createMenuButton(String text, Runnable action) {
        Button button = new Button(text);

        button.setFont(Font.font(23));
        button.setPrefSize(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
        button.getStyleClass().add(MENU_BUTTON_STYLE_CLASS);
        button.setOnAction(event -> action.run());

        return button;
    }

    private void showSettings() {
        getContentRoot().getChildren().clear();

        SettingsView settingsView = new SettingsView(
                settingsService,
                this::showMainMenu
        );

        getContentRoot().getChildren().add(settingsView.getRoot());
    }
}