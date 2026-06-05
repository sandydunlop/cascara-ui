package io.github.qishr.cascara.ui.api.render;

import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.control.Labeled;

public interface ScalarEditorRenderer extends Renderer {
    Node render(Labeled view, Observable data, DataProvider dataProvider, FieldMetadata meta);
}
