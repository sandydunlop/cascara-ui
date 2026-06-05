package io.github.qishr.cascara.ui.render.factory;

import io.github.qishr.cascara.common.service.ServiceProviderLayer;
import io.github.qishr.cascara.ui.api.render.ArrayEditorRenderer;
import io.github.qishr.cascara.ui.api.render.ArrayEditorRendererFactory;

public class SpiArrayEditorRendererFactory extends AbstractSpiRendererFactory<ArrayEditorRenderer> implements ArrayEditorRendererFactory{

    public SpiArrayEditorRendererFactory(ServiceProviderLayer layer) {
        super(ArrayEditorRenderer.class, layer);
    }

    @Override
    public ArrayEditorRenderer getRendererForSchemaType(String schemaType) {
        return null;
    }

    @Override
    public ArrayEditorRenderer getRendererForSchemaType(String schemaType, String format) {
        return null;
    }

    @Override
    public ArrayEditorRenderer getRendererForContentType(String type) {
        Class<ArrayEditorRenderer> clazz = renderersByContentType.get(type);
        if (clazz == null) {
            return null;
        }
        return ServiceProviderLayer.loadProvider(clazz);
    }
}
