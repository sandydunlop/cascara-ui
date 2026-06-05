package io.github.qishr.cascara.ui.render.control;

import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.OverrunStyle;

public class SingleLineCellRenderer extends AbstractScalarRenderer implements ScalarRenderer {
    @Override
    public String getContentType() { return null; }

    @Override
    public String getSchemaType() { return "string"; }

    @Override
    public String getSchemaFormat() { return null; }

    @Override
    public Node render(Labeled view, Object data, DataProvider dataProvider, FieldMetadata meta) {
        if (data instanceof String rawText) {
            String processedText = rawText.replace("\n", " ").replace("\r", " ");
            view.setText(processedText);
            view.setTextOverrun(OverrunStyle.ELLIPSIS);
            view.setGraphic(null);
            view.setStyle("-fx-wrap-text: false;");
        }
        if (data instanceof Long number) {
            String processedText = number.toString();
            view.setText(processedText);
            view.setTextOverrun(OverrunStyle.ELLIPSIS);
            view.setGraphic(null);
            view.setStyle("-fx-wrap-text: false;");
        }
        return null;
    }
}