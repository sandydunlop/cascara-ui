package io.github.qishr.cascara.ui.option;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.qishr.cascara.schema.rule.EnumRule;
import io.github.qishr.cascara.schema.rule.ValidationRule;
import io.github.qishr.cascara.schema.structure.SchemaNode;
import javafx.beans.property.Property;

public class EnumOptionProvider extends AbstractOptionProvider {
    public static final String NAME = "enum";
    // private List<String> enumValues;
    private Option activeOption;
    private List<Option> options = new ArrayList<>();

    public EnumOptionProvider(SchemaNode schema, String initialStringValue) {
        super(NAME, null, null, null);
        // this.enumValues = findEnumValues(schema);
        // if (enumValues == null) {
        //     throw new UiSchemaException(UiDiagnosticCode.EMPTY_ENUM);
        // }
        activeOption = populateEnumOptions(options, schema, initialStringValue);
    }

    public EnumOptionProvider(SchemaNode schema, Enum<?> initialValue) {
        this(schema, initialValue == null ? null : initialValue.toString());
        // super(NAME, null, null, null);
        // this.enumValues = findEnumValues(schema);
        // if (enumValues == null) {
        //     throw new UiSchemaException(UiDiagnosticCode.EMPTY_ENUM);
        // }
        // String initialValueString = initialValue == null ? null : initialValue.toString();

        // activeOption = populateEnumOptions(options, schema, initialValueString);

        // Localization.localeProperty().addListener((obs, oldSet, newSet) -> {
        //     listeners.forEach(Runnable::run);
        // });
    }

    // TODO: setSchema and setInitialValue

    public Option getOption(Enum<?> enumValue) {
        String enumValueString = enumValue.toString();
        for (Option option : options) {
            if (option.getOptionId().equals(enumValueString)) {
                return option;
            }
        }
        return null;
    }

    public static Option populateEnumOptions(List<Option> options, SchemaNode schema, String initialValueString) {
        options.clear();
        Option activeOption = null;
        List<String> strings = findEnumValues(schema);
        for (String item : strings) {
            SimpleStringOption option;

            if (schema.getExtension("x-i18n-enum") instanceof String enumKey) {
                String key = "enum." + enumKey + "." + item;
                option = new SimpleStringOption(item, item, key);
            } else {
                option = new SimpleStringOption(item, item);
            }

            if (initialValueString != null && initialValueString.equals(item)) {
                activeOption = option;
            }

            options.add(option);
        }
        return activeOption;
    }

    private static List<String> findEnumValues(SchemaNode schema) {
        if (schema == null) return null;

        // // TODO: This seems wrong.
        // // Should it not be: SchemaKeyword.TYPE.asString().equals(fieldName.get())
        // if ("type".equals(fieldName.get()) && SchemaKeyword.exists(fieldName.get())) {
        //     SchemaNode meta = fieldSchema.getMetaSchema();
        //     if (meta != null && isMetaSchema(meta.getOriginUri())) {
        //         return SchemaKeyword.TYPE.suggestions();
        //     }
        // }

        for (ValidationRule rule : schema.getRules()) {
            if (rule instanceof EnumRule enumRule) {
                return enumRule.getAllowedValues();
            }
        }
        return null;
    }

    // // TODO: This is dodgy. Needs drastically improved.
    // private boolean isMetaSchema(URI uri) {
    //     if (uri == null) return false;
    //     String uriString = uri.toString();
    //     return uriString.contains("json-schema.org") ||
    //             uriString.contains("cema-meta") ||
    //             uriString.contains("schema-service/schema");
    // }

    @Override
    public Option getActiveOption(Map<String,Property<?>> contextData, String parameter) {
        return activeOption;
    }

    @Override
    public List<Option> getOptions(Map<String,Property<?>> contextData, String parameter) {
        return options;
    }
}

