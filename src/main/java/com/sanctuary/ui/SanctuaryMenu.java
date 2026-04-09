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

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

public class SanctuaryMenu extends FXGLMenu {

    public SanctuaryMenu() {
        super(MenuType.MAIN_MENU);

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
        Button btn = new Button(text);
        btn.setFont(Font.font(24));
        btn.setPrefSize(200, 50);
        btn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #4a4a4a, #2a2a2a);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: gold;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-width: 2;"
        );

        btn.setOnMouseEntered(e ->
                btn.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #5a5a5a, #3a3a3a);" +
                                "-fx-text-fill: gold;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 10;" +
                                "-fx-border-color: gold;" +
                                "-fx-border-radius: 10;" +
                                "-fx-border-width: 3;"
                )
        );

        btn.setOnMouseExited(e ->
                btn.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #4a4a4a, #2a2a2a);" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 10;" +
                                "-fx-border-color: gold;" +
                                "-fx-border-radius: 10;" +
                                "-fx-border-width: 2;"
                )
        );

        btn.setOnAction(e -> action.run());
        return btn;
    }

    private void showOptions() {
        getContentRoot().getChildren().clear();

        Rectangle bg = new Rectangle(getAppWidth(), getAppHeight(), Color.DARKGRAY);

        Text titleText = new Text("Настройки");
        titleText.setFont(Font.font(36));
        titleText.setFill(Color.WHITE);
        titleText.setTranslateX((double) getAppWidth() / 2 - 100);
        titleText.setTranslateY(150);

        Button backBtn = new Button("← Вернуться в меню");
        backBtn.setFont(Font.font(20));
        backBtn.setPrefSize(200, 40);
        backBtn.setTranslateX((double) getAppWidth() / 2 - 100);
        backBtn.setTranslateY(300);
        backBtn.setOnAction(e -> {
            getContentRoot().getChildren().clear();
            SanctuaryMenu mainMenu = new SanctuaryMenu();
            getContentRoot().getChildren().addAll(mainMenu.getContentRoot().getChildren());
        });

        Text infoText = new Text("Здесь будут настройки:\n- Громкость\n- Управление\n- Графика");
        infoText.setFont(Font.font(18));
        infoText.setFill(Color.LIGHTGRAY);
        infoText.setTranslateX((double) getAppWidth() / 2 - 150);
        infoText.setTranslateY(220);

        getContentRoot().getChildren().addAll(bg, titleText, infoText, backBtn);
    }
}