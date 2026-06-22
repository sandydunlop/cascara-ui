package io.github.qishr.cascara.ui.form;

import io.github.qishr.cascara.ui.language.Localization;
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

    boolean isHeading;
    boolean isSearchMatch;

    public FieldLabel(String text) {
        this(null, text);
    }

    public FieldLabel(String translationKey, String text) {
        this.query.addListener((obs,old,val) -> {
            formatText();
        });
        this.text.addListener((obs,old,val) -> {
            formatText();
        });

        if (translationKey != null) {
            Localization.bind(this.text, translationKey).withDefault(text);
        } else {
            this.text.set(text);
        }

        formatText();

        // setMinHeight(8);
        // setSpacing(2);
    }

    public SimpleStringProperty queryProperty() { return query; }
    public StringProperty textProperty() { return text; }
    public String getQuery() {return this.query.get();}
    public void setQuery(String text) {this.query.set(text);}
    public String getText() { return text.get(); }
    public void setText(String text) {this.text.set(text);}

    /// For larger label text
    public void setHeading(boolean isHeading) {
        this.isHeading = isHeading;
        formatText();
    }

    public void formatText() {
        TextFlow textFlow;
        String filter = getQuery();

        if (filter == null || filter.isEmpty() || !text.get().toLowerCase().contains(filter.toLowerCase())) {
            textFlow = new TextFlow(createText(text.get(), "label"));
            textFlow.getStyleClass().setAll("paragraph");
            isSearchMatch = false;
        } else {

            String lowerText = text.get().toLowerCase();
            String lowerFilter = filter.toLowerCase();
            textFlow = new TextFlow();
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
                //     "-fx-background-color: %s; -fx-fill: %s; -fx-font-weight: bold; -fx-underline: true;",
                //     ThemeEngine.getUiColor(ColorID.EDITOR_MATCH_BACKGROUND),
                //     ThemeEngine.getUiColor(ColorID.EDITOR_MATCH_FOREGROUND)
                // );

                String highlightStyle = String.format(
                    "-fx-fill: %s; -fx-font-weight: bold; -fx-underline: true;",
                    ThemeEngine.getUiColor(ColorID.EDITOR_MATCH_FOREGROUND)
                );

                highlight.setStyle(highlightStyle);
                textFlow.getChildren().add(highlight);
                start = pos + filter.length();
            }

            // Add remaining text
            if (start < text.get().length()) {
                Text textElement = createText(text.get().substring(start), "label");
                textFlow.getChildren().add(textElement);
            }
            isSearchMatch = true;
        }

        if (isHeading) {
            for (Node n : textFlow.getChildren()) {
                if (n instanceof Text t) {
                    t.getStyleClass().add(TextStyle.HEADING);
                }
            }
        }

        VBox box = new VBox(textFlow);
        box.setMinHeight(VBox.USE_PREF_SIZE);
        textFlow.setMinHeight(TextFlow.USE_PREF_SIZE);

        getChildren().setAll(box);
    }

    private Text createText(String string, String cssClass) {
        Text textElement = new Text(string);
        if (isHeading) {
            textElement.setStyle("-fx-font-weight: bold;");
        }
        textElement.getStyleClass().setAll("label", "text");
        return textElement;
    }
}
