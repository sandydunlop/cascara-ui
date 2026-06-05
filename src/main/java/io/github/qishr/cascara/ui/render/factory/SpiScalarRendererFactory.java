package io.github.qishr.cascara.ui.render.factory;

import io.github.qishr.cascara.common.service.ServiceProviderLayer;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.api.render.ScalarRendererFactory;

public class SpiScalarRendererFactory extends AbstractSpiRendererFactory<ScalarRenderer> implements ScalarRendererFactory {

    /// Creates a scalar renderer factory with access to a specific
    /// `ServiceProviderLayer` and all public layers
    /// @param layer The module layer of the caller. This enables
    /// the renderer factory to use renderers in the caller's layer.
    public SpiScalarRendererFactory(ServiceProviderLayer layer) {
        super(ScalarRenderer.class, layer);
    }

    @Override
    public ScalarRenderer getRendererForSchemaType(String contentType) {
        return null;
    }

    @Override
    public ScalarRenderer getRendererForSchemaType(String schemaType, String format) {
        return null;
    }

    @Override
    public ScalarRenderer getRendererForContentType(String type) {
        Class<ScalarRenderer> clazz = renderersByContentType.get(type);
        if (clazz == null) {
            return null;
        }
        return ServiceProviderLayer.loadProvider(clazz);
    }
}
