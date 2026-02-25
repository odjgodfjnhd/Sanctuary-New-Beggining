package com.sanctuary;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Main extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Sanctuary: The New Beginning");
        settings.setVersion("0.1.0");
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.BLACK);

        Entity player = entityBuilder()
                .at(100, 100)
                .view(new Rectangle(40, 40, Color.GREEN))
                .buildAndAttach();
    }

    public static void main(String[] args) {
        launch(args);
    }
}