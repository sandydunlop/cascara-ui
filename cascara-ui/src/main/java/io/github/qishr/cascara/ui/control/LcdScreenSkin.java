package io.github.qishr.cascara.ui.control;

import java.util.HashMap;
import java.util.Map;

import io.github.qishr.cascara.ui.color.ColorClipboard;
import io.github.qishr.cascara.ui.color.ColorDefinition;
import io.github.qishr.cascara.ui.color.ColorUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class LcdScreenSkin extends SkinBase<LcdScreen> {
    public static final int PIXELS_WIDE = 41; // 7 chars * 5 px + 6 gaps = 41
    public static final int PIXELS_HIGH = 7;
    public static final double PIXEL_SIZE = 3; // Size of LCD pixel
    public static final double GAP_SIZE = 1.0; // 1px gap between BLACK pixels

    private final Pane screenPane;
    private final Rectangle[][] pixelGrid = new Rectangle[PIXELS_WIDE][PIXELS_HIGH];

    private final HBox overlayPane;
    private final Button copyBgButton;
    private final Button copyFgButton;
    private final Button pasteButton;

    public LcdScreenSkin(final LcdScreen control) {
        super(control);
        this.screenPane = new Pane();
        this.overlayPane = new HBox();
        this.copyBgButton = new Button("Copy BG");
        this.copyFgButton = new Button("Copy FG");
        this.pasteButton = new Button("Paste");
        setupOverlay();
        initializePixelGrid();
        initGraphics();
        registerListeners();
        updateAppearance();
    }

    private void setupOverlay() {
        setButtonAppearance(copyBgButton);
        setButtonAppearance(copyFgButton);
        setButtonAppearance(pasteButton);
        copyBgButton.setOnAction(click -> copyBackgroundColorToClipboard());
        copyFgButton.setOnAction(click -> copyForegroundColorToClipboard());
        pasteButton.setOnAction(click -> pasteColorFromClipboard());
        overlayPane.setPadding(new Insets(0));
        overlayPane.setSpacing(4);
        overlayPane.setVisible(false);
        updateOverlayButtons();
    }

    private void updateOverlayButtons() {
        overlayPane.getChildren().clear();
        overlayPane.getChildren().addAll(copyBgButton, copyFgButton);
        if (getSkinnable().getOnPaste() != null) {
            overlayPane.getChildren().add(pasteButton);
        }
        getSkinnable().requestLayout();
    }

    private void setButtonAppearance(Button button) {
        final double coreContentHeight = PIXELS_HIGH * (PIXEL_SIZE + GAP_SIZE) - GAP_SIZE;
        final String buttonStyle = "-fx-background-color: black; "
                                 + "-fx-padding: 2px 4px; "
                                 + "-fx-font-size: 8pt; ";
        button.setStyle(buttonStyle);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(coreContentHeight);
        HBox.setHgrow(button, Priority.ALWAYS);
    }

    private void initializePixelGrid() {
        for (int x = 0; x < PIXELS_WIDE; x++) {
            for (int y = 0; y < PIXELS_HIGH; y++) {
                Rectangle pixel = new Rectangle(PIXEL_SIZE, PIXEL_SIZE);
                double xPos = x * (PIXEL_SIZE + GAP_SIZE);
                double yPos = y * (PIXEL_SIZE + GAP_SIZE);
                pixel.setTranslateX(xPos);
                pixel.setTranslateY(yPos);
                pixelGrid[x][y] = pixel;
                screenPane.getChildren().add(pixel);
            }
        }
    }

    private void initGraphics() {
        getChildren().add(screenPane);
        getChildren().add(overlayPane);
        getSkinnable().colorProperty().addListener((obs, oldV, newV) -> updateAppearance());
    }

    private void updateAppearance() {
        Color bgColor = getSkinnable().getColor();
        getSkinnable().setStyle("-fx-background-color: " + ColorUtil.toRGBHexCode(bgColor) + ";");

        Color brightColor = Color.hsb(bgColor.getHue(), 1.0, 1.0);
        Color pixelColor = calculatePixelColor(bgColor);

        String fgColorHex = ColorUtil.toRGBHexCode(brightColor);
        String buttonFgStyle = "-fx-text-fill: " + fgColorHex + ";";
        copyBgButton.setStyle(copyBgButton.getStyle() + buttonFgStyle);
        copyFgButton.setStyle(copyFgButton.getStyle() + buttonFgStyle);
        pasteButton.setStyle(pasteButton.getStyle() + buttonFgStyle);
        overlayPane.setBackground(Background.fill(bgColor));
        updateOverlayButtons();

        // Draw the Hex Code or Value
        String textToDraw;
        double value = getSkinnable().getDisplayValue();
        final int MAX_DISPLAY_CHARS = 7;

        if (Double.isNaN(value)) {
            // Default display: Hex Code (e.g., #FFFFFF)
            // Note: Hex codes are 7 chars long including '#', but your pixel grid is 6 chars wide.
            // We'll display the 6-digit code, dropping the '#'.
            textToDraw = ColorUtil.toRGBHexCode(bgColor).toUpperCase();

        } else {
            // Temporary display: The double value
            // Format to MAX_DISPLAY_CHARS (6 characters)
            if (value >= 10000) {
                // e.g., 123456
                textToDraw = String.format("%6.0f", value);
            } else if (value >= 100) {
                // e.g., 123.45 (6 chars)
                 textToDraw = String.format("%5.1f", value);
            } else if (value >= 10) {
                 // e.g., 12.34 (5 chars)
                 textToDraw = String.format("%4.2f", value);
            } else {
                 // e.g., 1.234 (5 chars)
                 textToDraw = String.format("%3.3f", value);
            }

            // Limit to the max length, trimming any extra digits/decimals created by formatting.
            if (textToDraw.length() > MAX_DISPLAY_CHARS) {
                textToDraw = textToDraw.substring(0, MAX_DISPLAY_CHARS);
            }
        }

        // Right-align the text to fill 6 slots
        String alignedText = String.format("%" + MAX_DISPLAY_CHARS + "s", textToDraw);

        // Draw the final text
        drawText(alignedText, bgColor, pixelColor);
    }

    private Color calculatePixelColor(Color bgColor) {
        final double BRIGHTNESS_THRESHOLD = 0.58;
        Color pixelColor;
        if (bgColor.getBrightness() > BRIGHTNESS_THRESHOLD) {
            pixelColor = Color.BLACK;
        } else {
            if (bgColor.getSaturation() == 0) {
                pixelColor = Color.LIGHTGRAY;
            } else {
                pixelColor = Color.hsb(bgColor.getHue(), 1.0, 1.0);
            }
        }
        return pixelColor;
    }

    private void drawText(String text, Color bgColor, Color onPixelColor) {
        int charStartX = 0;

        // The total width of a character block, including the inter-character gap
        final int BLOCK_WIDTH = 5 + 1;

        for (int charIndex = 0; charIndex < text.length(); charIndex++) {
            char c = text.charAt(charIndex);
            // Use 7 rows, 5 columns (y, x)
            boolean[][] glyph = GLYPHS.getOrDefault(c, new boolean[7][5]);

            // Loop over the 5 columns of the actual glyph
            for (int dx = 0; dx < 5; dx++) {
                for (int dy = 0; dy < 7; dy++) {

                    int absoluteX = charStartX + dx;

                    if (absoluteX < PIXELS_WIDE) {
                        Rectangle pixel = pixelGrid[absoluteX][dy];
                        pixel.setVisible(true);

                        if (glyph[dy][dx]) {
                            // Pixel is 'on'
                            pixel.setFill(onPixelColor);
                        } else {
                            // Pixel is 'off' - use the background color
                            pixel.setFill(bgColor);
                        }
                    }
                }
            }

            // The gap space is at charStartX + 5.
            int gapX = charStartX + 5;

            // Check bounds and fill the entire 1px column with the background color
            if (gapX < PIXELS_WIDE) {
                for (int dy = 0; dy < 7; dy++) {
                    Rectangle gapPixel = pixelGrid[gapX][dy];
                    gapPixel.setVisible(true);
                    // Ensure the gap is explicitly set to the background color
                    gapPixel.setFill(bgColor);
                }
            }

            // Advance the starting position by 5 (glyph) + 1 (gap)
            charStartX += BLOCK_WIDTH;
        }
    }

    @Override
    public void layoutChildren(final double x, final double y, final double width, final double height) {
        final Insets padding = getSkinnable().getScreenPadding();

        double coreContentWidth = PIXELS_WIDE * (PIXEL_SIZE + GAP_SIZE) - GAP_SIZE;
        double coreContentHeight = PIXELS_HIGH * (PIXEL_SIZE + GAP_SIZE) - GAP_SIZE;

        double totalWidth = coreContentWidth + padding.getLeft() + padding.getRight();
        double totalHeight = coreContentHeight + padding.getTop() + padding.getBottom();

        getSkinnable().setMinSize(totalWidth, totalHeight); // Without this, she skin shrinks and the display becomes off-center
        getSkinnable().setMaxSize(totalWidth, totalHeight); // Without this, the skin expands to the parent container size

        screenPane.setPrefSize(coreContentWidth, coreContentHeight);
        screenPane.setMaxSize(coreContentWidth, coreContentHeight);

        overlayPane.setPrefSize(coreContentWidth, coreContentHeight);
        overlayPane.setMaxSize(coreContentWidth, coreContentHeight);

        super.layoutChildren(0, 0, totalWidth, totalHeight);
    }

    private void registerListeners() {
        getSkinnable().colorProperty().addListener((obs, oldV, newV) -> updateAppearance());
        getSkinnable().displayValueProperty().addListener((obs, oldV, newV) -> updateAppearance());
        getSkinnable().setOnMouseEntered(e -> overlayPane.setVisible(true));
        getSkinnable().setOnMouseExited(e -> overlayPane.setVisible(false));
    }

    /// Copies the background color as a serialized ColorDefinition to the clipboard.
    private void copyBackgroundColorToClipboard() {
        final LcdScreen skinnable = getSkinnable();
        ColorDefinition definitionToCopy;
        if (skinnable.getColorDefinition() != null) {
            definitionToCopy = skinnable.getColorDefinition();
        } else {
            Color bgColor = skinnable.getColor();
            String hex = ColorUtil.toRGBHexCode(bgColor).toUpperCase();
            definitionToCopy = new ColorDefinition();
            definitionToCopy.setHexColor(hex);
        }
        ColorClipboard.put(definitionToCopy);
    }

    /// Copies the foreground (pixel) color as a serialized ColorDefinition to the clipboard.
    private void copyForegroundColorToClipboard() {
        Color pixelColor = calculatePixelColor(getSkinnable().getColor());
        String hex = ColorUtil.toRGBHexCode(pixelColor).toUpperCase();
        ColorDefinition definition = new ColorDefinition(hex);
        ColorClipboard.put(definition);
    }

    /// Attempts to paste a ColorDefinition from the clipboard (via YAML) and set it as the new background color.
    private void pasteColorFromClipboard() {
        ColorDefinition definition = ColorClipboard.get();
        if (definition == null) {
            // No color to paste
        } else {
            Color newColor = Color.web(definition.getHexColor());
            getSkinnable().setColor(newColor);
            getSkinnable().onPaste(definition.getHexColor());
        }
    }

    private static final Map<Character, boolean[][]> GLYPHS = new HashMap<>();

    static {
        // Defines the 5 columns (x) by 7 rows (y) for each character.
        // true = pixel ON, false = pixel OFF

        // --- Hash Symbol ---
        GLYPHS.put('#', new boolean[][] {
            {false, true, false, true, false},
            {true, true, true, true, true},
            {false, true, false, true, false},
            {true, true, true, true, true},
            {false, true, false, true, false},
            {false, true, false, true, false},
            {false, false, false, false, false}
        });

        // --- Digits (0-9) ---
        GLYPHS.put('0', new boolean[][] {
            {false, true, true, true, false},
            {true, false, false, false, true},
            {true, false, false, true, true},
            {true, false, true, false, true},
            {true, true, false, false, true},
            {true, false, false, false, true},
            {false, true, true, true, false}
        });

        GLYPHS.put('1', new boolean[][] {
            {false, false, true, false, false},
            {false, true, true, false, false},
            {false, false, true, false, false},
            {false, false, true, false, false},
            {false, false, true, false, false},
            {false, false, true, false, false},
            {false, true, true, true, false}
        });

        GLYPHS.put('2', new boolean[][] {
            {false, true, true, true, false},
            {true, false, false, false, true},
            {false, false, false, false, true},
            {false, false, false, true, false},
            {false, false, true, false, false},
            {false, true, false, false, false},
            {true, true, true, true, true}
        });

        GLYPHS.put('3', new boolean[][] {
            {false, true, true, true, false},
            {true, false, false, false, true},
            {false, false, false, false, true},
            {false, false, true, true, false},
            {false, false, false, false, true},
            {true, false, false, false, true},
            {false, true, true, true, false}
        });

        GLYPHS.put('4', new boolean[][] {
            {false, false, false, true, false},
            {false, false, true, true, false},
            {false, true, false, true, false},
            {true, false, false, true, false},
            {true, true, true, true, true},
            {false, false, false, true, false},
            {false, false, false, true, false}
        });

        GLYPHS.put('5', new boolean[][] {
            {true, true, true, true, true},
            {true, false, false, false, false},
            {true, true, true, true, false},
            {false, false, false, false, true},
            {false, false, false, false, true},
            {true, false, false, false, true},
            {false, true, true, true, false}
        });

        GLYPHS.put('6', new boolean[][] {
            {false, true, true, true, false},
            {true, false, false, false, false},
            {true, false, false, false, false},
            {true, true, true, true, false},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {false, true, true, true, false}
        });

        GLYPHS.put('7', new boolean[][] {
            {true, true, true, true, true},
            {false, false, false, false, true},
            {false, false, false, false, true},
            {false, false, false, true, false},
            {false, false, true, false, false},
            {false, true, false, false, false},
            {false, true, false, false, false}
        });

        GLYPHS.put('8', new boolean[][] {
            {false, true, true, true, false},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {false, true, true, true, false},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {false, true, true, true, false}
        });

        GLYPHS.put('9', new boolean[][] {
            {false, true, true, true, false},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {false, true, true, true, true},
            {false, false, false, false, true},
            {false, false, false, false, true},
            {false, true, true, true, false}
        });

        // --- Hex Letters (A-F) ---
        GLYPHS.put('A', new boolean[][] {
            {false, true, true, true, false},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {true, true, true, true, true},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {true, false, false, false, true}
        });

        GLYPHS.put('B', new boolean[][] {
            {true, true, true, true, false},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {true, true, true, true, false},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {true, true, true, true, false}
        });

        GLYPHS.put('C', new boolean[][] {
            {false, true, true, true, false},
            {true, false, false, false, true},
            {true, false, false, false, false},
            {true, false, false, false, false},
            {true, false, false, false, false},
            {true, false, false, false, true},
            {false, true, true, true, false}
        });

        GLYPHS.put('D', new boolean[][] {
            {true, true, true, true, false},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {true, false, false, false, true},
            {true, true, true, true, false}
        });

        GLYPHS.put('E', new boolean[][] {
            {true, true, true, true, true},
            {true, false, false, false, false},
            {true, false, false, false, false},
            {true, true, true, true, false},
            {true, false, false, false, false},
            {true, false, false, false, false},
            {true, true, true, true, true}
        });

        GLYPHS.put('F', new boolean[][] {
            {true, true, true, true, true},
            {true, false, false, false, false},
            {true, false, false, false, false},
            {true, true, true, true, false},
            {true, false, false, false, false},
            {true, false, false, false, false},
            {true, false, false, false, false}
        });

        GLYPHS.put('.', new boolean[][] {
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, true, true, false, false}, // Positioned low on the 7-row matrix
            {false, true, true, false, false}  // Two pixels wide for visibility
        });

        // Space (to ensure drawText pads correctly)
        GLYPHS.put(' ', new boolean[][] {
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false}
        });
    }
}
