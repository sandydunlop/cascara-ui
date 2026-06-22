package io.github.qishr.cascara.ui.form;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import io.github.qishr.cascara.common.data.TableData;
import io.github.qishr.cascara.common.diagnostic.LocalizableRuntimeException;
import io.github.qishr.cascara.common.diagnostic.code.GenericDiagnosticCode;
import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.schema.util.ValidationResult;
import io.github.qishr.cascara.ui.control.OptionChooser;
import io.github.qishr.cascara.ui.option.OptionProvider;
import io.github.qishr.cascara.ui.style.custom.FormStyle;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class Field extends AbstractFormComponent {

    private FieldMetadata metadata;
    private FieldValidator onValidate;
    private ObjectProperty<?> scalarData;
    private ObservableList<?> arrayData;

    private boolean expandControl;
    private LabelPosition labelPosition = LabelPosition.BESIDE;
    private int verticalSpacing = 3;
    private int horizontalSpacing = 8;

    private ViewAndControl inputControl;
    private Label errorLabel;

    @SuppressWarnings("unchecked")
    public Field(Observable observable, ViewAndControl inputControl, FieldMetadata metadata) {
        super();
        this.inputControl = inputControl;
        this.metadata = metadata;

        setPadding(new Insets(4));

        if (inputControl == null || inputControl.view() == null) {
            throw new LocalizableRuntimeException(GenericDiagnosticCode.UNEXPECTED_NULL, "inputControl");
        }

        if (observable instanceof ObservableList<?> list) {
            this.arrayData = list;
            // TODO: What was this for? Table contents?
            list.addListener(new ListChangeListener<Object>() {
                public void onChanged(Change<? extends Object> c) {
                    onTextChanged();
                }
            });
        } else if (observable instanceof ObjectProperty scalar) {
            this.scalarData = scalar;
            scalar.addListener((obs, oldVal, newVal) -> {
                onTextChanged();
            });
        } else {
            System.out.println("ERROR");
        }
        performLayout();
    }

    public Field setExpandControl(boolean v) {
        expandControl = v;
        performLayout();
        return this;
    }

    public Field setLabelPosition(LabelPosition v) {
        labelPosition = v;
        performLayout();
        return this;
    }

    public Field setHorizontalSpacing(int v) {
        horizontalSpacing = v;
        performLayout();
        return this;
    }

    public Field setVerticalSpacing(int v) {
        verticalSpacing = v;
        performLayout();
        return this;
    }

    public Field setOptionProvider(OptionProvider v) {
        if (inputControl != null && inputControl.control() instanceof OptionChooser chooser) {
            chooser.setOptionProvider(v);
        }

        // TODO Instead of setting things in multiple places, Field and FieldMetadata should use properties.
        metadata.setOptionProvider(v);

        return this;
    }

    public FieldLabel getLabel() {
        return title;
    }

    public void setLabel(FieldLabel label) {
        super.setTitle(label);
    }

    public SchemaNode getSchema() {
        return metadata == null ? null : metadata.getSchema();
    }

    public void setAddRowHandler(Runnable addRow) {
        if (metadata != null) {
            metadata.setAddRowHandler(addRow);
        }
    }

    public void setRemoveRowHandler(Consumer<TableData> addRow) {
        if (metadata != null) {
            metadata.setRemoveRowHandler(addRow);
        }
    }

    public StringProperty queryProperty() {
        return query;
    }

    public Node getInputControl() {
        return inputControl == null ? null : inputControl.control();
    }

    public FieldMetadata getMetadata() {
        return metadata;
    }

    public boolean isArray() {
        return metadata == null ? false : metadata.isArrayField();
    }

    public String getName() {
        return metadata == null ? null : metadata.getName();
    }

    /// If onValidate is set, errorLabel is created.
    /// If it's unset, errorLabel will be null.
    public void setOnValidate(FieldValidator callback) {
        this.onValidate = callback;
        configureValidation();
        applyValidationStyle();
    }

    //
    // Protected Methods
    //

    @Override
    protected void onTextChanged() {
        applyValidationStyle();

        // Content highlighting
        applyHighlighting();
    }

    @Override
    protected void performLayout() {
        setSpacing(verticalSpacing);

        if (inputControl.view() == null) {
            getChildren().setAll(new Label("ERROR"));
            return;
        }

        List<Node> col = new ArrayList<>();

        // This is statement is here because tables are bahving strangely.
        // Fix it later.
        if (metadata.isArrayField()) {
            if (title != null) {
                col.add(title);
            }
            if (description != null) {
                col.add(description);
            }
            if (inputControl.view() != null) {
                col.add(inputControl.view());
            }
        } else {
            List<Node> row = new ArrayList<>();

            // In this context, `title` is the Field label
            if (title != null) {
                if (labelPosition == LabelPosition.ABOVE) {
                    col.add(title);
                }
                if (labelPosition == LabelPosition.BESIDE) {
                    row.add(title);
                    if (!expandControl) {
                        // Add a spacer so that if the Field is set to expand, the control doesn't have to.
                        Region spacer = new Region();
                        HBox.setHgrow(spacer, Priority.ALWAYS);
                        row.add(spacer);
                    }
                }
            }

            if (description == null) {
                row.add(inputControl.view());
            } else {
                if (getInputControl() instanceof CheckBox checkBox) {
                    row.add(inputControl.control());
                    row.add(description);
                    description.setOnMouseClicked(click -> {
                        checkBox.setSelected(!checkBox.isSelected());
                    });
                } else {
                    col.add(description);
                    row.add(inputControl.view());
                }
            }

            if (expandControl) {
                HBox.setHgrow(inputControl.view(), Priority.ALWAYS);
            }

            if (!row.isEmpty()) {
                HBox rowContainer = new HBox(horizontalSpacing);
                rowContainer.getChildren().setAll(row);
                rowContainer.setMinHeight(10);
                col.add(rowContainer);
            }

            if (errorLabel != null) {
                col.add(errorLabel);
            }
        }

        if (col.isEmpty()) {
            getChildren().clear();
        } else {
            getChildren().setAll(col);
        }
    }

    //
    // Private Methods
    //

    private void configureValidation() {
        if (onValidate == null) {
            if (errorLabel != null) {
                errorLabel = null;
            }
        } else {
            if (errorLabel == null) {
                errorLabel = new Label();
                // cascara://organizer/CASC-000257D1 Use validation error colors from theme
                errorLabel.setStyle("-fx-text-fill: #f48771; -fx-font-size: 0.9em;");
                errorLabel.setWrapText(true);
            }
        }
    }

    private void applyValidationStyle() {
        if (onValidate == null) return;
        if (scalarData == null) return;

        SchemaNode schema = metadata.getSchema();
        Object value = scalarData.getValue();
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

    private void applyHighlighting() {
        boolean isMatch = false;
        if (scalarData != null && scalarData.get() != null) {
            String text = scalarData.get().toString();
            if (text != null) {
                String filter = getQuery();
                if (filter != null && !filter.isEmpty() && text.toLowerCase().contains(filter.toLowerCase())) {
                    isMatch = true;
                }
            }
        }

        // TODO: Highlighting of search matches in input controls
        if (getInputControl() instanceof Control) {
            if (isMatch) {
                // node.getStyleClass().add(TextInputStyle.SEARCH_MATCH);
                // Border border = Border.stroke(Paint.valueOf("aqua"));
                // node.setBorder(border);
            } else {
                // node.getStyleClass().remove(TextInputStyle.SEARCH_MATCH);
                // node.setBorder(Border.EMPTY);
            }
        }
    }
}
