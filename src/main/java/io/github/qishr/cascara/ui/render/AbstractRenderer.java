package io.github.qishr.cascara.ui.render;

import io.github.qishr.cascara.common.util.Properties;
import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.ui.api.render.Renderer;
import io.github.qishr.cascara.ui.service.ServicePropertyName;

public abstract class AbstractRenderer implements Renderer {
    private final String contentType;
    private final String schemaType;
    private final String format;

    private Properties properties;

    /// @param contentType
    /// @param schemaType
    /// @param format
    protected AbstractRenderer(String contentType, SchemaType schemaType, String format) {
        this.contentType = contentType;
        this.schemaType = schemaType == null ? null : schemaType.asString();
        this.format = format;
    }

    @Override
    public Properties getServiceProperties() {
        if (properties == null) {
            properties = new Properties();
            if (contentType != null) properties.set(ServicePropertyName.CONTENT_TYPE.asString(), contentType);
            if (schemaType != null) properties.set(ServicePropertyName.SCHEMA_TYPE.asString(), schemaType);
            if (format != null) properties.set(ServicePropertyName.SCHEMA_FORMAT.asString(), format);
        }
        return properties;
    }

    @Override
    public String getContentType() { return contentType; }

    @Override
    public String getSchemaType() { return schemaType; }

    @Override
    public String getSchemaFormat() { return format; }
}
