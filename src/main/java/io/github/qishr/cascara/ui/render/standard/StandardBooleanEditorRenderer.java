package io.github.qishr.cascara.ui.render.standard;

import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;

public class StandardBooleanEditorRenderer extends AbstractScalarRenderer implements ScalarEditorRenderer {
    @Override
    public String getContentType() { return null; }

    @Override
    public String getSchemaType() { return "boolean"; }

    @Override
    public String getSchemaFormat() { return null; }

    @SuppressWarnings("unchecked")
    @Override
    public Node render(Labeled view, Observable data, DataProvider dataProvider, FieldMetadata meta) {
        if (data instanceof ObjectProperty obj) {
            CheckBox checkBox = new CheckBox();
            if (obj.getValue() instanceof Boolean b) {
                checkBox.setSelected(b);
            }

            checkBox.selectedProperty().addListener((obs,old,val) -> {
                obj.set(val);
            });

            // TODO: Update from data

            view.setText(null);
            view.setGraphic(checkBox);
            return checkBox;
        }
        return null;
    }
}