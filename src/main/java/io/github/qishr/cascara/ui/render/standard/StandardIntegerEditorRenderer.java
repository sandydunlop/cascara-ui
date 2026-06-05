package io.github.qishr.cascara.ui.render.standard;

import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class StandardIntegerEditorRenderer extends AbstractScalarRenderer implements ScalarEditorRenderer {
    private boolean isUpdatingControl;

    @Override
    public String getContentType() { return null; }

    @Override
    public String getSchemaType() { return "integer"; }

    @Override
    public String getSchemaFormat() { return null; }

    @SuppressWarnings("unchecked")
    @Override
    public Node render(Labeled view, Observable data, DataProvider dataProvider, FieldMetadata meta) {
        TextField tf = new TextField();
        applyIntegerFilter(tf);

        if (data instanceof ObjectProperty obj) {
            tf.setText(extractString(obj, meta));
            tf.textProperty().addListener((o, oldV, newV) -> {
                if (isUpdatingControl) return;
                try {
                    Long value = Long.parseLong(newV);
                    obj.setValue(value);
                    if (meta.getOnChange() != null) meta.getOnChange().run();
                } catch (Exception e) { return; }
            });
            obj.addListener((obs,old,val) -> {
                isUpdatingControl = true;
                tf.setText(String.valueOf(val));
                isUpdatingControl = false;
            });
        }

        view.setText(null);
        view.setGraphic(tf);
        return tf;
    }

    private void applyIntegerFilter(TextField tf) {
        tf.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Integer allows digits and optional leading minus.
            String regex = "-?\\d*";
            if (newText.matches(regex)) {
                return change;
            }
            return null; // Reject the change
        }));
    }
}