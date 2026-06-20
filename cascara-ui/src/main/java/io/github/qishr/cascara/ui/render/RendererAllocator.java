package io.github.qishr.cascara.ui.render;

import java.util.List;

import io.github.qishr.cascara.common.diagnostic.GlobalReporter;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.schema.rule.EnumRule;
import io.github.qishr.cascara.schema.rule.ValidationRule;
import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.ui.api.render.ArrayEditorRenderer;
import io.github.qishr.cascara.ui.api.render.Renderer;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.control.OptionChooserRenderer;

public class RendererAllocator {
    private static final Reporter REPORTER = GlobalReporter.forClass(RendererAllocator.class);

    private RendererFactory factory;

    public RendererAllocator(RendererFactory rendererFactory) {
        this.factory = rendererFactory;
    }

    public Renderers allocate(FieldMetadata field) {
        ScalarRenderer scalarRenderer = null;
        ScalarEditorRenderer scalarEditorRenderer = null;
        ArrayEditorRenderer arrayEditorRenderer = null;

        boolean isEnum = findEnumValues(field.getSchema()) != null;
        if (factory != null) {
            if (field.isArrayField()) {
                arrayEditorRenderer = createArrayRenderer(field);
            } else {
                if (field.hasOptionProvider() || isEnum) {
                    scalarEditorRenderer = new OptionChooserRenderer();
                } else {
                    scalarRenderer = createScalarRenderer(field);
                    if (field.allowEdit()) {
                        scalarEditorRenderer = createScalarEditorRenderer(field);
                    }
                }
            }
        }

        // REPORTER.debug(
        //     "Field " + field.getName() + " was allocated " +
        //     scalarRenderer + ", " + scalarEditorRenderer + ", " + arrayEditorRenderer
        // );

        return new Renderers(scalarRenderer, scalarEditorRenderer, arrayEditorRenderer);
    }

    // TODO: Should we be doing this in the schema?
    private List<String> findEnumValues(SchemaNode schema) {
        if (schema == null) return null;

        // TODO: this...

        // // TODO: This seems wrong.
        // // Should it not be: SchemaKeyword.TYPE.asString().equals(fieldName.get())
        // if ("type".equals(fieldName.get()) && SchemaKeyword.exists(fieldName.get())) {
        //     SchemaNode meta = fieldSchema.getMetaSchema();
        //     if (meta != null && isMetaSchema(meta.getOriginUri())) {
        //         return SchemaKeyword.TYPE.suggestions();
        //     }
        // }

        for (ValidationRule rule : schema.getRules()) {
            if (rule instanceof EnumRule enumRule) {
                return enumRule.getAllowedValues();
            }
        }
        return null;
    }

    private ScalarRenderer createScalarRenderer(FieldMetadata meta) {
        String contentType = meta.getContentType();
        String format = meta.getFormat();
        String schemaType = meta.getSchemaType().asString();

        // TODO: `format` is "" when it should be null. That's weird.
        // Is it a SchemaGenerator or a parser problem?

        // 1. Find renderer using schema type and format
        if (schemaType != null && !schemaType.isBlank() && format != null && !format.isBlank()) {
            ScalarRenderer renderer = factory.createScalarRendererForSchemaType(schemaType, format);
            if (renderer != null) {
                logRenderer(meta.getName(), renderer, ScalarRenderer.class, "schemaTypeAndFormat", schemaType + "/" + format);
                return renderer;
            }
        }

        // 2. Find renderer using content type
        if (contentType != null) {
            ScalarRenderer renderer = factory.createScalarRendererForContentType(contentType);
            if (renderer != null) {
                logRenderer(meta.getName(), renderer, ScalarRenderer.class, "contentType", contentType);
                return renderer;
            }
        }

        // 3. Find renderer using schema type with no format
        ScalarRenderer renderer = factory.createScalarRendererForSchemaType(schemaType, null);
        if (renderer != null) {
            logRenderer(meta.getName(), renderer, ScalarRenderer.class, "schemaType", schemaType);
            return renderer;
        }

        return null;
    }

    private ScalarEditorRenderer createScalarEditorRenderer(FieldMetadata meta) {
        String contentType = meta.getContentType();
        String format = meta.getFormat();
        String schemaType = meta.getSchemaType().asString();

        // 1. Find renderer using schema type and format
        if (schemaType != null && !schemaType.isBlank() && format != null && !format.isBlank()) {
            ScalarEditorRenderer renderer = factory.createScalarEditorRendererForSchemaType(schemaType, format);
            if (renderer != null) {
                logRenderer(meta.getName(), renderer, ScalarEditorRenderer.class, "schemaTypeAndFormat", schemaType + "/" + format);
                return renderer;
            }
        }

        // 2. Find renderer using content type
        if (contentType != null) {
            ScalarEditorRenderer renderer = factory.createScalarEditorRendererForContentType(contentType);
            if (renderer != null) {
                logRenderer(meta.getName(), renderer, ScalarEditorRenderer.class, "contentType", contentType);
                return renderer;
            }

            // 2.5. If we're looking at a text field, fina a renderer for "text/*"
            renderer = factory.createScalarEditorRendererForContentType("text/*");
            if (renderer != null) {
                logRenderer(meta.getName(), renderer, ScalarEditorRenderer.class, "contentType", "text/*");
                return renderer;
            }
        }

        // 3. Find renderer using schema type with no format
        ScalarEditorRenderer renderer = factory.createScalarEditorRendererForSchemaType(schemaType, null);
        if (renderer != null) {
            logRenderer(meta.getName(), renderer, ScalarEditorRenderer.class, "schemaType", schemaType);
            return renderer;
        }

        return null;
    }

    private ArrayEditorRenderer createArrayRenderer(FieldMetadata meta) {
        if (factory == null) return null;

        ArrayEditorRenderer renderer = null;

        String contentType = meta.getContentType();

        if (contentType != null) {
            renderer = factory.createArrayEditorRendererForContentType(contentType);
        }

        // If we failed to find a specific renderer, fall back to the table renderer
        if (renderer == null) {
            contentType = "cascara/table-row";
            renderer = factory.createArrayEditorRendererForContentType(contentType);
        }

        if (renderer != null) {
            logRenderer(meta.getName(), renderer, ArrayEditorRenderer.class, "contentType", contentType);
        }

        return renderer;
    }

    private void logRenderer(String fieldName, Renderer renderer, Class<?> type, String findMethod, String value) {
        REPORTER.debug("Property \"" + fieldName + "\": " + type.getSimpleName() + " found by " + findMethod + " " + value + ": " + renderer.getClass().getSimpleName());
    }
}

