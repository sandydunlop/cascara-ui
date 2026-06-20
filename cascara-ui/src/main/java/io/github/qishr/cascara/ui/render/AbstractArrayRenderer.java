package io.github.qishr.cascara.ui.render;

import io.github.qishr.cascara.schema.SchemaType;

public abstract class AbstractArrayRenderer extends AbstractRenderer {
    protected AbstractArrayRenderer(String contentType, SchemaType schemaType, String format) {
        super(contentType, schemaType, format);
    }
}
