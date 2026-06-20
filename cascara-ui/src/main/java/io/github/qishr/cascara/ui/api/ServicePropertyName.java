package io.github.qishr.cascara.ui.api;

public enum ServicePropertyName {
    CONTENT_TYPE("contentType"),
    NAME("name"),
    SCHEMA_TYPE("schemaType"),
    SCHEMA_FORMAT("schemaFormat"),
    PLATFORM("platform"),
    URI_SCHEME("uriScheme");

    private final String string;

    ServicePropertyName(String string) {
        this.string = string;
    }

    public String asString() { return string; }
}
