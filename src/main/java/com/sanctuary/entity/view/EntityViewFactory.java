package com.sanctuary.entity.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class EntityViewFactory {

    private EntityViewFactory() {
    }

    public static Rectangle createOutlineRectangle(
            double width,
            double height,
            Color strokeColor,
            double strokeWidth,
            double initialOpacity
    ) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(strokeColor);
        rectangle.setStrokeWidth(strokeWidth);
        rectangle.setOpacity(initialOpacity);

        return rectangle;
    }
}