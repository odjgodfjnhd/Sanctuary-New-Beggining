package com.sanctuary.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.sanctuary.ui.style.MenuStyles;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

public class SanctuaryMenu extends FXGLMenu {

    private static final int MENU_BUTTON_WIDTH = 200;
    private static final int MENU_BUTTON_HEIGHT = 50;

    private static final int SMALL_BUTTON_WIDTH = 200;
    private static final int SMALL_BUTTON_HEIGHT = 40;

    public SanctuaryMenu() {
        super(MenuType.MAIN_MENU);

        showMainMenu();
    }

    private void showMainMenu() {
        getContentRoot().getChildren().clear();

        Rectangle bg = new Rectangle(getAppWidth(), getAppHeight(), Color.DARKBLUE);

        Text titleText = new Text("Sanctuary");
        titleText.setFont(Font.font(48));
        titleText.setFill(Color.GOLD);
        titleText.setTranslateX((double) getAppWidth() / 2 - 150);
        titleText.setTranslateY(150);

        Text subtitleText = new Text("The New Beginning");
        subtitleText.setFont(Font.font(24));
        subtitleText.setFill(Color.LIGHTGOLDENRODYELLOW);
        subtitleText.setTranslateX((double) getAppWidth() / 2 - 100);
        subtitleText.setTranslateY(190);

        Button newGameBtn = createMenuButton("Новая игра", this::fireNewGame);
        Button optionsBtn = createMenuButton("Настройки", this::showOptions);
        Button exitBtn = createMenuButton("Выход", this::fireExit);

        VBox menuBox = new VBox(20, newGameBtn, optionsBtn, exitBtn);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setTranslateX((double) getAppWidth() / 2 - 100);
        menuBox.setTranslateY((double) getAppHeight() / 2 - 100);

        getContentRoot().getChildren().addAll(bg, titleText, subtitleText, menuBox);
    }

    private Button createMenuButton(String text, Runnable action) {
        Button button = new Button(text);

        button.setFont(Font.font(24));
        button.setPrefSize(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
        button.setStyle(MenuStyles.menuButtonNormal());

        button.setOnMouseEntered(event -> button.setStyle(MenuStyles.menuButtonHover()));
        button.setOnMouseExited(event -> button.setStyle(MenuStyles.menuButtonNormal()));
        button.setOnAction(event -> action.run());

        return button;
    }

    private Button createSmallButton(String text, Runnable action) {
        Button button = new Button(text);

        button.setFont(Font.font(20));
        button.setPrefSize(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);
        button.setStyle(MenuStyles.menuButtonNormal());

        button.setOnMouseEntered(event -> button.setStyle(MenuStyles.menuButtonHover()));
        button.setOnMouseExited(event -> button.setStyle(MenuStyles.menuButtonNormal()));
        button.setOnAction(event -> action.run());

        return button;
    }

    private void showOptions() {
        getContentRoot().getChildren().clear();

        Rectangle bg = new Rectangle(getAppWidth(), getAppHeight(), Color.DARKGRAY);

        Text titleText = new Text("Настройки");
        titleText.setFont(Font.font(36));
        titleText.setFill(Color.WHITE);
        titleText.setTranslateX((double) getAppWidth() / 2 - 100);
        titleText.setTranslateY(150);

        Text infoText = new Text("Здесь будут настройки:\n- Громкость\n- Управление\n- Графика");
        infoText.setFont(Font.font(18));
        infoText.setFill(Color.LIGHTGRAY);
        infoText.setTranslateX((double) getAppWidth() / 2 - 150);
        infoText.setTranslateY(220);

        Button backBtn = createSmallButton("← Вернуться в меню", this::showMainMenu);
        backBtn.setTranslateX((double) getAppWidth() / 2 - 100);
        backBtn.setTranslateY(300);

        getContentRoot().getChildren().addAll(bg, titleText, infoText, backBtn);
    }
}