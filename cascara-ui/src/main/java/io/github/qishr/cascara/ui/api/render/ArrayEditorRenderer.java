package io.github.qishr.cascara.ui.api.render;

import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Labeled;

public interface ArrayEditorRenderer extends Renderer {
    Node render(Labeled view, @SuppressWarnings("rawtypes") ObservableList list, DataProvider dataProvider, FieldMetadata meta);
}
