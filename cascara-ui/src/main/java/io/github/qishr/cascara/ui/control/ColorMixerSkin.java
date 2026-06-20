package io.github.qishr.cascara.ui.control;

import io.github.qishr.cascara.ui.color.ColorDefinition;
import io.github.qishr.cascara.ui.color.ColorException;
import io.github.qishr.cascara.ui.color.ColorUtil;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class ColorMixerSkin extends SkinBase<ColorMixer> {
    private static final String STRING_SATURATION = "Sat";
    private static final String STRING_BRIGHTNESS = "Bri";

    private Color leftColor;
    private Color rightColor;

    private LcdScreen leftScreen;
    private LcdScreen rightScreen;

    private ColorWheel leftWheel;
    private ColorWheel rightWheel;
    private LcdScreen centerScreen;
    private Slider lerpSlider;
    private boolean updating = false;


    public ColorMixerSkin(final ColorMixer control) {
        super(control);
        initGraphics();
        updateLerpColor();
        Platform.runLater(() -> {
            registerListeners();
        });
    }

    private void initGraphics() {
        leftColor = getSkinnable().getLeftColor();
        rightColor = getSkinnable().getRightColor();

        // Left LcdScreen and ColorWheel

        leftScreen = new LcdScreen();
        leftScreen.setColor(leftColor);
        leftScreen.setScreenPadding(new Insets(8));
        leftWheel = new ColorWheel();
        leftWheel.setColor(leftColor);
        leftWheel.setPadding(new Insets(20, 0, 0, 0));
        VBox left = new VBox(leftScreen, leftWheel);
        left.setAlignment(Pos.TOP_CENTER);
        leftScreen.setViewOrder(-1.0);

        // Left Sliders

        Slider leftSatSlider = createSlider(leftWheel.getSaturation());
        Slider leftBriSlider = createSlider(leftWheel.getBrightness());
        leftWheel.saturationProperty().bindBidirectional(leftSatSlider.valueProperty());
        leftWheel.brightnessProperty().bindBidirectional(leftBriSlider.valueProperty());
        Label leftSatLabel = createLabel(STRING_SATURATION);
        Label leftBriLabel = createLabel(STRING_BRIGHTNESS);
        VBox leftSatBox = new VBox(leftSatLabel, leftSatSlider);
        VBox leftBriBox = new VBox(leftBriLabel, leftBriSlider);
        leftSatBox.setAlignment(Pos.CENTER);
        leftBriBox.setAlignment(Pos.CENTER);

        // Right LcdScreen and ColorWheel

        rightScreen = new LcdScreen();
        rightScreen.setColor(rightColor);
        rightScreen.setScreenPadding(new Insets(8));
        rightWheel = new ColorWheel();
        rightWheel.setColor(rightColor);
        rightWheel.setPadding(new Insets(20, 0, 0, 0));
        VBox right = new VBox(rightScreen, rightWheel);
        right.setAlignment(Pos.TOP_CENTER);
        rightScreen.setViewOrder(-1.0);

        // Right Sliders

        Slider rightSatSlider = createSlider(rightWheel.getSaturation());
        Slider rightBriSlider = createSlider(rightWheel.getBrightness());
        rightWheel.saturationProperty().bindBidirectional(rightSatSlider.valueProperty());
        rightWheel.brightnessProperty().bindBidirectional(rightBriSlider.valueProperty());
        Label rightSatLabel = createLabel(STRING_SATURATION);
        Label rightBriLabel = createLabel(STRING_BRIGHTNESS);
        VBox rightSatBox = new VBox(rightSatLabel, rightSatSlider);
        VBox rightBriBox = new VBox(rightBriLabel, rightBriSlider);
        rightSatBox.setAlignment(Pos.CENTER);
        rightBriBox.setAlignment(Pos.CENTER);

        // Center Screen and Sliders

        centerScreen = new LcdScreen();
        centerScreen.setColor(Color.MOCCASIN);
        centerScreen.setScreenPadding(new Insets(8));
        lerpSlider = createLerpSlider(0);
        lerpSlider.setMaxWidth(180);
        lerpSlider.setPadding(new Insets(0));
        VBox lerpSliderBox = new VBox(createLabel("Linear Interpolation"), lerpSlider);
        lerpSliderBox.setSpacing(0);
        lerpSliderBox.setPadding(new Insets(8, 16, 16, 16));
        lerpSliderBox.setAlignment(Pos.TOP_CENTER);

        // Connect Sliders to Screens

        lerpSlider.valueProperty().addListener((obs, oldValue, newValue) -> centerScreen.showValue(newValue.doubleValue()));
        leftSatSlider.valueProperty().addListener((obs, oldValue, newValue) -> leftScreen.showValue(newValue.doubleValue()));
        leftBriSlider.valueProperty().addListener((obs, oldValue, newValue) -> leftScreen.showValue(newValue.doubleValue()));
        rightSatSlider.valueProperty().addListener((obs, oldValue, newValue) -> rightScreen.showValue(newValue.doubleValue()));
        rightBriSlider.valueProperty().addListener((obs, oldValue, newValue) -> rightScreen.showValue(newValue.doubleValue()));

        // Handle Clipboard Paste

        leftScreen.setOnPaste(hexColor -> handlePasteColor(hexColor, leftWheel, leftSatSlider, leftBriSlider));
        rightScreen.setOnPaste(hexColor -> handlePasteColor(hexColor, rightWheel, rightSatSlider, rightBriSlider));

        // Layout

        Region spacer = new Region();
        HBox hbSliders = new HBox(leftSatBox, leftBriBox, spacer, rightSatBox, rightBriBox);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hbSliders.setAlignment(Pos.CENTER);
        hbSliders.setMinHeight(150);
        hbSliders.setSpacing(8);

        VBox center = new VBox(centerScreen, lerpSliderBox, hbSliders);
        center.setAlignment(Pos.TOP_CENTER);

        HBox all = new HBox(left, center, right);
        all.setPadding(new Insets(0));
        all.setSpacing(16);

        getChildren().addAll(all);
    }

    private Label createLabel(String text) {
        Label label = new Label(text.toUpperCase());
        label.setStyle("-fx-font-size: 0.7em");
        return label;
    }

    private Slider createSlider(double value) {
        Slider slider = new Slider(0, 1, value);
        slider.setOrientation(javafx.geometry.Orientation.VERTICAL);
        return slider;
    }

    private Slider createLerpSlider(double value) {
        Slider slider = new Slider(0, 1, value);
        slider.setOrientation(javafx.geometry.Orientation.HORIZONTAL);
        return slider;
    }

    private ColorDefinition updateLerpColor() {
        ColorDefinition definition = new ColorDefinition();
        definition.setLeftHexColor(ColorUtil.toRGBHexCode(leftColor));
        definition.setRightHexColor(ColorUtil.toRGBHexCode(rightColor));
        definition.setLerp(Double.toString(lerpSlider.getValue()));
        try{
            if (centerScreen != null && lerpSlider != null) {
                ColorUtil.processColor(definition, null);
                centerScreen.setColorDefinition(definition);
                getSkinnable().lerpProperty().set(lerpSlider.getValue());
            }
        } catch (ColorException e) {
            e.printStackTrace();
        }
        return definition;
    }

    private void handlePasteColor(String hexColor, ColorWheel colorWheel, Slider satSlider, Slider briSlider) {
        Color color = Color.web(hexColor);
        colorWheel.setColor(color);
        satSlider.setValue(color.getSaturation());
        briSlider.setValue(color.getBrightness());
        leftColor = leftWheel.getColor();
        rightColor = rightWheel.getColor();
        ColorDefinition colordef = updateLerpColor();
        getSkinnable().mixedColorProperty().set(Color.web(colordef.getHexColor()));
    }

    private void registerListeners() {
        getSkinnable().leftColorProperty().addListener((obs, o, n) -> leftWheel.setColor(n));
        getSkinnable().rightColorProperty().addListener((obs, o, n) -> rightWheel.setColor(n));
        getSkinnable().lerpProperty().addListener((obs, o, n) -> {
            lerpSlider.setValue(n.doubleValue());
        });
        leftWheel.setOnChange(hexColor -> {
            if (!updating) {
                updating = true;
                Color newColor = Color.web(hexColor);
                leftScreen.setColor(newColor);
                leftColor = newColor;
                ColorDefinition colordef = updateLerpColor();
                getSkinnable().mixedColorProperty().set(Color.web(colordef.getHexColor()));
                getSkinnable().leftColorProperty().set(leftColor);
                updating = false;
            }
        });
        rightWheel.setOnChange(hexColor -> {
            if (!updating) {
                updating = true;
                Color newColor = Color.web(hexColor);
                rightScreen.setColor(newColor);
                rightColor = newColor;
                ColorDefinition colordef = updateLerpColor();
                getSkinnable().mixedColorProperty().set(Color.web(colordef.getHexColor()));
                getSkinnable().rightColorProperty().set(rightColor);
                updating = false;
            }
        });
        lerpSlider.valueProperty().addListener((obs, oldV, newT) -> {
            ColorDefinition colordef = updateLerpColor();
            getSkinnable().mixedColorProperty().set(Color.web(colordef.getHexColor()));
        });
    }
}
