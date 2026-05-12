package com.sanctuary.entity.common;

import com.almasb.fxgl.entity.component.Component;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class OpacityPulseComponent extends Component {

    private final FadeTransition pulseAnimation;

    public OpacityPulseComponent(
            Node target,
            Duration duration,
            double fromOpacity,
            double toOpacity
    ) {
        this.pulseAnimation = new FadeTransition(duration, target);
        this.pulseAnimation.setFromValue(fromOpacity);
        this.pulseAnimation.setToValue(toOpacity);
        this.pulseAnimation.setAutoReverse(true);
        this.pulseAnimation.setCycleCount(Animation.INDEFINITE);
    }

    @Override
    public void onAdded() {
        pulseAnimation.play();
    }

    @Override
    public void onRemoved() {
        pulseAnimation.stop();
    }
}