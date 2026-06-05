package io.github.qishr.cascara.ui.render.standard;

import java.net.URI;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import io.github.qishr.cascara.schema.SchemaKeyword;
import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.schema.rule.EnumRule;
import io.github.qishr.cascara.schema.rule.ValidationRule;
import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.control.FilePicker;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;

public class StandardStringEditorRenderer extends AbstractScalarRenderer implements ScalarEditorRenderer {
    private boolean isUpdatingControl;

    @Override
    public String getContentType() { return null; }

    @Override
    public String getSchemaType() { return "string"; }

    @Override
    public String getSchemaFormat() { return null; }

    @Override
    public Node render(Labeled view, Observable data, DataProvider dataProvider, FieldMetadata meta) {
        SchemaNode schema = meta.getSchema();
        if (data instanceof ObjectProperty obj) {
            String fieldName = obj.getName();
            List<String> enumValues = findEnumValues(schema, fieldName);
            Node node = null;
            if (enumValues != null && !enumValues.isEmpty()) {
                node = createEnumField(obj, enumValues, meta);
            } else {
                node = createTextField(obj, meta);
            }
            if (node != null) {
                view.setGraphic(node);
            }
            view.setText(null);
            return node;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Node createTextField(@SuppressWarnings("rawtypes") ObjectProperty property, FieldMetadata meta) {
        TextField tf = new TextField();

        tf.setText(extractString(property));
        tf.textProperty().addListener((o, oldV, newV) -> {
            if (isUpdatingControl) return;
            property.setValue(newV);
            if (meta.getOnChange() != null) meta.getOnChange().run();
        });
        property.addListener((obs,old,val) -> {
            isUpdatingControl = true;
            tf.setText(String.valueOf(val));
            isUpdatingControl = false;
        });
        return tf;
    }

    //
    // Helpers
    //

    @SuppressWarnings("unchecked")
    protected Node createEnumField(@SuppressWarnings("rawtypes") ObjectProperty property, List<String> enumValues, FieldMetadata meta) {
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(enumValues));
        Object currentVal = property.getValue();
        if (currentVal == null && !enumValues.isEmpty()) {
            currentVal = enumValues.get(0);
        }
        comboBox.setValue(String.valueOf(currentVal));
        comboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdatingControl) return;
            property.setValue(newVal);
            if (meta.getOnChange() != null) meta.getOnChange().run();
        });
        property.addListener((obs,old,val) -> {
            isUpdatingControl = true;
            comboBox.setValue(String.valueOf(val));
            isUpdatingControl = false;
        });
        comboBox.setMaxWidth(Double.MAX_VALUE);
        return comboBox;
    }

    private List<String> findEnumValues(SchemaNode schema, String fieldName) {
        if (schema == null) return null;
        if ("type".equals(fieldName) && SchemaKeyword.exists(fieldName)) {
            SchemaNode meta = schema.getMetaSchema();
            if (meta != null && isMetaSchema(meta.getOriginUri())) {
                return SchemaKeyword.TYPE.suggestions();
            }
        }
        for (ValidationRule rule : schema.getRules()) {
            if (rule instanceof EnumRule enumRule) {
                return enumRule.getAllowedValues();
            }
        }
        return null;
    }

    private boolean isMetaSchema(URI uri) {
        if (uri == null) return false;
        String uriString = uri.toString();
        return uriString.contains("json-schema.org") ||
                uriString.contains("cema-meta") ||
                uriString.contains("schema-service/schema");
    }
}