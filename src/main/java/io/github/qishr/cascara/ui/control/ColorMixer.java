package io.github.qishr.cascara.ui.control;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;


public class ColorMixer extends Control {
    private static final StyleablePropertyFactory<ColorMixer> FACTORY = new StyleablePropertyFactory<>(Control.getClassCssMetaData());

    private final ObjectProperty<Color> leftColor = new SimpleObjectProperty<>(Color.WHITE);
    public final ObjectProperty<Color> leftColorProperty() { return leftColor; }
    public final Color getLeftColor() { return leftColor.get(); }
    public final void setLeftColor(Color c) { leftColor.set(c); }

    private final ObjectProperty<Color> mixedColor = new SimpleObjectProperty<>(Color.WHITE);
    public final ObjectProperty<Color> mixedColorProperty() { return mixedColor; }
    public final Color getMixedColor() { return mixedColor.get(); }

    private final ObjectProperty<Color> rightColor = new SimpleObjectProperty<>(Color.WHITE);
    public final ObjectProperty<Color> rightColorProperty() { return rightColor; }
    public final Color getRightColor() { return rightColor.get(); }
    public final void setRightColor(Color c) { rightColor.set(c); }

    private final DoubleProperty lerp = new SimpleDoubleProperty(0.0);
    public final DoubleProperty lerpProperty() { return lerp; }
    public final double getLerp() { return lerp.get(); }
    public final void setLerp(double c) { lerp.set(c); }


    public ColorMixer() {
        super();
    }

    // Skin

    @Override
    protected Skin<ColorMixer> createDefaultSkin() {
        return new ColorMixerSkin(ColorMixer.this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return "";
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return FACTORY.getCssMetaData();
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return FACTORY.getCssMetaData();
    }
}
