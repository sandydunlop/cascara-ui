package io.github.qishr.cascara.ui.render.control;

import io.github.qishr.cascara.common.diagnostic.Diagnostic.Level;
import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.control.SvgIcon;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;
import io.github.qishr.cascara.ui.style.custom.DiagnosticIconStyle;

import javafx.scene.Node;
import javafx.scene.control.Labeled;

public class DiagnosticRenderer extends AbstractScalarRenderer implements ScalarRenderer  {
    public DiagnosticRenderer() {
        super("cascara/diagnostic-level", null, null);
    }

    @Override
    public Node render(Labeled view, Object data, DataProvider dataProvider, FieldMetadata meta) {
        if (data instanceof Level level) {
            SvgIcon icon = new SvgIcon();
            icon.setSize(16);
            icon.setSource(DiagnosticIconStyle.svgFor(level));
            icon.getStyleClass().add(DiagnosticIconStyle.classFor(level));
            view.setGraphic(icon);
            view.setText(null);
        }  else {
            view.setGraphic(null);
            view.setText(null);
        }
        return null;
    }
}
