package io.github.qishr.cascara.ui.demo;

import io.github.qishr.cascara.schema.annotation.ContentMediaType;
import io.github.qishr.cascara.schema.annotation.SchemaDefinition;
import io.github.qishr.cascara.schema.annotation.SchemaProperty;
import io.github.qishr.cascara.schema.constraint.StringConstraint;
import io.github.qishr.cascara.ui.data.ObservableObject;
import javafx.beans.property.ObjectProperty;

@SchemaDefinition
public class SampleData extends ObservableObject {

    @SchemaProperty(titleKey = "title.sample-enum", descriptionKey = "text.sample-description", enumKey = "sample-enum")
    @StringConstraint(options = {"ONE", "TWO", "THREE"})
    public ObjectProperty<SampleEnum> sampleEnum;

    @SchemaProperty(titleKey = "title.sample-text", descriptionKey = "text.sample-description")
    public ObjectProperty<String> sampleText;

    @SchemaProperty
    @ContentMediaType("text/markdown")
    @StringConstraint(maxLength = 131072)
    public ObjectProperty<String> sampleMarkdown;

    public SampleData() {
        sampleText.set("Text");
        sampleMarkdown.set("# Markdown");
    }

    public static enum SampleEnum {
        ONE,
        TWO,
        THREE,
        FOUR
    }
}
