package io.github.qishr.cascara.ui.l10n;

import java.util.HashMap;
import java.util.Map;

import io.github.qishr.cascara.common.lang.annotation.AnyGetter;
import io.github.qishr.cascara.common.lang.annotation.AnySetter;
import io.github.qishr.cascara.common.lang.annotation.DataIgnore;
import io.github.qishr.cascara.common.lang.annotation.Serializable;
import io.github.qishr.cascara.schema.annotation.SchemaDefinition;
import io.github.qishr.cascara.schema.annotation.SchemaProperty;

@Serializable
@SchemaDefinition
public class Translation {

    @SchemaProperty
    private String lang;

    @DataIgnore
    private Map<String, Object> translations = new HashMap<>();

    @AnySetter
    public void add(String key, Object value) {
        this.translations.put(key, value);
    }

    @AnyGetter
    public Map<String, Object> getTranslations() {
        return translations;
    }

    public String getLanguageTag() {
        return lang;
    }

}
