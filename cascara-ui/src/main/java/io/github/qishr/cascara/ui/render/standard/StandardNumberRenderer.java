package io.github.qishr.cascara.ui.render.standard;

import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Labeled;

public class StandardNumberRenderer extends AbstractScalarRenderer implements ScalarRenderer {
    public StandardNumberRenderer() {
        super(null, SchemaType.NUMBER, null);
    }

    @Override
    public String getContentType() { return null; }

    @Override
    public String getSchemaType() { return SchemaType.NUMBER.asString(); }

    @Override
    public String getSchemaFormat() { return null; }

    @Override
    public Node render(Labeled view, Object data, DataProvider dataProvider, FieldMetadata meta) {
        if (data instanceof Double v) {
            view.setText(Double.toString(v));
        } else if (data instanceof ObjectProperty<?> property) {
            view.setText(extractString(property, meta));
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