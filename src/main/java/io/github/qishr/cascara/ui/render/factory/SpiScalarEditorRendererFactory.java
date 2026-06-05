package io.github.qishr.cascara.ui.render.factory;

import io.github.qishr.cascara.common.service.ServiceProviderLayer;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRendererFactory;

public class SpiScalarEditorRendererFactory extends AbstractSpiRendererFactory<ScalarEditorRenderer> implements ScalarEditorRendererFactory {

    public SpiScalarEditorRendererFactory(ServiceProviderLayer layer) {
        super(ScalarEditorRenderer.class, layer);
    }

    @Override
    public ScalarEditorRenderer getRendererForSchemaType(String schemaType) {
        Class<ScalarEditorRenderer> clazz = renderersBySchemaType.get(schemaType);
        if (clazz == null) {
            return null;
        }
        return ServiceProviderLayer.loadProvider(clazz);
    }

    @Override
    public ScalarEditorRenderer getRendererForSchemaType(String schemaType, String format) {
        String typeAndFormat = schemaType + "/" + format;
        Class<ScalarEditorRenderer> clazz = renderersBySchemaTypeAndFormat.get(typeAndFormat);
        if (clazz == null) {
            return null;
        }
        return ServiceProviderLayer.loadProvider(clazz);
    }

    @Override
    public ScalarEditorRenderer getRendererForContentType(String contentType) {
        Class<ScalarEditorRenderer> clazz = renderersByContentType.get(contentType);
        if (clazz != null) {
            return ServiceProviderLayer.loadProvider(clazz);
        }

        // TODO: This is a hack. Do it properly.
        if (contentType.startsWith("text/")) {
            for (String key : renderersByContentType.keySet()) {
                if (key.startsWith("text/")) {
                    clazz = renderersByContentType.get(key);
                    if (clazz != null) {
                        return ServiceProviderLayer.loadProvider(clazz);
                    }
                }
            }
        }

        return null;
    }
}
