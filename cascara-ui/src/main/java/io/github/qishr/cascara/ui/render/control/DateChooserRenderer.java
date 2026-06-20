package io.github.qishr.cascara.ui.render.control;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.language.Localization;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Labeled;

public class DateChooserRenderer extends AbstractScalarRenderer implements ScalarEditorRenderer {
    public DateChooserRenderer() {
        super(null, SchemaType.STRING, "date");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Node render(Labeled view, Observable data, DataProvider dataProvider, FieldMetadata meta) {
        if (data instanceof ObjectProperty prop) {

            DatePicker control = new DatePicker();

            Localization.bindLocale(control);

            try {
                control.setValue(LocalDate.parse(extractString(prop)));
            } catch (DateTimeParseException e) {
                // Ignore
            }

            control.valueProperty().addListener((obs,old,localDate) -> {
                String textDate = localDate.toString();
                prop.set(textDate);
                if (meta.getOnChange() != null) meta.getOnChange().run();
            });

            // TODO: Watch `text` for udpates

            view.setText(null);
            view.setGraphic(control);
            return control;
        }
        return null;
    }
}