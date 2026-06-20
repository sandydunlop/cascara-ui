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

public class Renderers {
    private static final Reporter REPORTER = GlobalReporter.forClass(Renderers.class);

    private ScalarRenderer scalarRenderer;
    private ScalarEditorRenderer scalarEditorRenderer;
    private ArrayEditorRenderer arrayEditorRenderer;

    public Renderers(ScalarRenderer scalarRenderer, ScalarEditorRenderer scalarEditorRenderer, ArrayEditorRenderer arrayEditorRenderer) {
        this.scalarRenderer = scalarRenderer;
        this.scalarEditorRenderer = scalarEditorRenderer;
        this.arrayEditorRenderer = arrayEditorRenderer;
    }

    public Renderers(RendererFactory rendererFactory, FieldMetadata meta) {
        boolean isEnum = findEnumValues(meta.getSchema()) != null;
        if (rendererFactory != null) {
            if (meta.isArrayField()) {
                configureArrayRenderer(rendererFactory, meta);
            } else {
                if (meta.hasOptionProvider() || isEnum) {
                    scalarEditorRenderer = new OptionChooserRenderer();
                } else {
                    configureScalarRenderers(rendererFactory, meta);
                }
            }
        }
    }

    public ScalarRenderer getScalarRenderer() {
        return scalarRenderer;
    }

    public ScalarEditorRenderer getScalarEditorRenderer() {
        return scalarEditorRenderer;
    }

    public ArrayEditorRenderer getArrayEditorRenderer() {
        return arrayEditorRenderer;
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


    private void configureArrayRenderer(RendererFactory rendererFactory, FieldMetadata meta) {
        if (rendererFactory == null) return;

        String contentType = meta.getContentType();

        if (contentType != null) {
            arrayEditorRenderer = rendererFactory.createArrayEditorRendererForContentType(contentType);
        }

        // If we failed to find a specific renderer, fall back to the table renderer
        if (arrayEditorRenderer == null) {
            arrayEditorRenderer = rendererFactory.createArrayEditorRendererForContentType("cascara/table-row");
        }
    }

    private void configureScalarRenderers(RendererFactory factory, FieldMetadata meta) {
        String contentType = meta.getContentType();
        String format = meta.getFormat();
        String schemaType = meta.getSchemaType().asString();

        // TODO: `format` is "" when it should be null. That's weird.
        // Is it a SchemaGenerator or a parser problem?

        if (schemaType != null && !schemaType.isBlank() && format != null && !format.isBlank()) {
            String schemaTypeAndFormat = schemaType + "/" + format;
            ScalarEditorRenderer editorRenderer = factory.createScalarEditorRendererForSchemaType(schemaType, format);
            if (editorRenderer != null && scalarEditorRenderer == null) {
                scalarEditorRenderer = editorRenderer;
                logRenderer(meta.getName(), editorRenderer, "editor renderer by schemaTypeAndFormat " + schemaTypeAndFormat);
            }

            ScalarRenderer renderer = factory.createScalarRendererForSchemaType(schemaType, format);
            if (renderer != null && scalarEditorRenderer == null) {
                scalarRenderer = renderer;
                logRenderer(meta.getName(), renderer, "renderer by schemaTypeAndFormat " + schemaTypeAndFormat);
            }
        }

        // TODO: Should contentType not be checked first?
        if (contentType != null) {
            ScalarEditorRenderer editorRenderer = factory.createScalarEditorRendererForContentType(contentType);
            if (meta.allowEdit() && editorRenderer != null && scalarEditorRenderer == null) {
                scalarEditorRenderer = editorRenderer;
                logRenderer(meta.getName(), editorRenderer, "editor renderer by contentType " + contentType);
            }

            ScalarRenderer renderer = factory.createScalarRendererForContentType(contentType);
            if (renderer != null && scalarRenderer == null) {
                scalarRenderer = renderer;
                logRenderer(meta.getName(), renderer, "renderer by contentType " + contentType);
            }
        }

        // If we didn't find a renderer by media type, then try with schema type
        if (scalarEditorRenderer == null && factory != null) {
            ScalarEditorRenderer editorRenderer = factory.createScalarEditorRendererForSchemaType(schemaType, null);
            if (editorRenderer != null && scalarEditorRenderer == null) {
                scalarEditorRenderer = editorRenderer;
                logRenderer(meta.getName(), editorRenderer, "editor renderer by schemaType " + schemaType);
            }
        }
    }

    private void logRenderer(String fieldName, Renderer renderer, String findMethod) {
        REPORTER.debug("Property \"" + fieldName + "\": " + findMethod + ": " + renderer.getClass().getSimpleName());
    }
}

