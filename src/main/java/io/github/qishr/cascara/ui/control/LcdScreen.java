package io.github.qishr.cascara.ui.control;

import java.util.List;

import io.github.qishr.cascara.ui.color.ColorDefinition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class LcdScreen extends Control {
    private static final StyleablePropertyFactory<LcdScreen> FACTORY = new StyleablePropertyFactory<>(Control.getClassCssMetaData());
    private final Timeline displayTimeline = new Timeline();

    public LcdScreen() {
        super();
        setPadding(new Insets(10, 10, 30, 10));

        KeyFrame resetFrame = new KeyFrame(
            Duration.seconds(1.0),
            e -> this.displayValue.set(Double.NaN) // Resets to NaN, triggering skin to draw hex code
        );
        displayTimeline.getKeyFrames().add(resetFrame);
    }

    private ObjectProperty<ColorDefinition> colorDefinition = new SimpleObjectProperty<>(this, "colorDefinition");
    public final ObjectProperty<ColorDefinition> colorDefinitionProperty() { return colorDefinition; }
    public final ColorDefinition getColorDefinition() { return colorDefinition.get(); }
    public final void setColorDefinition(ColorDefinition definition) {
        this.colorDefinition.set(definition);
        // When the definition is set, we must immediately update the simple color property
        // based on the definition's final hex color.
        if (definition != null && definition.getHexColor() != null) {
            try {
                Color finalColor = Color.web(definition.getHexColor());
                setColor(finalColor); // This triggers the skin update (updateAppearance)
            } catch (Exception _) {
                // Ignore invalid input
            }
        }
    }

    private final ObjectProperty<Insets> screenPadding = new SimpleObjectProperty<>(new Insets(3)); // Default padding of 3 pixels
    public final ObjectProperty<Insets> screenPaddingProperty() { return screenPadding; }
    public final Insets getScreenPadding() { return screenPadding.get(); }
    public final void setScreenPadding(Insets padding) { screenPadding.set(padding); }
    public final void setScreenPadding(double value) { screenPadding.set(new Insets(value)); }

    private final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.BLACK);
    public final ObjectProperty<Color> colorProperty() { return color; }
    public final Color getColor() { return color.get(); }
    public final void setColor(Color c) { color.set(c); }

    private final DoubleProperty displayValue = new SimpleDoubleProperty(this, "displayValue", Double.NaN);
    public final DoubleProperty displayValueProperty() { return displayValue; }
    public final double getDisplayValue() { return displayValue.get(); }

    private OnPasteHandler onPaste = null;
    public interface OnPasteHandler { void onPaste(String value); }
    public OnPasteHandler getOnPaste() { return onPaste; }
    public void setOnPaste(OnPasteHandler onPaste) { this.onPaste = onPaste; }
    public void onPaste(String value) {
        if (onPaste != null) {
            onPaste.onPaste(value);
        }
    }

    /**
     * Temporarily displays the given number for the default duration (1 second).
     * The display returns to the background color's hex code automatically.
     * @param value The double value to display.
     */
    public void showValue(double value) {
        showValue(value, Duration.seconds(1.0));
    }

    /**
     * Temporarily displays the given number for a configurable duration.
     * @param value The double value to display.
     * @param duration The duration to show the number before returning to the default hex display.
     *                 A value of Duration.INDEFINITE will display the value until it's explicitly changed again.
     */
    public void showValue(double value, Duration duration) {
        displayTimeline.stop();
        displayValue.set(value);
        if (duration != null && !duration.isIndefinite()) {
            displayTimeline.getKeyFrames().set(0, new KeyFrame(
                duration,
                e -> this.displayValue.set(Double.NaN)
            ));
            displayTimeline.playFromStart();
        }
        displayTimeline.playFromStart();
    }

    @Override
    public double computeMinWidth(double height) {
        return computePrefWidth(height);
    }

    @Override
    public double computeMinHeight(double width) {
        return computePrefHeight(width);
    }

    @Override
    public double computePrefWidth(double height) {
        double coreContentWidth = LcdScreenSkin.PIXELS_WIDE * (LcdScreenSkin.PIXEL_SIZE + LcdScreenSkin.GAP_SIZE) - LcdScreenSkin.GAP_SIZE;
        Insets padding = getScreenPadding();
        return coreContentWidth + (padding.getLeft() * 2) + padding.getRight();
    }

    @Override
    public double computePrefHeight(double width) {
        double coreContentHeight = LcdScreenSkin.PIXELS_HIGH * (LcdScreenSkin.PIXEL_SIZE + LcdScreenSkin.GAP_SIZE) - LcdScreenSkin.GAP_SIZE;
        Insets padding = getScreenPadding();
        return coreContentHeight + padding.getTop() + padding.getBottom();
    }

    @Override
    public double computeMaxWidth(double height) {
        return computePrefWidth(height);
    }

    @Override
    public double computeMaxHeight(double width) {
        return computePrefHeight(width);
    }

    // Skin

    @Override
    protected Skin<LcdScreen> createDefaultSkin() {
        return new LcdScreenSkin(LcdScreen.this);
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return FACTORY.getCssMetaData();
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return FACTORY.getCssMetaData();
    }
}
