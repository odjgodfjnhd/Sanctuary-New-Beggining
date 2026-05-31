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
import com.sanctuary.ui.SanctuaryGameMenu;
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
        settings.setFullScreenAllowed(GameConfig.FULLSCREEN_ALLOWED);
        settings.setFullScreenFromStart(GameConfig.FULLSCREEN_FROM_START);

        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new SanctuaryMenu(
                        gameContext.getServices().getSettingsService()
                );
            }

            @NotNull
            @Override
            public FXGLMenu newGameMenu() {
                return new SanctuaryGameMenu(
                        gameContext.getServices().getSettingsService(),
                        () -> gameContext.getServices().getAudioService().stopBackgroundMusic()
                );
            }
        });
    }

    @Override
    protected void initInput() {
        gameContext.getControllers().registerInput();
    }

    @Override
    protected void initGame() {
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);

        gameContext.getServices().initialize();
        gameContext.getServices().getMapService().loadCurrentMap();

        bindCameraToCurrentMap();
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollisionBegin(EntityType.PLAYER, EntityType.MAP_TRANSITION, (player, transition) -> {
            if (gameContext.getServices().getTransitionService().isTransitionInProgress()) {
                return;
            }

            TransitionComponent transitionComponent = transition.getComponent(TransitionComponent.class);

            gameContext.getServices().getTransitionService().playFadeTransition(() -> {
                gameContext.getServices().getMapService().changeMap(
                        transitionComponent.getTargetMapId(),
                        transitionComponent.getTargetSpawnId()
                );

                bindCameraToCurrentMap();
            });
        });
    }

    private void bindCameraToCurrentMap() {
        gameContext.getControllers().getCameraController().bindToPlayer(
                gameContext.getSession().getPlayer(),
                gameContext.getSession().getCurrentWorldMap()
        );
    }
}
