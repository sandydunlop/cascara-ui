package io.github.qishr.cascara.ui.form;

import java.util.function.Consumer;

import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.schema.util.ValidationResult;
import io.github.qishr.cascara.common.data.TableData;
import io.github.qishr.cascara.common.diagnostic.code.GenericDiagnosticCode;
import io.github.qishr.cascara.ui.data.UiDataException;
import io.github.qishr.cascara.ui.style.custom.FormStyle;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class UnlabeledField extends Field {
    private VBox content;
    private ViewAndControl inputControl;
    private ObjectProperty<?> property;
    private FieldValidator onValidate = null;
    private Label errorLabel;

    public UnlabeledField(Observable observable, ViewAndControl inputControl, FieldMetadata metadata) {
        if (inputControl == null) {
            throw new UiDataException(GenericDiagnosticCode.UNEXPECTED_NULL, "inputControl");
        }
        if (observable instanceof ObjectProperty obj) {
            this.property = obj;
        }
        this.inputControl = inputControl;
        this.metadata = metadata;

        content = new VBox(2);
        content.getChildren().addAll(inputControl.view());

        HBox.setHgrow(content, Priority.ALWAYS);
        getChildren().add(content);

        if (property != null) {
            property.addListener((obs, oldVal, newVal) -> {
                applyValidationStyle();
            });
        }
    }

    /// For array fields
    /// @param list the array elements to watch for validation
    /// @param inputControl the input/editor control for this field
    /// @param metadata i
    public UnlabeledField(ObservableList<?> list, ViewAndControl inputControl, FieldMetadata metadata) {
        if (inputControl == null) {
            throw new UiDataException(GenericDiagnosticCode.UNEXPECTED_NULL, "inputControl");
        }
        this.inputControl = inputControl;
        this.metadata = metadata;

        content = new VBox(2);
        content.getChildren().addAll(inputControl.view());

        HBox.setHgrow(content, Priority.ALWAYS);
        getChildren().add(content);

        list.addListener(new ListChangeListener<Object>() {
            public void onChanged(Change<? extends Object> c) {
                applyValidationStyle();
            }
        });
    }

    public void setOnValidate(FieldValidator callback) {
        this.onValidate = callback;
        if (onValidate == null) {
            if (errorLabel != null) {
                getChildren().remove(errorLabel);
            }
        } else {
            if (errorLabel == null) {
                errorLabel = new Label();
                // cascara://organizer/CASC-000257D1 Use validation error colors from theme
                errorLabel.setStyle("-fx-text-fill: #f48771; -fx-font-size: 0.9em;");
                errorLabel.setWrapText(true);
                getChildren().add(errorLabel);
            }
        }
        applyValidationStyle();
    }

    private void applyValidationStyle() {
        if (onValidate == null) return;
        if (property == null) {
            return;
        }

        SchemaNode schema = metadata.getSchema();
        Object value = property.getValue();
        String path = metadata.getName();

        ValidationResult result = onValidate.performValidation(value, path, schema);
        boolean hasError = !result.isValid();

        if (hasError) {
            if (!inputControl.view().getStyleClass().contains(FormStyle.INPUT_ERROR)) {
                inputControl.view().getStyleClass().add(FormStyle.INPUT_ERROR);
            }
            errorLabel.setText(result.getMessages().get(0).text());
            errorLabel.setVisible(true);
        } else {
            inputControl.view().getStyleClass().remove(FormStyle.INPUT_ERROR);
            errorLabel.setVisible(false);
        }
    }

    @Override
    public Node getInputControl() {
        if (inputControl == null) return null;
        return inputControl.control();
    }

    @Override
    public boolean isArray() {
        return metadata.isArrayField();
    }

    @Override
    public FieldMetadata getMetadata() {
        return metadata;
    }

    @Override
    public void setAddRowHandler(Runnable addRow) {
        System.out.println("TODO");
    }

    @Override
    public void setRemoveRowHandler(Consumer<TableData> addRow) {
        System.out.println("TODO");
    }
}
