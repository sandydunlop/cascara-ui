package io.github.qishr.cascara.ui.api.render;

import io.github.qishr.cascara.common.service.ServiceProvider;

public interface Renderer extends ServiceProvider {
    String getSchemaType();
    String getSchemaFormat();
    String getContentType();
}
