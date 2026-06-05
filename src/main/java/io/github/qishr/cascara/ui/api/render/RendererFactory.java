package io.github.qishr.cascara.ui.api.render;

public interface RendererFactory<R extends Renderer> {
    R getRendererForContentType(String contentType);
    R getRendererForSchemaType(String schemaType);
    R getRendererForSchemaType(String schemaType, String format);
}
