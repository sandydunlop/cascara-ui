package io.github.qishr.cascara.ui.render;

import io.github.qishr.cascara.common.util.Properties;
import io.github.qishr.cascara.ui.api.render.Renderer;

public abstract class AbstractRenderer implements Renderer {
    public static final String RENDERER_CONTENT_TYPE = "contentType";
    public static final String RENDERER_SCHEMA_TYPE = "schemaType";
    public static final String RENDERER_SCHEMA_FORMAT = "schemaFormat";

    @Override
    public Properties getServiceProperties() {
        Properties capabilities = new Properties();
        if (getContentType() != null) {
            capabilities.set(RENDERER_CONTENT_TYPE, getContentType());
        }
        if (getSchemaType() != null) {
            capabilities.set(RENDERER_SCHEMA_TYPE, getSchemaType());
        }
        if (getSchemaFormat() != null) {
            capabilities.set(RENDERER_SCHEMA_FORMAT, getSchemaFormat());
        }
        return capabilities;
    }

}
