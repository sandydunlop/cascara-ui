package io.github.qishr.cascara.ui.render.standard;

import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Labeled;

public class StandardIntegerRenderer extends AbstractScalarRenderer implements ScalarRenderer {
    public StandardIntegerRenderer() {
        super(null, SchemaType.INTEGER, null);
    }

    @Override
    public Node render(Labeled view, Object data, DataProvider dataProvider, FieldMetadata meta) {
        if (data instanceof Integer value) {
            view.setText(Integer.toString(value));
        } else if (data instanceof ObjectProperty<?> property) {
            view.setText(extractString(property));
            property.addListener((obs,old,val) -> {
                view.setText(extractString(property, meta));
            });
        } else {
            view.setText(data.toString());
        }
        view.setGraphic(null);
        return null;
    }
}