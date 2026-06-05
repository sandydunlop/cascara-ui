package io.github.qishr.cascara.ui.window;

import javafx.scene.paint.Color;

public class ShadowProfile {
    public double radius;
    public Color color;
    public double offset;
    // Add spread, etc., if needed

    public ShadowProfile(int radius, Color color, int offsetY) {
        this.radius = radius;
        this.color = color;
        this.offset = offsetY;
    }
}

