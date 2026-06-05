package io.github.qishr.cascara.ui.render.factory;

import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRendererFactory;
import io.github.qishr.cascara.ui.render.standard.StandardBooleanEditorRenderer;
import io.github.qishr.cascara.ui.render.standard.StandardIntegerEditorRenderer;
import io.github.qishr.cascara.ui.render.standard.StandardNumberEditorRenderer;
import io.github.qishr.cascara.ui.render.standard.StandardStringEditorRenderer;

public class StandardScalarEditorRendererFactory implements ScalarEditorRendererFactory {
    @Override
    public ScalarEditorRenderer getRendererForSchemaType(String schemaType) {
        return switch (schemaType) {
            case "boolean" -> new StandardBooleanEditorRenderer();
            case "string" -> new StandardStringEditorRenderer();
            case "number" -> new StandardNumberEditorRenderer();
            case "integer" -> new StandardIntegerEditorRenderer();
            default -> null;
        };
    }

    @Override
    public ScalarEditorRenderer getRendererForSchemaType(String schemaType, String format) {
        return null;
    }

    @Override
    public ScalarEditorRenderer getRendererForContentType(String contentType) {
        return null;
    }
}
