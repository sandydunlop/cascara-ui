package io.github.qishr.cascara.ui.data;

import io.github.qishr.cascara.schema.SchemaType;

public class PropertyMetadata {
    private SchemaType schemaType;
    private String mediaType;
    private boolean isDeclaredProperty;

    public PropertyMetadata(SchemaType schemaType, String mediaType, boolean isDeclaredProperty) {
        this.schemaType = schemaType;
        this.mediaType = mediaType;
        this.isDeclaredProperty = isDeclaredProperty;
    }

    public SchemaType getSchemaType() { return schemaType; }
    public String getMediaType() { return mediaType; }
    public boolean isDeclaredProperty() { return isDeclaredProperty; }
}
