package io.github.qishr.cascara.ui.control;

import io.github.qishr.cascara.ui.option.Option;
import io.github.qishr.cascara.ui.option.TagOption;
import io.github.qishr.cascara.ui.render.control.TagRenderer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Pill extends HBox {

    private Runnable removeHandler;

    public Pill(Option option) {
        // TagRenderer renderer = new TagRenderer();
        // renderer.render(this, option);

        Label label = new Label();
        // Label label = this;

        label.setText(option.getOptionText());

        // The 'X' button
        Label closeBtn = new Label("✕");
        closeBtn.setVisible(false); // Hidden by default

        getChildren().addAll(label, closeBtn);

        // HBox pillContent = new HBox(label, closeBtn);
        this.setAlignment(Pos.CENTER);

        // 1. Get colors using our shared utility
        String bgColor = (option instanceof TagOption tag) ? tag.getColor() : "#888888";
        String textColor = TagRenderer.getContrastColor(bgColor);

        // 2. Apply unified styling
        // We set the style on the HBox to keep the label and 'X' unified in the background
        this.setStyle(String.format(
            "-fx-background-color: %s; " +
            "-fx-background-radius: 12; " +
            "-fx-padding: 2 10 2 10; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 2, 0, 0, 1);",
            bgColor
        ));

        // 3. Style the internal text/icon with the contrast color
        label.setStyle("-fx-text-fill: " + textColor + "; -fx-font-weight: bold;");
        closeBtn.setStyle("-fx-text-fill: " + textColor + "; -fx-font-weight: bold; -fx-padding: 0 0 0 5;");

        // Hover logic
        this.setOnMouseEntered(e -> closeBtn.setVisible(true));
        this.setOnMouseExited(e -> closeBtn.setVisible(false));

        // Clicking the pill or the X removes it
        this.setOnMouseClicked(e -> {
            if (removeHandler != null) {
                removeHandler.run();
            }
            e.consume();
        });

    }

    public void setOnRemoveClicked(Runnable handler) {
        removeHandler = handler;
    }
}
