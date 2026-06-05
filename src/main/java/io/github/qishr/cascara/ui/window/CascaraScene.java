package io.github.qishr.cascara.ui.window;

import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class CascaraScene extends Scene {
    private static final double RESIZE_BORDER_WIDTH = 5.0;
    private static final double SHADOW_SIZE = 15;

    private Parent root;
    private Stage enclosingStage;
    private StackPane stagePane;
    private MenuBar menuBar;

    private boolean windowResizing = false;
    private double mousePressX; // Initial mouse X on press
    private double mousePressY; // Initial mouse Y on press
    private double initialX;
    private double initialY;
    private double initialWidth; // Stage width on press
    private double initialHeight; // Stage height on press
    private ResizeEdge currentEdge = null;

    private TitleBar titleBar;
    private VBox windowLayout;
    private DropShadow shadow;
    private StackPane shadowWrapper;
    private Rectangle2D preMaximizedBounds;
    private TitleBarTheme titleBarTheme;

    // private boolean useNativeTitleBar = false;

    // Enum to represent resize edges/corners
    private enum ResizeEdge {
        NONE, LEFT, RIGHT, TOP, BOTTOM, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    public CascaraScene(@NamedArg("root") Parent root) {
        super(root);
        this.root = root;
        init();
    }

    public CascaraScene(@NamedArg("root") Parent root, @NamedArg("width") double width, @NamedArg("height") double height) {
        super(root, width, height);
        this.root = root;
        init();
    }

    public CascaraScene(@NamedArg("root") Parent root, @NamedArg("width") double width, @NamedArg("height") double height, MenuBar menuBar) {
        super(root, width, height);
        this.root = root;
        this.menuBar = menuBar;
        init();
    }

    public CascaraScene(@NamedArg("root") Parent root, @NamedArg(value="fill", defaultValue="WHITE") Paint fill) {
        super(root, fill);
        this.root = root;
        init();
    }

    public CascaraScene(@NamedArg("root") Parent root, @NamedArg("width") double width, @NamedArg("height") double height,
            @NamedArg(value="fill", defaultValue="WHITE") Paint fill) {
        super(root, width, height, false, SceneAntialiasing.DISABLED);
        this.root = root;
        init();
    }

    public VBox getWindowLayout() {
        return windowLayout;
    }

    public void setTitle(String title) {
        if (titleBar != null) {
            titleBar.setTitle(title);
        }

        // setTitle(title);
    }

    public void setTitlebarStyle(String id) {
        TitleBarTheme previousTitleBarTheme = titleBarTheme;
        //useNativeTitleBar
        titleBarTheme = null;
        if (id.equals("platinum")) {
            titleBarTheme = TitleBarTheme.macSystem8();
        } else if (id.equals("aero")) {
            titleBarTheme = TitleBarTheme.windows7();
        } else if (id.equals("modern")) {
            titleBarTheme = TitleBarTheme.windows10();
        }

        if (titleBarTheme == null) {
            if (previousTitleBarTheme != null) {
                initNativeWindow();
            }
        } else {
            if (previousTitleBarTheme == null) {
                initCustomWindow();
            }
            titleBar.applyTheme(
                titleBarTheme,
                () -> onClose.run(),
                () -> enclosingStage.setIconified(true),
                () -> enclosingStage.setMaximized(!enclosingStage.isMaximized())
            );
        }
    }


    private void init() {
        onClose = () -> {
            Event event =  new WindowEvent(
                getEnclosingStage(),
                WindowEvent.WINDOW_CLOSE_REQUEST
            );
            getEnclosingStage().fireEvent(event);
        };


        stagePane = new StackPane();

        initMenuBar();

        setTitlebarStyle("platinum");








    }

    private void initNativeWindow() {
        stagePane.getChildren().clear();

        stagePane.getChildren().add(root);
        // if (windowLayout != null) {
        //     windowLayout.getChildren().remove(root);
        // }
        // setRoot(root);

        // initMenuBar();
    }

    private void initCustomWindow() {
        stagePane.getChildren().clear();

        stagePane.getStyleClass().add("cascara-stage-pane");
        getStylesheets().add(getClass().getResource("titlebar.css").toExternalForm());

        // 1. Vertical Container for the UI
        windowLayout = new VBox();
        windowLayout.getStyleClass().add("cascara-window-layout");
        windowLayout.setBackground(Background.EMPTY);
        root.getStyleClass().add("cascara-stage-root");
        this.setFill(Color.TRANSPARENT);



        titleBar = new TitleBar();



        // 3. Root Content
        VBox.setVgrow(root, Priority.ALWAYS);

        // 4. Assemble: Title Bar on top, Root fills the rest
        windowLayout.getChildren().addAll(titleBar, root);

        // Inside CascaraScene.init() or applyTheme
        DropShadow shadow = new DropShadow();
        shadow.setRadius(15.0);
        shadow.setOffsetX(0);
        shadow.setOffsetY(5.0);
        shadow.setColor(Color.rgb(0, 0, 0, 0.5));

        windowLayout.setEffect(shadow);
        windowLayout.setCache(true);
        windowLayout.setCacheHint(CacheHint.SPEED);



        // initMenuBar();



        // 2. Wrap it in a container that provides "Shadow Room"
        shadow = new DropShadow(15, Color.BLACK);
        shadowWrapper = new StackPane(windowLayout);
        shadowWrapper.setPadding(new Insets(SHADOW_SIZE)); // Room for shadow
        windowLayout.setEffect(shadow);

        // 3. Add the wrapper to the stagePane (which handles the Resize Border)
        stagePane.getChildren().add(shadowWrapper);
        stagePane.setPadding(new Insets(RESIZE_BORDER_WIDTH)); // Actual draggable area

        windowLayout.setPickOnBounds(false);
        stagePane.setPickOnBounds(true);


        setRoot(stagePane);


        enclosingStage = getEnclosingStage();
        addResizeHandlers();
        bind(this);
    }

    public void initMenuBar() {
        // 5. Setup native macOS MenuBar
        if (menuBar != null) {
            menuBar.setUseSystemMenuBar(true);
            // We add it to stagePane just to keep it in the Scene Graph,
            // but it won't take up any physical pixels in the window.
            stagePane.getChildren().add(menuBar);
            menuBar.setVisible(true);
        }

    }

    public void setupMenuBarShortcuts() {
        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            // Toggle with Alt (Windows/Linux) or Cmd+Shift+M (Generic)
            boolean isToggleKey = (event.getCode() == KeyCode.ALT) ||
                                (event.isShortcutDown() && event.isShiftDown() && event.getCode() == KeyCode.M);

            if (isToggleKey) {
                setMenuBarVisible(!menuBar.isVisible());
                event.consume(); // Prevent the event from triggering other actions
            }
        });
    }

    public void setMenuBarVisible(boolean visible) {
        if (menuBar != null) {
            menuBar.setVisible(visible);
            menuBar.setManaged(visible);

            // If the window is currently maximized, hiding the menu bar
            // might require a small layout pulse to ensure the root fills correctly
            stagePane.requestLayout();
        }
    }

    private Stage getEnclosingStage() {
        Window w = getWindow();
        if (w instanceof Stage stage) {
            return stage;
        }
        return null;
    }


    Runnable onClose = null;
    public void setOnClose(Runnable r) {
        onClose = r;
    }


    private void refreshShadow() {
        // Safety: Never draw shadow if maximized
        enclosingStage = getEnclosingStage();
        if (enclosingStage == null) return;

        if (enclosingStage.isMaximized()) {
            windowLayout.setEffect(null);
            return;
        }

        Platform.runLater(() -> {
            ShadowProfile profile = enclosingStage.isFocused() ?
                titleBarTheme.activeProfile() : titleBarTheme.inactiveProfile();

            DropShadow newShadow = new DropShadow(profile.radius, profile.color);
            newShadow.setOffsetX(profile.offset);
            newShadow.setOffsetY(profile.offset);

            windowLayout.setEffect(newShadow);
        });
    }


    public void bind(Scene scene) {
        titleBar.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (isButton(event)) return;

            // Use Screen coordinates to avoid "relative jumping"
            // Subtract the current Stage position and the padding to get the "Local Offset"
            mousePressX = event.getScreenX() - enclosingStage.getX() - RESIZE_BORDER_WIDTH;
            mousePressY = event.getScreenY() - enclosingStage.getY() - RESIZE_BORDER_WIDTH;
        });

        titleBar.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            if (isButton(event)) return;

            double xOffset = mousePressX + RESIZE_BORDER_WIDTH;
            double yOffset = mousePressY + RESIZE_BORDER_WIDTH;
            double newX = event.getScreenX() - xOffset;
            double newY = event.getScreenY() - yOffset;

            // Keep at least 40px of the title bar on screen at the top
            // and prevent dragging completely off the sides.
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

            // Clamp Y so the title bar doesn't go above the menu bar or below the bottom
            newY = Math.max(visualBounds.getMinY(), Math.min(newY, visualBounds.getMaxY() - 40));

            enclosingStage.setX(newX);
            enclosingStage.setY(newY);
        });
        titleBar.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            // 1. Only act on double-clicks
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {

                // 2. Ensure we aren't double-clicking a button
                Node target = (Node) event.getTarget();
                boolean isButton = target instanceof Button || target.getParent() instanceof Button;

                if (!isButton) {
                    Stage stage = (Stage) getWindow();
                    stage.setMaximized(!stage.isMaximized());

                    // 3. If we maximize, we might want to remove the shadow/padding
                    // for a "Full Screen" look, or keep it for "Zoom" look.
                    event.consume();
                }
            }
        });
        scene.windowProperty().addListener((obs, oldWindow, newWindow) -> {
            if (newWindow instanceof Stage) {
                Stage stage = (Stage) newWindow;
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initStyle(StageStyle.TRANSPARENT);

                stage.focusedProperty().addListener((focusObs, wasFocused, isFocused) -> {
                    // Toggle the :inactive pseudo-class
                    titleBar.pseudoClassStateChanged(
                        PseudoClass.getPseudoClass("inactive"), !isFocused
                    );
                    stagePane.pseudoClassStateChanged(
                        PseudoClass.getPseudoClass("inactive"), !isFocused
                    );
                    refreshShadow();
                });

                // Now that we have the stage, we can safely listen for maximize
                stage.maximizedProperty().addListener((mObs, wasMax, isMax) -> {
                    if (isMax) {
                        // 1. SAVE the current state before we lose it
                        if (stage.getWidth() < Screen.getPrimary().getVisualBounds().getWidth()) {
                            preMaximizedBounds = new Rectangle2D(
                                stage.getX(),
                                stage.getY(),
                                stage.getWidth(),
                                stage.getHeight()
                            );
                        }

                        // 2. Remove shadow and padding for clean full-screen look
                        windowLayout.setEffect(null);
                        stagePane.setPadding(Insets.EMPTY);
                        shadowWrapper.setPadding(Insets.EMPTY);
                        titleBar.getStyleClass().add("maximized");

                        windowLayout.setFillWidth(true); // Ensures VBox children stretch to the new edge
                        VBox.setVgrow(root, Priority.ALWAYS); // Ensures the main content area expands to fill height
                    } else {
                        // 3. Restore shadow and padding for floating look
                        windowLayout.setEffect(shadow);
                        stagePane.setPadding(new Insets(RESIZE_BORDER_WIDTH));
                        shadowWrapper.setPadding(new Insets(SHADOW_SIZE));
                        titleBar.getStyleClass().remove("maximized");

                        // 4. Move and resize the stage back to its "floating" memory
                        if (preMaximizedBounds != null) {
                            stage.setX(preMaximizedBounds.getMinX());
                            stage.setY(preMaximizedBounds.getMinY());
                            stage.setWidth(preMaximizedBounds.getWidth());
                            stage.setHeight(preMaximizedBounds.getHeight());
                        }

                        // 5. Re-apply shadow on the next pulse
                        windowLayout.setCache(false); // Turn off cache to force redraw
                        windowLayout.setEffect(null);

                        refreshShadow(); // Re-applies the correct shadow for the current focus state
                    }
                });
            }
        });
        // this.cursorProperty().addListener((obs,old,val) -> {
        //     Thread.dumpStack();
        // });
    }


    private boolean isButton(Event event) {
        Node target = (Node) event.getTarget();
        return target instanceof Button || target.getParent() instanceof Button;
    }


    private void addResizeHandlers() {
        // 1. Use an EventFilter so children don't "steal" the move events
        stagePane.addEventFilter(MouseEvent.MOUSE_MOVED, mouse -> {
            if (!windowResizing) {
                // Get local coordinates relative to the layout container
                Point2D local = windowLayout.sceneToLocal(mouse.getSceneX(), mouse.getSceneY());

                ResizeEdge oldEdge = currentEdge;
                currentEdge = getResizeEdge(local.getX(), local.getY());

                // Only update the cursor if the edge actually changed to save performance
                if (oldEdge != currentEdge) {
                    updateCursor();
                }
            }
        });

        // 2. Clear edge when leaving the window entirely
        stagePane.addEventFilter(MouseEvent.MOUSE_EXITED, mouse -> {
            if (!windowResizing) {
                currentEdge = ResizeEdge.NONE;
                updateCursor();
            }
        });

        stagePane.setOnMousePressed(mouse -> {
            if (currentEdge != ResizeEdge.NONE) {
                windowResizing = true;
                mousePressX = mouse.getScreenX();
                mousePressY = mouse.getScreenY();
                initialX = enclosingStage.getX();
                initialY = enclosingStage.getY();
                initialWidth = enclosingStage.getWidth();
                initialHeight = enclosingStage.getHeight();
            }
        });
        stagePane.setOnMouseDragged(mouse -> {
            if (currentEdge != ResizeEdge.NONE) {
                resizeStage(mouse);
            }
        });
        stagePane.setOnMouseReleased(mouse -> {
            windowResizing = false;
        });
    }


    private ResizeEdge getResizeEdge(double localX, double localY) {
        double grab = 5.0;     // How many pixels inside/outside the line count
        double corner = 15.0;  // Size of the corner hitbox

        enclosingStage = getEnclosingStage();
        if (enclosingStage == null) return ResizeEdge.NONE;

        // Check edges relative to the visible window bounds (0 to Width)
        boolean isLeft   = Math.abs(localX) <= grab;
        boolean isRight  = Math.abs(localX - windowLayout.getWidth()) <= grab;
        boolean isTop    = Math.abs(localY) <= grab;
        boolean isBottom = Math.abs(localY - windowLayout.getHeight()) <= grab;

        // Refine for Corners
        if ((isTop || isBottom) && Math.abs(localX) <= corner) isLeft = true;
        if ((isTop || isBottom) && Math.abs(localX - windowLayout.getWidth()) <= corner) isRight = true;
        if ((isLeft || isRight) && Math.abs(localY) <= corner) isTop = true;
        if ((isLeft || isRight) && Math.abs(localY - windowLayout.getHeight()) <= corner) isBottom = true;

        // Return the specific Edge
        if (isTop && isLeft) return ResizeEdge.TOP_LEFT;
        if (isTop && isRight) return ResizeEdge.TOP_RIGHT;
        if (isBottom && isLeft) return ResizeEdge.BOTTOM_LEFT;
        if (isBottom && isRight) return ResizeEdge.BOTTOM_RIGHT;
        if (isLeft) return ResizeEdge.LEFT;
        if (isRight) return ResizeEdge.RIGHT;
        if (isTop) return ResizeEdge.TOP;
        if (isBottom) return ResizeEdge.BOTTOM;

        return ResizeEdge.NONE;
    }


    private void resizeStage(MouseEvent event) {
        double deltaX = event.getScreenX() - mousePressX;
        double deltaY = event.getScreenY() - mousePressY;
        if (currentEdge == null || enclosingStage == null) return;

        switch (currentEdge) {
            case LEFT:
                double newWidthLeft = initialWidth - deltaX;
                enclosingStage.setWidth(Math.max(newWidthLeft, 200));
                enclosingStage.setX(initialX + deltaX);
                break;
            case RIGHT:
                double newWidthRight = initialWidth + deltaX;
                enclosingStage.setWidth(Math.max(newWidthRight, 200));
                break;
            case TOP:
                double newHeightTop = initialHeight - deltaY;
                enclosingStage.setHeight(Math.max(newHeightTop, 200));
                enclosingStage.setY(initialY + deltaY);
                break;
            case BOTTOM:
                double newHeightBottom = initialHeight + deltaY;
                enclosingStage.setHeight(Math.max(newHeightBottom, 200));
                break;
            case TOP_LEFT:
                double newWidthTL = initialWidth - deltaX;
                double newHeightTL = initialHeight - deltaY;
                enclosingStage.setWidth(Math.max(newWidthTL, 200));
                enclosingStage.setHeight(Math.max(newHeightTL, 200));
                enclosingStage.setX(initialX + deltaX);
                enclosingStage.setY(initialY + deltaY);
                break;
            case TOP_RIGHT:
                double newWidthTR = initialWidth + deltaX;
                double newHeightTR = initialHeight - deltaY;
                enclosingStage.setWidth(Math.max(newWidthTR, 200));
                enclosingStage.setHeight(Math.max(newHeightTR, 200));
                enclosingStage.setY(initialY + deltaY);
                break;
            case BOTTOM_LEFT:
                double newWidthBL = initialWidth - deltaX;
                double newHeightBL = initialHeight + deltaY;
                enclosingStage.setWidth(Math.max(newWidthBL, 200));
                enclosingStage.setHeight(Math.max(newHeightBL, 200));
                enclosingStage.setX(initialX + deltaX);
                break;
            case BOTTOM_RIGHT:
                double newWidthBR = initialWidth + deltaX;
                double newHeightBR = initialHeight + deltaY;
                enclosingStage.setWidth(Math.max(newWidthBR, 200));
                enclosingStage.setHeight(Math.max(newHeightBR, 200));
                break;
            default:
                // No resize
        }
    }


    private void updateCursor() {
        switch (currentEdge) {
            case LEFT, RIGHT:
                stagePane.setCursor(Cursor.H_RESIZE); // Horizontal resize
                break;
            case TOP, BOTTOM:
                stagePane.setCursor(Cursor.V_RESIZE); // Vertical resize
                break;
            case TOP_LEFT:
                stagePane.setCursor(Cursor.NW_RESIZE);
                break;
            case BOTTOM_RIGHT:
                stagePane.setCursor(Cursor.SE_RESIZE);
                break;
            case TOP_RIGHT:
                stagePane.setCursor(Cursor.NE_RESIZE);
                break;
            case BOTTOM_LEFT:
                stagePane.setCursor(Cursor.SW_RESIZE);
                break;
            default:
                stagePane.setCursor(null);
        }
    }

    public void setMousePointer(Cursor cursor) {
        if (cursor == Cursor.WAIT) {
            String c = "crosshair";
            root.setStyle("-fx-cursor: "+c+";");
        } else {
            root.setStyle("-fx-cursor: null;");
        }
    }
}
