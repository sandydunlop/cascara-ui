package io.github.qishr.cascara.ui.render.control;

import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.option.TagOption;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;

public class TagRenderer extends AbstractScalarRenderer implements ScalarRenderer {

    @Override
    public String getContentType() { return "cascara/tag"; }

    @Override
    public String getSchemaType() { return null; }

    @Override
    public String getSchemaFormat() { return null; }

    @Override
    public Node render(Labeled cell, Object data, DataProvider dataProvider, FieldMetadata meta) {
        if (data instanceof TagOption tag) {
            String bgColor = tag.getColor();
            String textColor = getContrastColor(bgColor);

            // Shadow for "White" text to prevent washing out
            String textShadow = textColor.equalsIgnoreCase("white")
                ? "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 2, 0, 0, 1);"
                : "";

            Label pillText = new Label(tag.getOptionText());

            // Use !important-style logic by ensuring we set the text fill
            // and opacity explicitly on the label itself.
            pillText.setStyle(String.format(
                "-fx-text-fill: %s !important;" +      // Force the color here
                // "-fx-font-weight: bold; " +
                "-fx-opacity: 1.0; " +        // Ensure no "ghosting"
                "%s",
                textColor, textShadow
            ));

            // Create the container for the background
            javafx.scene.layout.StackPane container = new javafx.scene.layout.StackPane(pillText);
            container.setStyle(String.format(
                "-fx-background-color: %s; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 2 8 2 8;",
                bgColor
            ));

            cell.setGraphic(container);
            cell.setText(null);
            cell.setContentDisplay(javafx.scene.control.ContentDisplay.GRAPHIC_ONLY);
            return pillText;
        }
        return null;
    }

    public static String getContrastColor(String colorStr) {
        try {
            if (colorStr != null && colorStr.startsWith("#")) {
                int r, g, b;
                if (colorStr.length() == 7) {
                    r = Integer.parseInt(colorStr.substring(1, 3), 16);
                    g = Integer.parseInt(colorStr.substring(3, 5), 16);
                    b = Integer.parseInt(colorStr.substring(5, 7), 16);
                } else {
                    r = Integer.parseInt(colorStr.substring(1, 2), 16) * 17;
                    g = Integer.parseInt(colorStr.substring(2, 3), 16) * 17;
                    b = Integer.parseInt(colorStr.substring(3, 4), 16) * 17;
                }

                // Perceived brightness formula
                double luminance = (0.299 * r + 0.587 * g + 0.114 * b) / 255;

                // 0.5 is the standard threshold, but 0.6 is often safer for "Tag" aesthetics
                return (luminance > 0.6) ? "black" : "white";
            }
        } catch (Exception ignored) {}
        return "white"; // Default to white
    }
}
