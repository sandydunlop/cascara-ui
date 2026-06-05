package io.github.qishr.cascara.ui.control;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class ColorWheelSkin extends SkinBase<ColorWheel> {
    private static final double WHEEL_SIZE = 200;
    private static final double RING_DEPTH = 32;
    private static final double OUTER_RADIUS = WHEEL_SIZE / 2.0;
    private static final double INNER_RADIUS = OUTER_RADIUS - RING_DEPTH;

    private final Canvas hueCanvas;
    private final StackPane wheelContainer;
    private Circle innerCircle;
    private Polygon marker;
    private double currentHueAngle = 0.0;
    private double previousMouseAngle;

    public ColorWheelSkin(final ColorWheel control) {
        super(control);
        hueCanvas = new Canvas(WHEEL_SIZE, WHEEL_SIZE);
        wheelContainer = createColorWheel();
        initGraphics();
        registerListeners();

        control.hueProperty().addListener((obs, oldV, newV) -> drawHueWheel());
        control.saturationProperty().addListener((obs, oldV, newV) -> drawHueWheel());
        control.brightnessProperty().addListener((obs, oldV, newV) -> drawHueWheel());
        control.hueProperty().addListener((obs, oldV, newV) -> {
            drawHueWheel();
            updateRotationFromHue((double)newV);
        });

        drawHueWheel();
        updateRotationFromHue(control.getHue());
    }

    private void initGraphics() {
        StackPane colorWheelPane = new StackPane();
        colorWheelPane.getChildren().addAll(
            wheelContainer,
            createMarker()
        );
        // Position the marker outside the wheel (above it)
        StackPane.setAlignment(wheelContainer, Pos.CENTER);
        HBox mainContainer = new HBox(4);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getChildren().addAll(colorWheelPane);
        getChildren().add(mainContainer);
    }

    private Polygon createMarker() {
        // A small triangle pointing down at the top edge of the wheel
        marker = new Polygon(
            0.0, 15.0,  // Bottom point
            -10.0, 0.0, // Top-left point
            10.0, 0.0  // Top-right point
        );
        marker.setFill(Color.LIGHTGRAY);
        marker.setStroke(Color.DARKGRAY);

        // Position it just above the wheel's top edge
        // Offset is negative because Y-axis goes down
        marker.setTranslateY(-(OUTER_RADIUS + 5));

        marker.setMouseTransparent(true);

        return marker;
    }

    private StackPane createColorWheel() {
        StackPane stack = new StackPane();
        stack.setMaxSize(WHEEL_SIZE, WHEEL_SIZE);
        innerCircle = new Circle(INNER_RADIUS);
        innerCircle.setFill(Color.TRANSPARENT); // Make it transparent!
        innerCircle.setMouseTransparent(true);
        Circle outerCircle = new Circle(OUTER_RADIUS);
        outerCircle.setFill(Color.TRANSPARENT);
        outerCircle.setMouseTransparent(true);
        outerCircle.setStroke(Color.BLACK);
        stack.getChildren().addAll(hueCanvas, innerCircle, outerCircle);
        return stack;
    }

    private void drawHueWheel() {
        GraphicsContext gc = hueCanvas.getGraphicsContext2D();
        double center = OUTER_RADIUS;

        // The key dimensions:
        final double innerRadius = INNER_RADIUS; // The inner edge of the ring
        final double outerRadius = OUTER_RADIUS; // The outer edge of the ring

        gc.clearRect(0, 0, WHEEL_SIZE, WHEEL_SIZE);
        gc.setTransform(1, 0, 0, 1, 0, 0);

        double segmentAngle = 3.0;
        final double OFFSET_DEGREES = 270.0; // To place Red (0 degrees) at the top

        // Draw 360 small wedges
        for (int h = 0; h < 360; h += 3) {

            double angleStart = Math.toRadians(h - (segmentAngle / 2.0) + OFFSET_DEGREES);
            double angleEnd   = Math.toRadians(h + (segmentAngle / 2.0) + OFFSET_DEGREES);

            gc.setFill(Color.hsb(h, 1.0, 1.0));

            // 5. Define the path of the WEDGE (Trapezoid from inner to outer points)
            gc.beginPath();

            // Point 1: Inner point on the starting edge
            // X = center + innerRadius * cos(angleStart)
            // Y = center + innerRadius * sin(angleStart)
            gc.moveTo(center + innerRadius * Math.cos(angleStart),
                      center + innerRadius * Math.sin(angleStart));

            // Point 2: Outer point on the starting edge
            // X = center + outerRadius * cos(angleStart)
            // Y = center + outerRadius * sin(angleStart)
            gc.lineTo(center + outerRadius * Math.cos(angleStart),
                      center + outerRadius * Math.sin(angleStart));

            // Point 3: Outer point on the ending edge (This completes the outer arc segment)
            gc.lineTo(center + outerRadius * Math.cos(angleEnd),
                      center + outerRadius * Math.sin(angleEnd));

            // Point 4: Inner point on the ending edge (This completes the inner arc segment)
            gc.lineTo(center + innerRadius * Math.cos(angleEnd),
                      center + innerRadius * Math.sin(angleEnd));

            gc.closePath();
            gc.fill();
        }
    }

    private void registerListeners() {
        wheelContainer.setOnMousePressed(event -> {
            double centerX = wheelContainer.getWidth() / 2;
            double centerY = wheelContainer.getHeight() / 2;
            double dx = event.getX() - centerX;
            double dy = event.getY() - centerY;
            previousMouseAngle = Math.toDegrees(Math.atan2(dy, dx));
        });

        wheelContainer.setOnMouseDragged(event -> {
            double centerX = wheelContainer.getWidth() / 2;
            double centerY = wheelContainer.getHeight() / 2;
            double dx = event.getX() - centerX;
            double dy = event.getY() - centerY;
            double currentMouseAngle = Math.toDegrees(Math.atan2(dy, dx));
            double deltaAngle = currentMouseAngle - previousMouseAngle;

            // Handle the 360-degree jump (occurs when crossing the -180/180 line)
            if (deltaAngle > 180) {
                deltaAngle -= 360;
            } else if (deltaAngle < -180) {
                deltaAngle += 360;
            }

            currentHueAngle = wheelContainer.getRotate() + deltaAngle;
            wheelContainer.setRotate(currentHueAngle);
            previousMouseAngle = currentMouseAngle;
            updateSelectedColor();
        });

        wheelContainer.setOnScroll(event -> {
            double rotationChange = event.getDeltaY() > 0 ? -5 : 5; // Rotate 5 degrees per scroll click
            currentHueAngle = (wheelContainer.getRotate() + rotationChange);
            wheelContainer.setRotate(currentHueAngle);
            updateSelectedColor();
            event.consume();
        });
    }

    private void updateRotationFromHue(double hue) {
        double newRotation = 360.0 - hue;
        wheelContainer.setRotate(newRotation);
        currentHueAngle = newRotation;
    }

    private void updateSelectedColor() {
        double hue = (360 - (wheelContainer.getRotate() % 360)) % 360;
        hue = (360 + hue) % 360;
        getSkinnable().hueProperty().set(hue);
    }

    @Override
    public void layoutChildren(final double x, final double y, final double width, final double height) {
        super.layoutChildren(x, y, width, height);
    }
}