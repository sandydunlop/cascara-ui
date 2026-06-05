package io.github.qishr.cascara.ui.render.control;

import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.color.ColorException;
import io.github.qishr.cascara.ui.color.ColorUtil;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Labeled;
import javafx.scene.paint.Color;

public class ColorChooserRenderer extends AbstractScalarRenderer implements ScalarEditorRenderer {
    @Override
    public String getContentType() { return "cascara/color"; }

    @Override
    public String getSchemaType() { return "string"; }

    @Override
    public String getSchemaFormat() { return "color"; }

    @SuppressWarnings("unchecked")
    @Override
    public Node render(Labeled view, Observable data, DataProvider dataProvider, FieldMetadata meta) {
        if (data instanceof ObjectProperty prop) {
            Color c;
            try {
                c = ColorUtil.toColor(extractString(prop));
            } catch (ColorException e) {
                c = Color.RED;
            }

            ColorPicker cp = new ColorPicker(c);

            cp.valueProperty().addListener((obs,old,val) -> {
                String hex = ColorUtil.toRGBHexCode(val);
                prop.setValue(hex);
                if (meta.getOnChange() != null) { meta.getOnChange().run(); }
            });

            view.setText(null);
            view.setGraphic(cp);
            return cp;
        }
        return null;
    }
}