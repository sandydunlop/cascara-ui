package io.github.qishr.cascara.ui.render;

import java.util.List;

import io.github.qishr.cascara.common.diagnostic.GlobalReporter;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.ui.api.render.ArrayEditorRenderer;
import io.github.qishr.cascara.ui.api.render.ArrayEditorRendererFactory;
import io.github.qishr.cascara.ui.api.render.Renderer;
import io.github.qishr.cascara.ui.api.render.RendererFactory;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRendererFactory;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.api.render.ScalarRendererFactory;
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

    public Renderers(List<RendererFactory<?>> rendererFactories, FieldMetadata meta) {
        if (rendererFactories != null) {
            if (meta.isArrayField()) {
                configureArrayRenderer(rendererFactories, meta);
            } else {
                if (meta.hasOptionProvider()) {
                    scalarEditorRenderer = new OptionChooserRenderer();
                } else {
                    configureScalarRenderers(rendererFactories, meta);
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

    private void configureArrayRenderer(List<RendererFactory<?>> rendererFactories, FieldMetadata meta) {
        if (rendererFactories == null) return;

        String contentType = meta.getContentType();

        if (contentType != null) {
            // Set up renderers for mediaType.
            for (RendererFactory<? extends Renderer> item : rendererFactories) {
                if (item instanceof ArrayEditorRendererFactory factory) {
                    ArrayEditorRenderer renderer = factory.getRendererForContentType(contentType);
                    if (renderer != null) {
                        arrayEditorRenderer = renderer;
                        logRenderer(meta.getName(), renderer, "array editor by contentType " + contentType);
                        break;
                    }
                }
            }
        }

        // If we failed to find a specific renderer, fall back to the table renderer
        if (arrayEditorRenderer == null) {
            for (RendererFactory<? extends Renderer> item : rendererFactories) {
                if (item instanceof ArrayEditorRendererFactory factory) {
                    ArrayEditorRenderer renderer = factory.getRendererForContentType("cascara/table-row");
                    if (renderer != null) {
                        arrayEditorRenderer = renderer;
                        logRenderer(meta.getName(), renderer, "array editor renderer by contentType " + "cascara/table-row");
                        break;
                    }
                }
            }
        }
    }

    private void configureScalarRenderers(List<RendererFactory<?>> rendererFactories, FieldMetadata meta) {
        String contentType = meta.getContentType();
        String format = meta.getFormat();
        String schemaType = meta.getSchemaType().asString();

        // TODO: `format` is "" when it should be null. That's weird.
        // Is it a SchemaGenerator or a parser problem?

        if (schemaType != null && !schemaType.isBlank() && format != null && !format.isBlank()) {
            String schemaTypeAndFormat = schemaType + "/" + format;
            for (RendererFactory<? extends Renderer> item : rendererFactories) {
                if (item instanceof ScalarEditorRendererFactory factory) {
                    ScalarEditorRenderer renderer = factory.getRendererForSchemaType(schemaType, format);
                    if (renderer != null && scalarEditorRenderer == null) {
                        scalarEditorRenderer = renderer;
                        logRenderer(meta.getName(), renderer, "editor renderer by schemaTypeAndFormat " + schemaTypeAndFormat);
                    }
                }
                if (item instanceof ScalarRendererFactory factory) {
                    ScalarRenderer renderer = factory.getRendererForSchemaType(schemaType, format);
                    if (renderer != null && scalarRenderer == null) {
                        scalarRenderer = renderer;
                        logRenderer(meta.getName(), renderer, "renderer by schemaTypeAndFormat " + schemaTypeAndFormat);
                    }
                }
            }
        }

        if (contentType != null) {
            // Set up renderers for contentType.
            for (RendererFactory<? extends Renderer> item : rendererFactories) {
                if (item instanceof ScalarEditorRendererFactory factory) {
                    ScalarEditorRenderer renderer = factory.getRendererForContentType(contentType);
                    if (renderer != null && scalarEditorRenderer == null) {
                        scalarEditorRenderer = renderer;
                        logRenderer(meta.getName(), renderer, "editor renderer by contentType " + contentType);
                    }
                }
                if (item instanceof ScalarRendererFactory factory) {
                    ScalarRenderer renderer = factory.getRendererForContentType(contentType);
                    if (renderer != null && scalarRenderer == null) {
                        scalarRenderer = renderer;
                        logRenderer(meta.getName(), renderer, "renderer by contentType " + contentType);
                    }
                }
            }
        }

        // If we didn't find a renderer by media type, then try with schema type
        if (scalarEditorRenderer == null && rendererFactories != null) {
            for (RendererFactory<? extends Renderer> item : rendererFactories) {
                if (item instanceof ScalarEditorRendererFactory factory) {
                    ScalarEditorRenderer renderer = factory.getRendererForSchemaType(schemaType);
                    if (renderer != null && scalarEditorRenderer == null) {
                        scalarEditorRenderer = renderer;
                        logRenderer(meta.getName(), renderer, "editor renderer by schemaType " + schemaType);
                    }
                }
            }
        }
    }

    private void logRenderer(String fieldName, Renderer renderer, String findMethod) {
        REPORTER.debug("Property \"" + fieldName + "\": " + findMethod + ": " + renderer.getClass().getSimpleName());
    }
}

