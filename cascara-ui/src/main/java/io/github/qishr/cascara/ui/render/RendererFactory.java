package io.github.qishr.cascara.ui.render;

import io.github.qishr.cascara.common.service.AbstractServiceProviderFactory;
import io.github.qishr.cascara.common.service.CapabilityQueries;
import io.github.qishr.cascara.common.service.ServiceException;
import io.github.qishr.cascara.common.service.ServiceProviderLayer;
import io.github.qishr.cascara.ui.api.ServicePropertyName;
import io.github.qishr.cascara.ui.api.render.ArrayEditorRenderer;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;

public class RendererFactory extends AbstractServiceProviderFactory {
    public RendererFactory() {
        super();
    }

    public RendererFactory(ServiceProviderLayer layer) {
        super(layer);
    }

    public ArrayEditorRenderer createArrayEditorRendererForContentType(String contentType) throws ServiceException {
        return createServiceProvider(
            ArrayEditorRenderer.class,
            CapabilityQueries.hasExactValue(ServicePropertyName.CONTENT_TYPE.asString(), contentType)
        );
    }

    public ArrayEditorRenderer createArrayEditorRendererForSchemaType(String schemaType, String format) throws ServiceException {
        return createServiceProvider(
            ArrayEditorRenderer.class,
            CapabilityQueries.allOf(
                CapabilityQueries.hasExactValue(ServicePropertyName.SCHEMA_TYPE.asString(), schemaType),
                CapabilityQueries.hasExactValue(ServicePropertyName.SCHEMA_FORMAT.asString(), format)
            )
        );
    }

    public ScalarRenderer createScalarRendererForContentType(String contentType) throws ServiceException {
        return createServiceProvider(
            ScalarRenderer.class,
            CapabilityQueries.hasExactValue(ServicePropertyName.CONTENT_TYPE.asString(), contentType)
        );
    }

    public ScalarRenderer createScalarRendererForSchemaType(String schemaType, String format) throws ServiceException {
        return createServiceProvider(
            ScalarRenderer.class,
            CapabilityQueries.allOf(
                CapabilityQueries.hasExactValue(ServicePropertyName.SCHEMA_TYPE.asString(), schemaType),
                CapabilityQueries.hasExactValue(ServicePropertyName.SCHEMA_FORMAT.asString(), format)
            )
        );
    }

    public ScalarEditorRenderer createScalarEditorRendererForContentType(String contentType) throws ServiceException {
        return createServiceProvider(
            ScalarEditorRenderer.class,
            CapabilityQueries.hasExactValue(ServicePropertyName.CONTENT_TYPE.asString(), contentType)
        );
    }

    public ScalarEditorRenderer createScalarEditorRendererForSchemaType(String schemaType, String format) throws ServiceException {
        return createServiceProvider(
            ScalarEditorRenderer.class,
            CapabilityQueries.allOf(
                CapabilityQueries.hasExactValue(ServicePropertyName.SCHEMA_TYPE.asString(), schemaType),
                CapabilityQueries.hasExactValue(ServicePropertyName.SCHEMA_FORMAT.asString(), format)
            )
        );
    }
}