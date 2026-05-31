package com.sanctuary.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
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

public class SanctuaryGameMenu extends FXGLMenu {

    private static final String MENU_CSS_PATH = "/assets/ui/css/menu.css";
    private static final String MENU_BUTTON_STYLE_CLASS = "menu-button";

    private static final int MENU_BUTTON_WIDTH = 260;
    private static final int MENU_BUTTON_HEIGHT = 50;

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

        Rectangle background = new Rectangle(getAppWidth(), getAppHeight(), Color.rgb(
                0,
                0,
                0,
                0.72
        ));

        Text titleText = new Text("Pause");
        titleText.setFont(Font.font(44));
        titleText.setFill(Color.GOLD);
        titleText.setTranslateX((double) getAppWidth() / 2 - 70);
        titleText.setTranslateY(160);

        Button resumeButton = createMenuButton("Продолжить", this::fireResume);
        Button mainMenuButton = createMenuButton("В главное меню", this::returnToMainMenu);
        Button exitButton = createMenuButton("Выход", this::fireExit);

        VBox menuBox = new VBox(20, resumeButton, mainMenuButton, exitButton);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setTranslateX((double) getAppWidth() / 2 - 130);
        menuBox.setTranslateY((double) getAppHeight() / 2 - 80);

        getContentRoot().getChildren().addAll(background, titleText, menuBox);
    }

    private Button createMenuButton(String text, Runnable action) {
        Button button = new Button(text);

        button.setFont(Font.font(22));
        button.setPrefSize(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
        button.getStyleClass().add(MENU_BUTTON_STYLE_CLASS);
        button.setOnAction(event -> action.run());

        return button;
    }

    private void returnToMainMenu() {
        returnToMainMenuAction.run();
        fireExitToMainMenu();
    }
}