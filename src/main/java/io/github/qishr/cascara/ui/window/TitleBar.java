package io.github.qishr.cascara.ui.window;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class TitleBar extends BorderPane {
    private final Label titleLabel = new Label();
    private final HBox leftContainer = new HBox();
    private final HBox rightContainer = new HBox();
    private final StackPane centerStack = new StackPane(); // Allows layering lines behind text

    public TitleBar() {
        this.setLeft(leftContainer);
        this.setRight(rightContainer);
        this.setCenter(centerStack);

        leftContainer.setAlignment(Pos.CENTER_LEFT);
        rightContainer.setAlignment(Pos.CENTER_RIGHT);

    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void applyTheme(TitleBarTheme theme,
        Runnable onClose,
        Runnable onMinimize,
        Runnable onMaximize
    ) {
        // 1. Reset
        // Reset container defaults before applying theme-specific alignment
        rightContainer.setAlignment(Pos.CENTER_RIGHT);
        rightContainer.setPadding(new Insets(0, 5, 0, 0));
        getStyleClass().setAll(theme.styleClass());
        setPrefHeight(theme.height());
        leftContainer.getChildren().clear();
        rightContainer.getChildren().clear();
        centerStack.getChildren().clear();

        // TODO: Move this to CSS
        centerStack.setPadding(new Insets(0, 4, 0, 4));
        leftContainer.setPadding(new Insets(0, 0, 0, 3));
        rightContainer.setPadding(new Insets(0, 3, 0, 0));

        // 2. Setup Title
        titleLabel.getStyleClass().setAll("title-text");
        centerStack.getChildren().add(titleLabel);
        StackPane.setAlignment(titleLabel, theme.titleAlignment());

        // 3. Add background decorative elements (like your System 8 lines)
        if (theme.styleClass().contains("sys8")) {
            Pane linesCanvas = createStretchingLines();
            // linesCanvas.getStyleClass().add("stretching-lines-canvas"); // Match the CSS
            centerStack.getChildren().add(0, linesCanvas);

            // Inside DynamicTitleBar constructor or applyTheme
            linesCanvas.setMouseTransparent(true); // Ensure lines don't block clicks

            // Crucial: Bind the height of the lines to the title bar's own height property
            linesCanvas.maxHeightProperty().bind(this.prefHeightProperty());
            linesCanvas.minHeightProperty().bind(this.prefHeightProperty());
        }

        // 4. Build Buttons
        theme.leftButtons().forEach(type ->
            leftContainer.getChildren().add(createThemeButton(type, onClose, onMinimize, onMaximize)));

        theme.rightButtons().forEach(type ->
            rightContainer.getChildren().add(createThemeButton(type, onClose, onMinimize, onMaximize)));



            if (theme.styleClass().contains("theme-win7")) {
                // Buttons must touch the top edge
                rightContainer.setPadding(new Insets(0, 8, 0, 0));
                rightContainer.setAlignment(Pos.TOP_RIGHT);

                // Ensure the TitleBar itself doesn't have internal padding pushing the HBox down
                this.setPadding(Insets.EMPTY);

                // addAeroOrb();
            }

        attachStageListeners();
    }

    // Inside TitleBar.java
    public void addAeroOrb() {
        Region orb = new Region();
        orb.getStyleClass().add("aero-orb");

        // Position it slightly away from the edges
        HBox.setMargin(orb, new Insets(4, 0, 0, 8));

        leftContainer.getChildren().add(0, orb);
    }

    public Pane createStretchingLines() {
        Pane lineCanvas = new Pane();
        lineCanvas.getStyleClass().setAll("stretching-lines-canvas");

        // Ensure it grows to fill the available space in the StackPane
        lineCanvas.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(lineCanvas, Priority.ALWAYS);

        return lineCanvas;
    }


    public String getCssName(String name) {
        return name.toLowerCase().replace("_", "-");
    }


    private Button createThemeButton(TitleBarTheme.TitleButtonType type, Runnable onClose, Runnable onMin, Runnable onMax) {
        Button btn = new Button();

        String cssName = getCssName(type.name());
        btn.getStyleClass().setAll("base-button", cssName);
        btn.setPadding(Insets.EMPTY);
        btn.setAlignment(Pos.CENTER);
        btn.setPadding(Insets.EMPTY);

        Region icon = new Region();
        String iconClass = cssName.replace("-button", "-icon");
        icon.getStyleClass().addAll("base-icon", iconClass);

        icon.setMaxSize(10, 10);
        icon.setMinSize(10, 10);

        icon.setMinWidth(10);
        icon.setMaxWidth(10);
        icon.setMinHeight(10);
        icon.setMaxHeight(10);

        btn.setGraphic(icon);

        // If using Win7, don't force the Win10 46x30 size
        // Let the CSS pref-width/height handle it
        btn.setMinWidth(Button.USE_PREF_SIZE);
        btn.setMinHeight(Button.USE_PREF_SIZE);

        // btn.setMinWidth(46);
        // btn.setMinHeight(30);

        switch (type) {
            case CLOSE_BUTTON -> {
                if (onClose != null) {
                    btn.setOnAction(e -> onClose.run());
                }
            }
            case MINIMIZE_BUTTON -> {
                if (onMin != null) {
                    btn.setOnAction(e -> onMin.run());
                }
            }
            case MAXIMIZE_BUTTON -> {
                if (onMax != null) {
                    btn.setOnAction(e -> onMax.run());
                }
            }
            case MENU_BUTTON -> {
                // TODO
            }
        }
        return btn;
    }

    public void attachStageListeners() {
        Scene scene = getScene();
        if (scene == null) { return; }
        Window w = scene.getWindow();
        if (w == null) { return; }
        if (!(w instanceof Stage stage)) { return; }


        stage.maximizedProperty().addListener((obs, wasMaximized, isMaximized) -> {
            // Find the button we created earlier
            Button maxBtn = (Button) this.lookup(".maximize-button");
            if (maxBtn != null && maxBtn.getGraphic() instanceof Region icon) {
                if (isMaximized) {
                    icon.getStyleClass().remove("maximize-icon");
                    icon.getStyleClass().add("restore-icon");
                } else {
                    icon.getStyleClass().remove("restore-icon");
                    icon.getStyleClass().add("maximize-icon");
                }
            }
        });
    }
}