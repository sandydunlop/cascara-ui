package io.github.qishr.cascara.ui.control;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class EditableLabel extends HBox {
    Label label = new Label();
    TextField textField = new TextField();

    private OnChangeHandler onChange = null;
    public interface OnChangeHandler { void onChange(String value); }
    public void setOnChange(OnChangeHandler onChange) { this.onChange = onChange; }

    public StringProperty textProperty() { return label.textProperty(); }

    public void onChange(String value) {
        if (onChange != null) {
            onChange.onChange(value);
        }
    }

    public EditableLabel(String text) {
        this();
        setText(text);
    }

    public EditableLabel() {
        super();
        label.setOnMouseClicked(mouse -> beginEditing());
        label.styleProperty().addListener((obs, old, val) -> {
            setLabelAppearance();
        });
        setLabelAppearance();
        textField.setPadding(new Insets(9.5));
        textField.setOnKeyTyped(key -> {
            if (key.getCharacter().equals("\r") || key.getCharacter().equals("\t")) {
                finishEditing();
            }
        });
        textField.focusedProperty().addListener((obs,old,val) -> {
            if (!val) {
                finishEditing();
            }
        });
        getChildren().add(label);
        setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(label, Priority.ALWAYS);
        HBox.setHgrow(textField, Priority.ALWAYS);
    }

    private void setLabelAppearance() {
        label.setCursor(Cursor.TEXT);
        label.setPadding(new Insets(9.5));
        label.setAlignment(Pos.CENTER_LEFT);
        label.setMouseTransparent(false);
        label.setPickOnBounds(true);
        label.setBackground(Background.fill(Color.RED));
    }

    private void beginEditing() {
        textField.setText(label.getText());
        getChildren().clear();
        getChildren().add(textField);
        textField.requestFocus();
    }

    private void finishEditing() {
        label.setText(textField.getText());
        getChildren().clear();
        getChildren().add(label);
        onChange(textField.getText());
    }

    public void setText(String text) {
        label.setText(text);
        textField.setText(text);
    }

    public String getText() {
        return textField.getText();
    }
}
