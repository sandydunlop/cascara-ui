package io.github.qishr.cascara.ui.render.control;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Labeled;

public class TimeRenderer extends AbstractScalarRenderer implements ScalarRenderer {
    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("HH:mm:ss.SSS").withZone(ZoneId.systemDefault());

    @Override
    public String getContentType() { return null; }

    @Override
    public String getSchemaType() { return "string"; }

    @Override
    public String getSchemaFormat() { return "time"; }

    @Override
    public Node render(Labeled view, Object data, DataProvider dataProvider, FieldMetadata meta) {
        if (data instanceof Long value) {
            view.setText(FORMATTER.format(Instant.ofEpochMilli(value.longValue())));
        } else if (data instanceof ObjectProperty property) {
            Object object = property.get();
            if (object instanceof Long value) {
                view.setText(FORMATTER.format(Instant.ofEpochMilli(value.longValue())));
            } else {
                Long value = Long.parseLong(extractString(property));
                view.setText(FORMATTER.format(Instant.ofEpochMilli(value.longValue())));
            }
            view.setText(extractString(property));
        } else {
            view.setText(null);
        }
        view.setGraphic(null);
        view.getStyleClass().add("log-time-text");
        return null;
    }
}
