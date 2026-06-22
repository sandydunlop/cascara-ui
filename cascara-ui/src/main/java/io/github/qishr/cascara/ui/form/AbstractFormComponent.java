package io.github.qishr.cascara.ui.form;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class AbstractFormComponent extends VBox {
    protected final SimpleStringProperty query = new SimpleStringProperty(this, "query");
    protected FieldLabel description;
    protected FieldLabel title;

    protected InvalidationListener listener = i -> internalChange();

    protected AbstractFormComponent() {
        HBox.setHgrow(this, Priority.ALWAYS);
        setSpacing(4);
        query.addListener(listener);
    }

    public void setTitle(FieldLabel title) {
        if (this.title != null) {
            this.title.textProperty().removeListener(listener);
            this.title.queryProperty().bind(query);
        }
        this.title = title;
        if (title != null) {
            title.textProperty().addListener(listener);
            title.queryProperty().bind(query);
        }
        performLayout();
    }

    public void setDescription(FieldLabel description) {
        if (this.description != null) {
            this.description.textProperty().removeListener(listener);
            this.description.queryProperty().unbind();
        }
        this.description = description;
        if (description != null) {
            description.textProperty().addListener(listener);
            description.queryProperty().bind(query);
        }
        performLayout();
    }

    protected void internalChange() {
        updateSearchHighlight();
        onTextChanged();
    }

    protected abstract void onTextChanged();
    protected abstract void performLayout();

    public String getQuery() {
        return query.get();
    }

    protected void updateSearchHighlight() {
        if (title != null) {
            title.formatText();
        }
        if (description != null) {
            description.formatText();
        }
    }
}
