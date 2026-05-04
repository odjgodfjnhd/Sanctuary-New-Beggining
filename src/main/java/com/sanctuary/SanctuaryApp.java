package com.sanctuary;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.sanctuary.config.GameConfig;
import com.sanctuary.entity.EntityType;
import com.sanctuary.game.GameBootstrap;
import com.sanctuary.game.GameContext;
import com.sanctuary.ui.SanctuaryMenu;
import com.sanctuary.world.TransitionComponent;
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

        bindCameraToCurrentMap();
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollisionBegin(EntityType.PLAYER, EntityType.MAP_TRANSITION, (player, transition) -> {
            if (gameContext.getTransitionService().isTransitionInProgress()) {
                return;
            }

            TransitionComponent transitionComponent = transition.getComponent(TransitionComponent.class);

            System.out.println(
                    "Transition to map: "
                            + transitionComponent.getTargetMapId()
                            + ", spawn: "
                            + transitionComponent.getTargetSpawnId()
            );

            gameContext.getTransitionService().playFadeTransition(() -> {
                gameContext.getMapService().changeMap(
                        transitionComponent.getTargetMapId(),
                        transitionComponent.getTargetSpawnId()
                );

                bindCameraToCurrentMap();
            });
        });
    }

    private void bindCameraToCurrentMap() {
        gameContext.getCameraController().bindToPlayer(
                gameContext.getSession().getPlayer(),
                gameContext.getSession().getCurrentWorldMap()
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}