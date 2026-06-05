package io.github.qishr.cascara.ui.data;

import io.github.qishr.cascara.common.util.Property;
import io.github.qishr.cascara.schema.annotation.SchemaDefinition;
import io.github.qishr.cascara.schema.annotation.SchemaProperty;

import javafx.beans.property.ObjectProperty;

@SchemaDefinition
public class ObservableProperty extends ObservableObject {

    @SchemaProperty
    ObjectProperty<String> name;

    @SchemaProperty
    ObjectProperty<String> value;

    public ObservableProperty(Property property) {
        set("name", property.getName());
        set("value", property.getValue());
    }
}
