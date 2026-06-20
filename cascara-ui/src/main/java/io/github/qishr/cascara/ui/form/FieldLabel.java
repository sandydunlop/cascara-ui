package io.github.qishr.cascara.ui.form;

import io.github.qishr.cascara.ui.style.standard.TextStyle;
import io.github.qishr.cascara.ui.theme.ColorID;
import io.github.qishr.cascara.ui.theme.ThemeEngine;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class FieldLabel extends VBox {

    private final SimpleStringProperty text = new SimpleStringProperty(this, "text");
    private final SimpleStringProperty query = new SimpleStringProperty(this, "query");

    private TextFlow highlightedTitle;

    public FieldLabel(String text) {
        // String labelText = text;
        this.text.set(text);

        // cascara://organizer/CASC-0004CE77

        // TODO: Make this automatically update if either:
        // 1. textProperty() changes
        // 2. queryProperty() changes

        highlightedTitle = createHighlightedText();

        this.query.addListener((obs,old,val) -> {
            highlightedTitle = createHighlightedText();
            getChildren().setAll(highlightedTitle);
        });
        this.text.addListener((obs,old,val) -> {
            highlightedTitle = createHighlightedText();
            getChildren().setAll(highlightedTitle);
        });

        setMinHeight(20);

        setSpacing(2);
        getChildren().add(highlightedTitle);
    }

    public SimpleStringProperty queryProperty() { return query; }
    public StringProperty textProperty() { return text; }
    public String getQuery() {return this.query.get();}
    public void setQuery(String text) {this.query.set(text);}
    public String getText() { return text.get(); }

    /// For larger label text
    public void setHeading(boolean isHeading) {
        if (isHeading) {
            for (Node n : highlightedTitle.getChildren()) {
                if (n instanceof Text t) {
                    t.getStyleClass().add(TextStyle.HEADING);
                }
            }
        }
    }

    private TextFlow createHighlightedText() {
        String filter = getQuery();
        if (filter == null || filter.isEmpty() || !text.get().toLowerCase().contains(filter.toLowerCase())) {
            TextFlow tf = new TextFlow(createText(text.get(), "label"));
            tf.getStyleClass().setAll("paragraph");
            return tf;
        }

        String lowerText = text.get().toLowerCase();
        String lowerFilter = filter.toLowerCase();
        TextFlow textFlow = new TextFlow();
        textFlow.getStyleClass().setAll("paragraph");

        int start = 0;
        int pos;
        while ((pos = lowerText.indexOf(lowerFilter, start)) != -1) {
            // Add text before the match
            if (pos > start) {
                Text textElement = createText(text.get().substring(start, pos), "label");
                textFlow.getChildren().add(textElement);
            }

            // Add the matched text with a highlight style
            Text highlight = createText(text.get().substring(pos, pos + filter.length()), "");




            // cascara://organizer/CASC-00028C57
            // TODO: create something in TextStyle for this
            // TODO: Fix search highlight colors in Cascara Theme
            // to be user-overridable

            // String highlightStyle = String.format("-fx-fill: #e51400; -fx-font-weight: bold; -fx-underline: true;");
            // String highlightStyle = String.format(
            //     "-fx-background-color: %s; -fx-fill: %s; -fx-font-weight: bold;",
            //     ThemeEngine.getUiColor(ColorID.EDITOR_MATCH_BACKGROUND),
            //     ThemeEngine.getUiColor(ColorID.EDITOR_MATCH_FOREGROUND)
            // );
            String highlightStyle = String.format(
                "-fx-fill: %s; -fx-font-weight: bold; -fx-underline: true;",
                ThemeEngine.getUiColor(ColorID.EDITOR_MATCH_FOREGROUND)
            );
            //EDITOR_MATCH_FOREGROUND
            highlight.setStyle(highlightStyle);




            textFlow.getChildren().add(highlight);

            start = pos + filter.length();
        }

        // Add remaining text
        if (start < text.get().length()) {
            Text textElement = createText(text.get().substring(start), "label");
            textFlow.getChildren().add(textElement);
        }

        return textFlow;
    }

    private Text createText(String string, String cssClass) {
        Text textElement = new Text(string);
        textElement.setStyle("-fx-font-weight: bold;");
        textElement.getStyleClass().setAll("label", "text");
        return textElement;
    }
}
