package io.github.qishr.cascara.ui.control;

import java.util.List;

import io.github.qishr.cascara.ui.color.ColorDefinition;
import io.github.qishr.cascara.ui.color.ColorUtil;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;

public class ColorWheel extends Control {
    private static final StyleablePropertyFactory<ColorWheel> FACTORY = new StyleablePropertyFactory<>(Control.getClassCssMetaData());
    private boolean updating = false; // Flag to prevent infinite loops

    public ColorWheel() {
        super();
        ChangeListener<Object> hsbListener = (obs, oldV, newV) -> updateColorFromHSB();
        hueProperty().addListener(hsbListener);
        saturationProperty().addListener(hsbListener);
        brightnessProperty().addListener(hsbListener);
        colorProperty().addListener((obs, oldV, newColor) -> updateHSBFromColor(newColor));
        updateCurrentColor();
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

    // Hue (0.0 to 360.0) - Often the primary result of the wheel itself
    private final DoubleProperty hue = new SimpleDoubleProperty(0.0);
    public final DoubleProperty hueProperty() { return hue; }
    public final double getHue() { return hue.get(); }

    // Saturation (0.0 to 1.0)
    private final DoubleProperty saturation = new SimpleDoubleProperty(1.0);
    public final DoubleProperty saturationProperty() { return saturation; }
    public final double getSaturation() { return saturation.get(); }

    // Brightness (0.0 to 1.0)
    private final DoubleProperty brightness = new SimpleDoubleProperty(1.0);
    public final DoubleProperty brightnessProperty() { return brightness; }
    public final double getBrightness() { return brightness.get(); }

    private final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.RED);
    public final ObjectProperty<Color> colorProperty() { return color; }
    public final Color getColor() { return color.get(); }
    public final void setColor(Color c) { color.set(c); }

    private OnChangeHandler onChange = null;
    public interface OnChangeHandler { void onChange(String value); }

    private void updateHSBFromColor(Color newColor) {
        if (updating || newColor == null) return;
        updating = true;

        // Convert the input Color (e.g., RGB) to its HSB components
        hue.set(newColor.getHue());
        saturation.set(newColor.getSaturation());
        brightness.set(newColor.getBrightness());
        if (this.onChange != null) {
            this.onChange.onChange(ColorUtil.toRGBHexCode(newColor));
        }
        updating = false;
    }

    private void updateColorFromHSB() {
        if (updating) return;
        updating = true;
        Color newColor = Color.hsb(getHue(), getSaturation(), getBrightness());
        color.set(newColor); // Update the main color property
        if (this.onChange != null) {
            this.onChange.onChange(ColorUtil.toRGBHexCode(newColor));
        }
        updating = false;
    }

    private void updateCurrentColor() {
        Color newColor = Color.hsb(getHue(), getSaturation(), getBrightness());
        if (this.onChange != null) {
            String hex = String.format("#%02X%02X%02X",
                                       (int)(newColor.getRed() * 255),
                                       (int)(newColor.getGreen() * 255),
                                       (int)(newColor.getBlue() * 255));
            this.onChange.onChange(hex);
        }
    }

    public void setOnChange(OnChangeHandler onChange) {
        this.onChange = onChange;
    }

    public void onChange(String value) {
        if (onChange != null) {
            onChange.onChange(value);
        }
    }

    // Skin

    @Override
    protected Skin<ColorWheel> createDefaultSkin() {
        return new ColorWheelSkin(ColorWheel.this);
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return FACTORY.getCssMetaData();
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return FACTORY.getCssMetaData();
    }
}
