package io.github.qishr.cascara.ui.form;

import java.util.function.Consumer;

import io.github.qishr.cascara.common.data.TableData;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public abstract class Field extends VBox {
    protected FieldMetadata metadata;

    abstract public Node getInputControl();
    abstract public boolean isArray();
    abstract public FieldMetadata getMetadata();
    abstract public void setAddRowHandler(Runnable addRow);
    abstract public void setRemoveRowHandler(Consumer<TableData> addRow);

    public String getName() {
        return metadata.getName();
    }
}
