package io.github.qishr.cascara.ui.service;

public enum ServicePropertyName {
    CONTENT_TYPE("contentType"),
    NAME("name"),
    SCHEMA_TYPE("schemaType"),
    SCHEMA_FORMAT("schemaFormat");

    private final String string;

    ServicePropertyName(String string) {
        this.string = string;
    }

    public String asString() { return string; }
}
