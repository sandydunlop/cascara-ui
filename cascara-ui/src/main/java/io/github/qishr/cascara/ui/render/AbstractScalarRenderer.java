package io.github.qishr.cascara.ui.render;

import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import javafx.beans.property.ObjectProperty;

public abstract class AbstractScalarRenderer extends AbstractRenderer {

    protected AbstractScalarRenderer(String contentType, SchemaType schemaType, String format) {
        super(contentType, schemaType, format);
    }

    protected String extractString(ObjectProperty<?> prop) {
        return prop.getValue() == null ? "" : String.valueOf(prop.getValue());
    }

    protected String extractString(ObjectProperty<?> prop, FieldMetadata meta) {
        if (meta == null) {
            return extractString(prop);
        }
        SchemaNode schema = meta.getSchema();
        Object object = prop.getValue();
        if (object == null) return "";

        try {
            switch (schema.getType()) {
                case INTEGER:
                    return Long.toString((Long)object);
                case NUMBER:
                    return Double.toString((Long)object);
                case BOOLEAN:
                    return Boolean.toString((Boolean)object);
                default:
                    return String.valueOf(object);
            }
        } catch (ClassCastException e) {
            return String.valueOf(object);
        }
    }
}
