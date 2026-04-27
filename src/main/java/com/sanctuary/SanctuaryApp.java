package com.sanctuary;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.sanctuary.config.GameConfig;
import com.sanctuary.game.GameBootstrap;
import com.sanctuary.game.GameContext;
import com.sanctuary.ui.SanctuaryMenu;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class SanctuaryApp extends GameApplication {

    private final GameContext gameContext = new GameBootstrap().bootstrapNewGame();

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(GameConfig.APP_WIDTH);
        settings.setHeight(GameConfig.APP_HEIGHT);
        settings.setTitle(GameConfig.TITLE);
        settings.setVersion(GameConfig.VERSION);
        settings.setMainMenuEnabled(GameConfig.MAIN_MENU_ENABLED);

        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new SanctuaryMenu();
            }
        });
    }

    @Override
    protected void initInput() {
        gameContext.getPlayerInputController().registerInput();
    }

    @Override
    protected void initGame() {
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);

        gameContext.getMapService().loadCurrentMap();

        gameContext.getCameraController().bindToPlayer(
                gameContext.getSession().getPlayer(),
                gameContext.getSession().getCurrentWorldMap()
        );
    }

    @Override
    protected void initPhysics() {
        // Коллизии стен теперь обрабатываются внутри MovementComponent:
        // движение по X и Y с откатом позиции при пересечении стены.
    }

    public static void main(String[] args) {
        launch(args);
    }
}