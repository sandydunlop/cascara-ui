package io.github.qishr.cascara.ui.api.render;

import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import javafx.scene.Node;
import javafx.scene.control.Labeled;

public interface ScalarRenderer extends Renderer {
    Node render(Labeled view, Object data, DataProvider dataProvider, FieldMetadata meta);
}
