package io.github.qishr.cascara.ui.data;

import java.lang.reflect.Field;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import io.github.qishr.cascara.common.lang.ast.AstNode;
import io.github.qishr.cascara.schema.Schema;
import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.schema.util.SchemaCompiler;
import io.github.qishr.cascara.schema.util.SchemaGenerator;
import io.github.qishr.cascara.schema.util.CascaraSchemaUri;
import io.github.qishr.cascara.ui.api.data.ObservableTableData;
import io.github.qishr.cascara.ui.schema.TypeAnalyzers;
import io.github.qishr.cascara.ui.schema.UiTypeAnalyzer;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/// Properties shared between object and tree
public class ObservableObject implements Observable, ObservableTableData {
    // DO NOT use GlobalReporter in this class. Since this class is used in the
    // logger, it would get into an infinite loop.

    private static Map<Class<? extends ObservableObject>,SchemaNode> objectSchemas = new HashMap<>();

    private final ObjectProperty<SchemaNode> objectSchema = new SimpleObjectProperty<>();

    private final ObjectProperty<String> displayStringProperty;

    private final Map<String,Observable> map = new HashMap<>();
    private final Map<String,PropertyMetadata> properties = new HashMap<>();

    private final Map<String, Object> userData = new HashMap<>();
    private final List<InvalidationListener> listeners = new ArrayList<>();


    public ObservableObject() {
        displayStringProperty = new SimpleObjectProperty<>(this, "displayString", "");
        createObjectSchema();
        if (objectSchema.get() == null) {
            throw new UiDataException("ObservableObject failed to create schema for " + this.getClass().getSimpleName());
        }
        registerProperties();
    }

    //
    // Schema
    //

    public final SchemaNode getObjectSchema() { return objectSchema.get(); }

    public final void setObjectSchema(SchemaNode schema) {
        this.objectSchema.set(schema);
        map.clear();
        properties.clear();
        registerProperties();
    }

    public String getContentType() {
        return null;
    }

    private void createObjectSchema() {
        SchemaNode schemaNode = objectSchemas.get(getClass());
        if (schemaNode == null) {
            if (!TypeAnalyzers.isRegistered(UiTypeAnalyzer.class)) {
                TypeAnalyzers.register(new UiTypeAnalyzer());
            }
            CascaraSchemaUri schemaUri = new CascaraSchemaUri(getClass());
            SchemaGenerator generator = new SchemaGenerator();
            AstNode doc = generator.generate(this);
            SchemaCompiler compiler = new SchemaCompiler();
            compiler.setResolver(TypeAnalyzers.getSchemaResolver());
            Schema compiledSchema = compiler.compile(doc, schemaUri.toUri());
            schemaNode = compiledSchema.getRoot();
            if (schemaNode == null) {
                throw new UiDataException("ObservableObject failed to compile schema");
            }
            objectSchemas.put(getClass(), schemaNode);
        }
        objectSchema.setValue(schemaNode);
    }

    public Set<String> getPropertyNames() { return properties.keySet(); }

    public final PropertyMetadata getPropertyMetadata(String name) { return properties.get(name); }

    // public final void defineProperty(String name, SchemaType schemaType, String mediaType) {
    //     defineProperty(name, schemaType, mediaType, false);
    // }

    private final void defineProperty(String name, SchemaType schemaType, String mediaType, boolean isDeclaredProperty) {
        properties.put(name, new PropertyMetadata(schemaType, mediaType, isDeclaredProperty));
    }

    private void registerProperties() {
        Map<String,Field> fieldMap = createFieldMap();
        Map<String,SchemaNode> propertySchemas = objectSchema.get().getProperties();
        for (Entry<String,SchemaNode> entry : propertySchemas.entrySet()) {
            String propertyName = entry.getKey();
            SchemaNode propertySchema = entry.getValue();
            Field field = fieldMap.get(propertyName);
            if (field != null && field.getType().getName().equals("javafx.beans.property.ObjectProperty")) {
                // TODO: ObservableList fields
                registerDeclaredProperty(field, propertySchema);
            } else {
                createObservableProperty(propertyName, propertySchema);
            }
        }
    }

    private void registerDeclaredProperty(Field field, SchemaNode propertySchema) throws UiDataException {
        String propertyName = field.getName();
        defineProperty(propertyName, propertySchema.getType(), propertySchema.getContentMediaType(), true);

        boolean accessible = field.canAccess(this);
        field.setAccessible(true);

        // Get the field's existing value, if any.
        Object fieldValue = null;
        try {
            fieldValue = field.get(this);
        } catch (IllegalArgumentException | IllegalAccessException _) {
            // DO NOT use GlobalReporter here. Since this class is used in the logger,
            // it would get into an infinite loop of failing to report itself.
            System.err.println("Error setting value of " + propertyName + " in " + getClass().getSimpleName());
            // This is possibly okay.
        }

        // If the field has no value, set it.
        ObjectProperty<?> property;
        if (fieldValue instanceof ObjectProperty prop) {
            // TODO: I don't think this will ever be the case
            property = prop;
        } else {
            property = new SimpleObjectProperty<>(this, propertyName);
            try {
                field.set(this, property);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // The field value was unset and we can't set it.
                throw new UiDataException("Unable to set value for " + propertyName + ". " + e.getMessage(), e);
            }
        }

        map.put(propertyName, property);
        property.addListener((p) -> invalidate());
        field.setAccessible(accessible);
    }

    @SuppressWarnings({ "rawtypes", "unchecked"})
    public void createObservableProperty(String key, SchemaNode propertySchema) {
        defineProperty(key, propertySchema.getType(), propertySchema.getContentMediaType(), false);
        switch (propertySchema.getType()) {
            case OBJECT: {
                SimpleObjectProperty prop = new SimpleObjectProperty<>(this, key);
                map.put(key, prop);
                prop.addListener((p) -> invalidate());
                return;
            }
            case ARRAY: {
                ObservableList prop = FXCollections.observableArrayList();
                map.put(key, prop);
                prop.addListener((ListChangeListener.Change c) -> {
                    invalidate();
                });
                return;
            }
            case STRING: {
                SimpleObjectProperty<String> prop = new SimpleObjectProperty<>(this, key);
                map.put(key, prop);
                prop.addListener((p) -> invalidate());
                return;
            }
            case NUMBER: {
                SimpleObjectProperty<Double> prop = new SimpleObjectProperty<>(this, key);
                map.put(key, prop);
                prop.addListener((p) -> invalidate());
                return;
            }
            case INTEGER: {
                SimpleObjectProperty<Long> prop = new SimpleObjectProperty<>(this, key);
                map.put(key, prop);
                prop.addListener((p) -> invalidate());
                return;
            }
            case BOOLEAN: {
                SimpleObjectProperty<Boolean> prop = new SimpleObjectProperty<>(this, key);
                map.put(key, prop);
                prop.addListener((p) -> invalidate());
                return;
            }
            default: {
                System.out.println("Unhandled schema type for " + key + ": " + propertySchema.getType());
                return;
            }
        }
    }

    private Map<String,Field> createFieldMap() {
        Map<String,Field> fields = new HashMap<>();
        Class<?> current = this.getClass();

        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                fields.put(field.getName(), field);
            }
            current = current.getSuperclass();
        }

        return fields;
    }

    //
    // ObservableTableData Implementation
    //

    @Override
    public final Map<String,Object> getValuesMap() {
        Map<String,Object> values = new HashMap<>();
        for (Map.Entry<String,Observable> entry : map.entrySet()) {
            if (entry.getValue() instanceof ObservableList list) {
                values.put(entry.getKey(), list);
            } else if (entry.getValue() instanceof ObjectProperty property) {
                values.put(entry.getKey(), property.get());
            }
        }
        return values;
    }

    @Override
    public final Object[] getValues() {
        Object[] r = new Object[map.size()];
        int i = 0;
        for (Observable observable : map.values()) {
            if (observable instanceof ObjectProperty property) {
                // For ObjectProperty we extract the value
                r[i] = property.get();
            } else {
                // For ObservableList and anything else, we simply return it
                r[i] = observable;
            }
            i++;
        }
        return r;
    }

    @Override
    public final Observable[] getObservables() {
        Observable[] r = new Observable[map.size()];
        int i = 0;
        for (Observable observable : map.values()) {
            r[i++] = observable;
        }
        return r;
    }

    @Override
    public final Map<String,Observable> getObservablesMap() {
        return map;
    }

    @Override
    public final Observable getObservable(String key) {
        if (key.equals("self")) {
            return this;
        }
        return map.get(key);
    }

    //
    // Values
    //

    public final ObservableList<?> getObservableList(String key) {
        if (map.get(key) instanceof ObservableList list) {
            return list;
        }
        return null;
    }

    @SuppressWarnings("rawtypes") // Needed by getPaletteColors in ThemeModule
    public final ObjectProperty getObjectProperty(String key) {
        if (map.get(key) instanceof ObjectProperty property) {
            return property;
        }
        return null;
    }

    public Object get(String key) {
        if (key.equals("self")) {
            return this;
        }
        return internalGet(key);
    }

    private Object internalGet(String key) {
        Observable observable = getObservable(key);
        if (observable instanceof ObjectProperty property) {
            return property.getValue();
        } else if (observable instanceof ObservableList list) {
            return list;
        }
        return null;
    }

    public final Boolean getBoolean(String key) {
        Object value = internalGet(key);
        return value == null ? null : (Boolean)value;
    }

    public final Integer getInteger(String key) {
        Object value = internalGet(key);
        return value == null ? null : (Integer)value;
    }

    public final Long getLong(String key) {
        Object value = internalGet(key);
        return value == null ? null : (Long)value;
    }

    public final Path getPath(String key) {
        Object value = internalGet(key);
        return value == null ? null : Path.of(value.toString());
    }

    public final String getString(String key) {
        Object value = internalGet(key);
        return value == null ? null : value.toString();
    }

    public final URI getUri(String key) {
        Object value = internalGet(key);
        return value == null ? null : (URI)value;
    }

    @SuppressWarnings("unchecked")
    public void set(String key, Object value) {
        Observable observable = map.get(key);
        if (observable instanceof ObjectProperty property) {
            property.set(value);
        } else if(observable instanceof ObservableList<?> list) {
            if (value instanceof Collection collection) {
                list.setAll(collection);
            }
        } else {
            throw new UiDataException("Unrecognized property name: " + key);
        }
        invalidate();
    }

    public void putUserData(String key, Object value) { userData.put(key, value); }
    public Object getUserData(String key) { return userData.get(key); }

    //
    // Properties
    //

    public final ObjectProperty<String> displayStringProperty() { return displayStringProperty; }
    public final ObjectProperty<SchemaNode> objectSchemaProperty() { return objectSchema; }

    //
    // Observable Implementation
    //

    private void invalidate() {
        for (InvalidationListener listener : listeners) {
            listener.invalidated(this);
        }
    }

    @Override
    public void addListener(InvalidationListener listener) {
        if (listeners.contains(listener)) return;
        listeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }
}
