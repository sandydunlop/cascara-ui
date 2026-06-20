package io.github.qishr.cascara.ui.render.standard;

import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;

public class StandardStringEditorRenderer extends AbstractScalarRenderer implements ScalarEditorRenderer {
    private boolean isUpdatingControl;

    public StandardStringEditorRenderer() {
        super(null, SchemaType.STRING, null);
    }

    @Override
    public Node render(Labeled view, Observable data, DataProvider dataProvider, FieldMetadata meta) {
        if (data instanceof ObjectProperty obj) {
            Node node = createTextField(obj, meta);
            if (node != null) {
                view.setGraphic(node);
            }
            view.setText(null);
            return node;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Node createTextField(@SuppressWarnings("rawtypes") ObjectProperty property, FieldMetadata meta) {
        TextField tf = new TextField();

        tf.setText(extractString(property));
        tf.textProperty().addListener((o, oldV, newV) -> {
            if (isUpdatingControl) return;
            property.setValue(newV);
            if (meta.getOnChange() != null) meta.getOnChange().run();
        });
        property.addListener((obs,old,val) -> {
            isUpdatingControl = true;
            tf.setText(String.valueOf(val));
            isUpdatingControl = false;
        });
        return tf;
    }
}