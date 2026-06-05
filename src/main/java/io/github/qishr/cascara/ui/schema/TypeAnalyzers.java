package io.github.qishr.cascara.ui.schema;

import java.util.HashMap;
import java.util.Map;

import io.github.qishr.cascara.schema.util.SchemaResolver;
import io.github.qishr.cascara.schema.util.TypeAnalyzer;

public class TypeAnalyzers {
    private static TypeAnalyzers instance;
    private SchemaResolver schemaResolver;
    private Map<String, TypeAnalyzer> analyzers = new HashMap<>();

    private TypeAnalyzers() {}

    public static TypeAnalyzers instance() {
        if (instance == null) {
            instance = new TypeAnalyzers();
        }
        return instance;
    }

    public static Map<String, TypeAnalyzer> getAnalyzers() { return instance().analyzers; }

    public static void register(TypeAnalyzer analyazer) {
        instance().analyzers.put(analyazer.getClass().getName(), analyazer);
    }

    public static boolean isRegistered(Class<? extends TypeAnalyzer> clazz) {
        return instance().analyzers.keySet().contains(clazz.getName());
    }

    public static SchemaResolver getSchemaResolver() { return instance().schemaResolver; }

    public static void setSchemaResolver(SchemaResolver resolver) {
        instance().schemaResolver = resolver;
    }



}
