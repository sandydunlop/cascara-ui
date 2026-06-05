package io.github.qishr.cascara.ui.form;

import io.github.qishr.cascara.ui.style.standard.TextStyle;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class FieldLabel extends VBox {

    private final SimpleStringProperty query = new SimpleStringProperty(this, "query");
    public SimpleStringProperty queryProperty() {return query;}
    public String getQuery() {return this.query.get();}
    public void setQuery(String text) {this.query.set(text);}

    private TextFlow highlightedTitle;
    private final String text;

    public FieldLabel(String fieldName) {
        String labelText = fieldName;
        this.text = labelText;

        highlightedTitle = createHighlightedText();
        query.addListener((obs,old,val) -> {
            highlightedTitle = createHighlightedText();
            getChildren().setAll(highlightedTitle);
        });

        setMinHeight(20);

        setSpacing(2);
        getChildren().add(highlightedTitle);
    }

    public String getText() {
        return text;
    }

    /// For larger label text
    public void setHeading(boolean isHeading) {
        if (isHeading) {
            // ui.getStyleClass().add("category-header");
            for (Node n : highlightedTitle.getChildren()) {
                if (n instanceof Text t) {
                    t.getStyleClass().add(TextStyle.HEADING);
                }
            }
        }
    }

    private TextFlow createHighlightedText() {
        String filter = getQuery();
        if (filter == null || filter.isEmpty() || !text.toLowerCase().contains(filter.toLowerCase())) {
            TextFlow tf = new TextFlow(createText(text, "label"));
            tf.getStyleClass().setAll("paragraph");
            return tf;
        }

        String lowerText = text.toLowerCase();
        String lowerFilter = filter.toLowerCase();
        TextFlow textFlow = new TextFlow();
        textFlow.getStyleClass().setAll("paragraph");

        int start = 0;
        int pos;
        while ((pos = lowerText.indexOf(lowerFilter, start)) != -1) {
            // Add text before the match
            if (pos > start) {
                Text textElement = createText(text.substring(start, pos), "label");
                textFlow.getChildren().add(textElement);
            }

            // Add the matched text with a highlight style
            Text highlight = createText(text.substring(pos, pos + filter.length()), "");
            highlight.setStyle("-fx-fill: #e51400; -fx-font-weight: bold; -fx-underline: true;");
            textFlow.getChildren().add(highlight);

            start = pos + filter.length();
        }

        // Add remaining text
        if (start < text.length()) {
            Text textElement = createText(text.substring(start), "label");
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
