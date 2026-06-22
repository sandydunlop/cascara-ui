package io.github.qishr.cascara.ui.form;

import java.util.Map.Entry;

import io.github.qishr.cascara.common.diagnostic.GlobalReporter;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.diagnostic.code.GenericDiagnosticCode;
import io.github.qishr.cascara.common.service.ServiceProviderLayer;
import io.github.qishr.cascara.schema.Schema;
import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.ui.api.UiDiagnosticCode;
import io.github.qishr.cascara.ui.api.data.ObservableTableData;
import io.github.qishr.cascara.ui.data.ObservableObject;
import io.github.qishr.cascara.ui.data.UiDataException;
import io.github.qishr.cascara.ui.language.Localization;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.layout.Region;

public class ObjectFieldFactory extends AbstractFieldFactory {
    private static final Reporter REPORTER = GlobalReporter.forClass(ObjectFieldFactory.class);
    private ObservableTableData object = null;
    private SchemaNode objectSchema = null;

    public ObjectFieldFactory(ObservableObject object) {
        // this(object, null);
        super(null);
        this.object = object;
        this.objectSchema = object.getObjectSchema();
    }

    public ObjectFieldFactory(ObservableObject object, ServiceProviderLayer moduleLayer) {
        super(moduleLayer);
        this.object = object;
        this.objectSchema = object.getObjectSchema();
    }

    public ObjectFieldFactory(ObservableTableData object, Schema schema) {
        this(object, schema.getRoot(), null);
    }

    public ObjectFieldFactory(ObservableTableData object, SchemaNode schema) {
        this(object, schema, null);
    }

    public ObjectFieldFactory(ObservableTableData object, Schema schema, ServiceProviderLayer moduleLayer) {
        this(object, schema.getRoot(), moduleLayer);
    }

    public ObjectFieldFactory(ObservableTableData object, SchemaNode schema, ServiceProviderLayer moduleLayer) {
        super(moduleLayer);
        this.object = object;
        this.objectSchema = schema;
    }

    public void setObject(ObservableTableData object) {
        this.object = object;
        if (object instanceof ObservableObject obs) {
            this.objectSchema = obs.getObjectSchema();
        }
    }

    public Field createLabeledField(String fieldName) throws UiDataException {
        SchemaNode schema = getFieldSchema(fieldName);

        // TODO: Why not call super.createLabeledField?
        Field field = createField(fieldName);
        if (field == null) { return null; }

        String labelText = fieldName;
        String title = schema.getTitle();
        if (title != null && !title.isBlank()) {
            labelText = title;
        }

        FieldLabel label = new FieldLabel(labelText);
        label.setHeading(true);

        String titleKey = Localization.getTitleKey(schema);
        if (titleKey != null) {
            Localization.bind(label.textProperty(), titleKey);
        }

        field.setLabel(label);

        if (schema.getType() == SchemaType.ARRAY) {
            field.setLabelPosition(LabelPosition.ABOVE);
        }


        String description = schema.getDescription();
        String descriptionKey = Localization.getDescriptionKey(schema);
        if (description != null || descriptionKey != null) {
            FieldLabel descriptionLabel = new FieldLabel(descriptionKey, description);
            field.setDescription(descriptionLabel);
        }


        // // TODO: Find a better way of doing this, eg fieldMets.hasDisplayToggle()
        // boolean isLarge = fieldSchema.getType() == SchemaType.ARRAY;
        // for (ValidationRule rule : fieldSchema.getRules()) {
        //     if (rule instanceof MaxLengthRule mlr) {
        //         if (mlr.getMax() > 256) {
        //             isLarge = true;
        //             break;
        //         }
        //     }
        // }

        // if (isLarge) {
        //     label.getChildren().add(valueField);
        //     HBox.setHgrow(label, Priority.ALWAYS);
        // } else {
        //     Region spacer = new Region();
        //     HBox.setHgrow(spacer, Priority.ALWAYS);
        //     field.addContent(spacer, valueField);
        // }

        return field;
    }

    public Field createField(String fieldName) throws UiDataException {
        SchemaNode schema = getFieldSchema(fieldName);
        if (schema == null) {
            REPORTER.error(UiDiagnosticCode.SCHEMA_NOT_SPECIFIED);
            return null;
        }

        FieldMetadata meta = new FieldMetadata(fieldName, schema, rendererFactory);
        meta.setRenderers(rendererAllocator.allocate(meta));

        Observable data = object.getObservablesMap().get(fieldName);
        if (data == null) {
            throw new UiDataException(UiDiagnosticCode.PROPERTY_NOT_FOUND_IN_MAP, object.getClass().getSimpleName(), fieldName);
        }

        if (meta.isArrayField() && data instanceof ObservableList list) {
            if (!schema.isReadOnly()) {
                meta.setRemoveRowHandler((row) -> {
                    list.remove(row);
                });
            }
        }

        ViewAndControl viewAndControl = createControl(meta, data);

        if (viewAndControl == null) {
            throw new UiDataException(GenericDiagnosticCode.UNEXPECTED_NULL, "viewAndControl");
            // REPORTER.error(null, "Failed to create form field: " + fieldSchema.getOriginUri());
            // return null;
        }
        Field field = new Field(data, viewAndControl, meta);

        // Experimental...
        if (!(data instanceof ObservableList)) {
            if (viewAndControl.control() instanceof Control control) {
                control.setPrefHeight(Region.USE_COMPUTED_SIZE);
                control.setPrefWidth(Region.USE_COMPUTED_SIZE);
                control.setMaxHeight(300);
                control.setMaxWidth(Double.MAX_VALUE);
            }
            if (viewAndControl.view() instanceof Control control) {
                control.setPrefHeight(Region.USE_COMPUTED_SIZE);
                control.setPrefWidth(Region.USE_COMPUTED_SIZE);
                control.setMaxHeight(300);
                control.setMaxWidth(Double.MAX_VALUE);
            }
        }

        return field;
    }

    private SchemaNode getFieldSchema(String fieldName) {
        for (Entry<String, SchemaNode> entry : objectSchema.getProperties().entrySet()) {
            if (fieldName.equals(entry.getKey())) {
                if (entry.getValue() instanceof SchemaNode fieldSchema) {
                    return fieldSchema;
                }
            }
        }
        return null;
    }
}
