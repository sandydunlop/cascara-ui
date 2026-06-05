package io.github.qishr.cascara.ui.render.factory;

import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.api.render.ScalarRendererFactory;
import io.github.qishr.cascara.ui.render.standard.StandardBooleanRenderer;
import io.github.qishr.cascara.ui.render.standard.StandardIntegerRenderer;
import io.github.qishr.cascara.ui.render.standard.StandardNumberRenderer;
import io.github.qishr.cascara.ui.render.standard.StandardStringRenderer;

public class StandardScalarRendererFactory implements ScalarRendererFactory {
    @Override
    public ScalarRenderer getRendererForSchemaType(String schemaType) {
        return switch (schemaType) {
            case "boolean" -> new StandardBooleanRenderer();
            case "string" -> new StandardStringRenderer();
            case "number" -> new StandardNumberRenderer();
            case "integer" -> new StandardIntegerRenderer();
            default -> null;
        };
    }

    @Override
    public ScalarRenderer getRendererForSchemaType(String schemaType, String format) {
        return null;
    }

    @Override
    public ScalarRenderer getRendererForContentType(String contentType) {
        return null;
    }
}
