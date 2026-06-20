package io.github.qishr.cascara.ui.form;

import java.util.function.Consumer;

import io.github.qishr.cascara.common.data.TableData;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LabeledField extends Field {
    private Label arrow = new Label("▼ ");
    private FieldLabel label = null;
    private HBox collapsibleArea = null;
    private HBox headerArea = new HBox();
    private UnlabeledField innerField;

    public LabeledField(FieldLabel label) {
        this(label, null, null);
    }

    public LabeledField(FieldLabel label, String description, FieldMetadata metadata) {
        this.label = label;
        this.metadata = metadata;
        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        headerArea.setAlignment(Pos.CENTER_LEFT);
        headerArea.getChildren().add(label);
        getChildren().add(headerArea);
    }

    public void addContent(Node... nodes) {
        headerArea.getChildren().addAll(nodes);
        for (Node node : nodes) {
            if (node instanceof UnlabeledField field) {
                innerField = field;
            }
        }
    }

    public HBox getHeader() {
        return headerArea;
    }

    public FieldLabel getLabel() {
        return label;
    }

    public Node getCollapsibleContent() {
        return collapsibleArea;
    }

    public void addCollapsibleContent(Node node) {
        if (collapsibleArea == null) {
            collapsibleArea = new HBox();
            collapsibleArea.setPadding(new Insets(10, 10, 10, 10));
            getChildren().add(collapsibleArea);
            initializeArrow();
        }
        collapsibleArea.getChildren().add(node);
        if (node instanceof UnlabeledField field) {
            innerField = field;
        }
    }

    private void initializeArrow() {
        // Toggle icon
        arrow.setStyle("-fx-font-family: 'monospace'; -fx-cursor: hand;");
        headerArea.setCursor(Cursor.HAND);
        headerArea.getChildren().add(0, arrow);

        // Toggle logic
        headerArea.setOnMouseClicked(e -> {
            boolean isVisible = collapsibleArea.isVisible();
            collapsibleArea.setVisible(!isVisible);
            collapsibleArea.setManaged(!isVisible);
            arrow.setText(!isVisible ? "▼ " : "▶ ");
        });
    }

    @Override
    public Node getInputControl() {
        return innerField == null ? null : innerField.getInputControl();
    }

    public void setInnerField(UnlabeledField inner) { innerField = inner; }

    @Override
    public boolean isArray() {
        return metadata == null ? false : metadata.isArrayField();
    }

    @Override
    public void setAddRowHandler(Runnable addRow) {
        if (metadata != null) {
            metadata.setAddRowHandler(addRow);
        }
    }

    @Override
    public void setRemoveRowHandler(Consumer<TableData> addRow) {
        if (metadata != null) {
            metadata.setRemoveRowHandler(addRow);
        }
    }

    @Override
    public FieldMetadata getMetadata() {
        return metadata;
    }
}
