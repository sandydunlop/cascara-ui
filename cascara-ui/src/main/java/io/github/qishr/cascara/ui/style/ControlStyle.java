package io.github.qishr.cascara.ui.style;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.ui.theme.ThemeEngine;
import javafx.scene.Parent;
import javafx.scene.Scene;
import io.github.qishr.cascara.ui.theme.ColorID;


public abstract class ControlStyle {
    protected static final String SHAPE_ARROW_DOWN = "M 0 0 h 7 l -3.5 4 z";

    protected static final String NONE = "none";
    protected static final String NULL = "null";
    protected static final String TRUE = "true";
    protected static final String FALSE = "false";
    protected static final String ZERO = "0";

    protected static final String TRANSPARENT = "transparent";
    protected static final String NORMAL = "normal";
    protected static final String BOLD = "bold";
    protected static final String BOLDER = "bolder";
    protected static final String LIGHTER = "lighter";
    protected static final String ITALIC = "italic";

    protected static final String SOLID = "solid";

    protected static final String UPPERCASE = "uppercase";

    protected static final String BASELINE_LEFT = "baseline-left";
    protected static final String CENTER = "center";
    protected static final String CENTER_LEFT = "center-left";
    protected static final String CENTER_RIGHT = "center-right";
    protected static final String LEFT = "left";

    protected static final String COLLAPSE = "collapse";

    protected static final String CURSOR_DEFAULT = "default";
    protected static final String CURSOR_HAND = "hand";
    protected static final String CURSOR_TEXT = "text";
    protected static final String CURSOR_WAIT = "wait";

    protected static final String FX_BACKGROUND_COLOR = "-fx-background-color";
    protected static final String FX_BORDER_COLOR = "-fx-border-color";
    protected static final String FX_CELL_FOCUS_INNER_BORDER = "-fx-cell-focus-inner-border";
    protected static final String FX_PADDING = "-fx-padding";
    protected static final String FX_SIZE = "-fx-size";
    protected static final String FX_PREF_HEIGHT = "-fx-pref-height";
    protected static final String FX_TEXT_FILL = "-fx-text-fill";
    protected static final String FX_FONT_FAMILY = "-fx-font-family";
    protected static final String FX_FONT_SIZE = "-fx-font-size";
    protected static final String FX_ALIGNMENT = "-fx-alignment";
    protected static final String FX_BORDER_INSETS = "-fx-border-insets";
    protected static final String FX_BORDER_WIDTH = "-fx-border-width";
    protected static final String FX_BACKGROUND_INSETS = "-fx-background-insets";
    protected static final String FX_OPACITY = "-fx-opacity";
    protected static final String FX_FILL = "-fx-fill";
    protected static final String FX_SCALE_SHAPE = "-fx-scale-shape";
    protected static final String FX_SHAPE = "-fx-shape";
    protected static final String FX_TABLE_CELL_BORDER_COLOR = "-fx-table-cell-border-color";
    protected static final String FX_SCALE_Y = "-fx-scale-y";
    protected static final String FX_ROTATE = "-fx-rotate";
    protected static final String SHRINK_ANIMATE_ON_PRESS = "-shrink-animate-on-press";
    protected static final String FX_BACKGROUND_RADIUS = "-fx-background-radius";
    protected static final String FX_BORDER_STYLE = "-fx-border-style";
    protected static final String FX_BORDER_RADIUS = "-fx-border-radius";
    protected static final String FX_SEPARATOR_STROKE = "-fx-separator-stroke";
    protected static final String FX_PROMPT_TEXT_FILL = "-fx-prompt-text-fill";
    protected static final String FX_HIGHLIGHT_FILL = "-fx-highlight-fill";
    protected static final String FX_HIGHLIGHT_TEXT_FILL = "-fx-highlight-text-fill";
    protected static final String RIGHT_BUTTON_VISIBLE = "-right-button-visible";
    protected static final String FX_CURSOR = "-fx-cursor";
    protected static final String FX_STROKE = "-fx-stroke";
    protected static final String FX_STROKE_WIDTH = "-fx-stroke-wdth";
    protected static final String FX_TAB_MIN_HEIGHT = "-fx-tab-min-height";
    protected static final String FX_TAB_MAX_HEIGHT = "-fx-tab-max-height";
    protected static final String FX_DURATION = "-fx-duration";
    protected static final String FX_EFFECT = "-fx-effect";
    protected static final String FX_MIN_HEIGHT = "-fx-min-height";
    protected static final String FX_MAX_HEIGHT = "-fx-max-height";
    protected static final String VISIBILITY = "visibility";
    protected static final String FX_MANAGED = "-fx-managed";
    protected static final String FX_PREF_WIDTH = "-fx-pref-width";
    protected static final String SHOW_VALUE_ON_INTERACTION = "-show-value-on-interaction";
    protected static final String FX_TICK_LABEL_FILL = "-fx-tick-label-fill";
    protected static final String FX_TICK_LENGTH = "-fx-tick-length";
    protected static final String FX_MINOR_TICK_LENGTH = "-fx-minor-tick-length";
    protected static final String FX_FONT_WEIGHT = "-fx-font-weight";
    protected static final String FX_BOUNDS_TYPE = "-fx-bounds-type";
    protected static final String FX_FONT_SMOOTHING_TYPE = "-fx-font-smoothing-type";
    protected static final String FX_CELL_SIZE = "-fx-cell-size";
    protected static final String FX_SPACING = "-fx-spacing";
    protected static final String FX_TEXT_WRAP = "-fx-text-wrap";
    protected static final String FX_LINE_SPACING = "-fx-line-spacing";
    protected static final String FX_LABEL_PADDING = "-fx-label-padding";
    protected static final String FX_TRANSLATE_Y = "-fx-translate-y";
    protected static final String FX_MIN_WIDTH = "-fx-min-width";
    protected static final String FX_MAX_WIDTH = "-fx-max-width";
    protected static final String FX_TEXT_BOX_BORDER = "-fx-text-box-border";
    protected static final String FX_UNDERLINE = "-fx-underline";
    protected static final String FX_REGION_BACKGROUND = "-fx-region-background";
    protected static final String FX_SKIN = "-fx-skin";
    protected static final String TEXT_TRANSFORM = "text-transform";
    protected static final String FX_CONTENT_DISPLAY = "-fx-content-display";
    protected static final String FX_TRANSLATE_X = "-fx-translate-x";
    protected static final String FX_FONT_STYLE = "-fx-font-style";
    protected static final String FX_PROGRESS_COLOR = "-fx-progress-color";
    protected static final String FX_FOCUS_TRAVERSABLE = "-fx-focus-traversable";

    protected static final String RTFX_UNDERLINE_COLOR = "-rtfx-underline-color";
    protected static final String RTFX_UNDERLINE_WIDTH = "-rtfx-underline-width";
    protected static final String RTFX_UNDERLINE_DASH_ARRAY = "-rtfx-underline-dash-array";

    protected List<String> rules = new ArrayList<>();

    protected ControlStyle() {
    }

    public List<String> getRules() {
        return rules;
    }

    protected void defineRule(String rule) {
        rules.add(rule);
    }

    protected String color(String colorId) {
        return ThemeEngine.cssColorVariable(colorId);
    }

    protected String mapped(String color) {
        return color(ColorID.getDefaultPaletteMapping().getString(color));
    }

    protected String color() {
        return "orange";
    }

    protected StyleRuleBuilder newRule() {
        return new StyleRuleBuilder();
    }

    protected String selector(String s) {
        return s;
    }

    protected String classSelector(String s) {
        return "." + s;
    }

    protected String childSelector(String s) {
        return ">" + s;
    }

    protected String pseudoSelector(String s) {
        return ":" + s;
    }

    protected String px(int n) {
        return String.format("%dpx", n);
    }

    protected String em(double n) {
        return String.format("%.2fem", n);
    }

    protected String degrees(double n) {
        return String.format("%.2fem", n);
    }

    protected String sides(String t, String r, String b, String l) {
        return String.format("%s %s %s %s", t, r, b, l);
    }

    protected String sides(int t, int r, int b, int l) {
        return String.format("%d %d %d %d", t, r, b, l);
    }

    protected String values(String... values) {
        return String.join(", ", values);
    }

    protected String literal(String expression) {
        return expression;
    }

    protected String shape(String expression) {
        return "\"" + expression + "\"";
    }

    protected String derive(String cssColor, int precentage) {
        return String.format("derive(%s,%d%%)", cssColor, precentage);
    }

    protected void incorporateRules(PartStyle partStyle) {
        for (String rule : partStyle.getRules()) {
            rules.add(rule);
        }
    }

    protected String dropshadow(String cssColor) {
        return String.format("dropshadow(gaussian, %s, 10, 0.3, 1.5, 1.5)", cssColor);
    }

    public void applyTo(Scene node) {
        ThemeEngine.instance().applyStyle(this, node);
    }

    public void applyTo(Parent node) {
        ThemeEngine.instance().applyStyle(this, node);
    }
}
