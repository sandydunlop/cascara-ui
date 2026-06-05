package io.github.qishr.cascara.ui.render.standard;

import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;

public class StandardBooleanRenderer extends AbstractScalarRenderer implements ScalarRenderer {
    @Override
    public String getContentType() { return null; }

    @Override
    public String getSchemaType() { return "boolean"; }

    @Override
    public String getSchemaFormat() { return null; }

    @Override
    public Node render(Labeled view, Object data, DataProvider dataProvider, FieldMetadata meta) {
        CheckBox checkBox = null;
        if (data instanceof Boolean value) {
            checkBox = new CheckBox();
            checkBox.setSelected(value);
            checkBox.setDisable(true);
            view.setGraphic(checkBox);
        } else if (data instanceof ObjectProperty<?> property) {
            checkBox = new CheckBox();
            checkBox.setSelected(Boolean.parseBoolean(extractString(property, meta)));
            checkBox.setDisable(true);
            view.setGraphic(checkBox);
            property.addListener((obs,old,val) -> {
                view.setText(extractString(property, meta));
            });
        } else {
            view.setGraphic(null);
        }
        view.setText(null);
        return checkBox;
    }
}