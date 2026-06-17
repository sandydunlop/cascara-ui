package io.github.qishr.cascara.ui.option;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.common.util.Properties;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.service.ServicePropertyName;

public abstract class AbstractOptionProvider implements OptionProvider {
    protected final List<Runnable> listeners = new ArrayList<>();

    private final String name;
    private final String contentType;
    private final String schemaType;
    private final String format;

    private Properties properties;

    /// @param name The name used to reference this provider from a schema
    /// @param schemaType
    /// @param format
    /// @param contentEncoding
    protected AbstractOptionProvider(String name, String contentType, String schemaType, String format) {
        this.name = name;
        this.contentType = contentType;
        this.schemaType = schemaType;
        this.format = format;
    }

    @Override
    public Properties getServiceProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.set(ServicePropertyName.NAME.asString(), name);
            if (contentType != null) properties.set(ServicePropertyName.CONTENT_TYPE.asString(), contentType);
            if (schemaType != null) properties.set(ServicePropertyName.SCHEMA_TYPE.asString(), schemaType);
            if (format != null) properties.set(ServicePropertyName.SCHEMA_FORMAT.asString(), format);
        }
        return properties;
    }

    @Override
    public void initialize() {
        // To be overridden by providers that need initialization
    }

    @Override public void addListener(Runnable listener) { listeners.add(listener); }
    @Override public void removeListener(Runnable listener) { listeners.remove(listener); }
    public ScalarRenderer getRenderer() { return null; }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContentType() { return contentType; }

    @Override
    public String getSchemaType() { return schemaType; }

    @Override
    public String getSchemaFormat() { return format; }


}
