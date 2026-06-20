package io.github.qishr.cascara.ui.render;

import io.github.qishr.cascara.ui.api.render.ArrayEditorRenderer;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;

public record Renderers(ScalarRenderer scalarRenderer, ScalarEditorRenderer scalarEditorRenderer, ArrayEditorRenderer arrayEditorRenderer) {}
