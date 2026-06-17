package io.github.qishr.cascara.ui.render.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.github.qishr.cascara.common.service.ServiceProviderLayer;
import io.github.qishr.cascara.common.service.ServiceMetadata;
import io.github.qishr.cascara.ui.api.render.Renderer;
import io.github.qishr.cascara.ui.service.ServicePropertyName;

public abstract class AbstractSpiRendererFactory<T extends Renderer> {
    protected final Class<T> serviceType;
    protected final ServiceProviderLayer serviceProviderLayer;
    protected final Map<String,Class<T>> renderersByContentType = new HashMap<>();
    protected final Map<String,Class<T>> renderersBySchemaType = new HashMap<>();
    protected final Map<String,Class<T>> renderersBySchemaTypeAndFormat = new HashMap<>();

    protected AbstractSpiRendererFactory(Class<T> serviceType, ServiceProviderLayer layer) {
        this.serviceType = serviceType;
        this.serviceProviderLayer = layer == null ? ServiceProviderLayer.getRootLayer() : layer;
        discoverRenderers();
    }

    private void discoverRenderers() {
        if (serviceProviderLayer != null) {
            Collection<ServiceMetadata> providers  = serviceProviderLayer.findAllProviders(serviceType);
            for (ServiceMetadata provider : providers) {
                processServiceProvider(provider);
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void processServiceProvider(ServiceMetadata provider) {
        // If there is already a renderer for the content type or schema type,
        // don't add the newly discovered one. Since `findProviders` starts
        // with the providers registered closest to the caller, we don't want
        // to overwrite them.

        String contentType = provider.getProperty(ServicePropertyName.CONTENT_TYPE.asString());
        if (contentType != null  && !renderersByContentType.containsKey(contentType)) {
            renderersByContentType.put(contentType, (Class) provider.getType());
        }

        String schemaType = provider.getProperty(ServicePropertyName.SCHEMA_TYPE.asString());
        if (schemaType != null) {
            String format = provider.getProperty(ServicePropertyName.SCHEMA_FORMAT.asString());
            if (format == null) {
                if (!renderersBySchemaType.containsKey(schemaType)) {
                    renderersBySchemaType.put(schemaType, (Class) provider.getType());
                }
            } else if (format != null) {
                String typeAndFormat = schemaType + "/" + format;
                if (schemaType != null && !renderersBySchemaType.containsKey(typeAndFormat)) {
                    renderersBySchemaTypeAndFormat.put(typeAndFormat, (Class) provider.getType());
                }
            }
        }
    }
}
