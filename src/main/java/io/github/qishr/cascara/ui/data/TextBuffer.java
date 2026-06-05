package io.github.qishr.cascara.ui.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TextBuffer {
    private final StringProperty content = new SimpleStringProperty("");

    public StringProperty textProperty() { return content; }
    public String getText() { return content.get(); }
    public void setText(String text) { content.set(text); }

    // Add helper methods for offset-to-line translation here
}
