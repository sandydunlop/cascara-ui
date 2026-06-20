package io.github.qishr.cascara.ui.demo;

import io.github.qishr.cascara.schema.annotation.SchemaDefinition;
import io.github.qishr.cascara.schema.annotation.SchemaProperty;
import io.github.qishr.cascara.schema.constraint.StringConstraint;
import io.github.qishr.cascara.ui.data.ObservableObject;
import javafx.beans.property.ObjectProperty;

@SchemaDefinition
public class SampleData extends ObservableObject {

    @SchemaProperty(enumKey = "sample-enum")
    @StringConstraint(options = {"ONE", "TWO", "THREE"})
    public ObjectProperty<SampleEnum> sampleEnum;

    @SchemaProperty
    public ObjectProperty<String> sampleText;

    public SampleData() {
        sampleText.set("Text");
    }

    public static enum SampleEnum {
        ONE,
        TWO,
        THREE,
        FOUR
    }
}
