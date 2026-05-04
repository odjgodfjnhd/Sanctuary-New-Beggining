package com.sanctuary.world;

import com.almasb.fxgl.dsl.FXGL;
import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class TransitionService {

    private boolean transitionInProgress = false;

    public void playFadeTransition(Runnable actionDuringBlackScreen) {
        if (transitionInProgress) {
            return;
        }

        transitionInProgress = true;

        Rectangle fadeOverlay = new Rectangle(
                FXGL.getAppWidth(),
                FXGL.getAppHeight(),
                Color.BLACK
        );

        fadeOverlay.setOpacity(0);
        fadeOverlay.setMouseTransparent(true);

        FXGL.getGameScene().addUINode(fadeOverlay);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.35), fadeOverlay);
        fadeOut.setFromValue(0);
        fadeOut.setToValue(1);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.35), fadeOverlay);
        fadeIn.setFromValue(1);
        fadeIn.setToValue(0);

        fadeOut.setOnFinished(event -> {
            actionDuringBlackScreen.run();

            fadeIn.setOnFinished(event2 -> {
                FXGL.getGameScene().removeUINode(fadeOverlay);
                transitionInProgress = false;
            });

            fadeIn.play();
        });

        fadeOut.play();
    }

    public boolean isTransitionInProgress() {
        return transitionInProgress;
    }
}