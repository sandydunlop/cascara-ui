package io.github.qishr.cascara.ui.l10n;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.github.qishr.cascara.common.diagnostic.DiagnosticLocalizer;
import io.github.qishr.cascara.common.diagnostic.GlobalReporter;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.diagnostic.code.DiagnosticCode;
import io.github.qishr.cascara.common.diagnostic.code.GenericDiagnosticCode;
import io.github.qishr.cascara.lang.yaml.processor.YamlSerializer;
import io.github.qishr.cascara.ui.option.AbstractObservableOptionProvider;
import io.github.qishr.cascara.ui.option.LanguageOption;
import io.github.qishr.cascara.ui.option.OptionProvider;
import io.github.qishr.cascara.ui.option.SimpleStringOption;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class StandardLocalizer implements UiLocalizer {
    private static final Reporter REPORTER = GlobalReporter.forClass(StandardLocalizer.class);

    private static final String defaultLanguageTag = "en_US";

    private final ObservableList<LanguageOption> languages = FXCollections.observableArrayList();

    private final Map<String, Map<String, Object>> translationsByLanguage = new HashMap<>();

    private ObjectProperty<Locale> activeLocale = new SimpleObjectProperty<>();

    private String activeLanguageTag;

    private LanguageOptionProvider languageOptionProvider;


    public StandardLocalizer() {
        languages.add(new LanguageOption("System"));
        activeLocale.addListener((ob,old,val) -> {
            activeLanguageTag = val.toLanguageTag();
        });
        activeLocale.set(Locale.forLanguageTag(defaultLanguageTag));
    }


    /// {@inheritDoc}
    @Override
    public ObjectProperty<Locale> activeLocaleProperty() {
        return activeLocale;
    }

    /// {@inheritDoc}
    @Override
    public ObservableList<LanguageOption> getLanguages() {
        return languages;
    }

    public boolean hasLanguage(String languageTag) {
        return translationsByLanguage.containsKey(languageTag);
    }

    public Locale getLocale() {
        return activeLocale.get();
    }

    public void setLocale(Locale locale) {
        activeLocale.setValue(locale);
    }

    public OptionProvider getOptionProvider() {
        if (languageOptionProvider == null) {
            languageOptionProvider = new LanguageOptionProvider(this);
        }
        return languageOptionProvider;
    }

    /// {@inheritDoc}
    @Override
    public void registerTranslations(InputStream yamlStream) {
        try {
            // TODO:
            // cascara://organizer/CASC-00045D2F
            // Feed the stream directly into the serializer

            String content = new String(yamlStream.readAllBytes(), StandardCharsets.UTF_8);
            YamlSerializer serializer = new YamlSerializer();
            Translation trans = serializer.fromText(content, Translation.class);
            String languageTag = trans.getLanguageTag();
            if (languageTag == null) {
                REPORTER.error(GenericDiagnosticCode.ERROR, "No `lang` key specified in translations file");
                return;
            }
            Map<String, Object> translations = translationsByLanguage.get(languageTag);
            if (translations == null) {
                translations = addLanguage(languageTag);
            }
            mergeMaps(translations, trans.getTranslations());

        } catch (Exception e) {
            REPORTER.error(e, GenericDiagnosticCode.ERROR, "Failed to load translations: " + e.getMessage());
        }
    }

    /// {@inheritDoc}
    @Override
    public String format(String key, Object... args) {
        String pattern = getPattern(key);
        if (pattern == null) {
            return key;
        }
        try {
            return MessageFormat.format(pattern, args);
        } catch (IllegalArgumentException e) {
            return String.format(DiagnosticLocalizer.FORMATTING_ERROR, key, pattern);
        }
    }

    /// {@inheritDoc}
    @Override
    public String format(DiagnosticCode diagnosticCode, Object... args) {
        if (diagnosticCode.getCode() == null || diagnosticCode.getCode().isEmpty()) {
            return String.format(DiagnosticLocalizer.FORMATTING_ERROR, diagnosticCode.getCode(), diagnosticCode.getMessage());
        }
        String pattern = getPattern("diagnostic." + diagnosticCode.getCode());
        if (pattern == null) {
            try {
                return MessageFormat.format(diagnosticCode.getMessage(), args);
            } catch (IllegalArgumentException e) {
                return String.format(DiagnosticLocalizer.FORMATTING_ERROR, diagnosticCode.getCode(), diagnosticCode.getMessage());
            }
        }
        try {
            return MessageFormat.format(pattern, args);
        } catch (IllegalArgumentException e) {
            return String.format(DiagnosticLocalizer.FORMATTING_ERROR, diagnosticCode.getCode(), pattern);
        }
    }

    //
    // Retrieval
    //

    private String get(String key) {
        Object value = resolveKey(key, translationsByLanguage.get(activeLanguageTag));
        if (value == null) {
            value = resolveKey(key, translationsByLanguage.get(defaultLanguageTag));
        }
        return value == null ? null : value.toString();
    }

    private String getPattern(String key) {
        String pattern = get(key);
        if (pattern == null) {
            return null;
        }
        return pattern.replace("'", "''");
    }

    private Object resolveKey(String key, Map<String,Object> languageTree) {
        String[] parts = key.split("\\.");
        Object current = languageTree;
        for (String part : parts) {
            if (current instanceof Map<?, ?> map) {
                current = map.get(part);
            } else {
                return null;
            }
        }
        return current;
    }

    /// Merges a new set of translations into the existing pool.
    /// If a key already exists, the new value overwrites the old one.
    /// This allows modules to "patch" or add to the global dictionary.
    @SuppressWarnings("unchecked")
    private void mergeMaps(Map<String, Object> target, Map<String, Object> source) {
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String key = entry.getKey();
            Object sourceValue = entry.getValue();

            if (sourceValue instanceof Map && target.get(key) instanceof Map) {
                // Both are maps, so we need to dive deeper and merge them
                mergeMaps((Map<String, Object>) target.get(key), (Map<String, Object>) sourceValue);
            } else {
                // It's a leaf node (String/List) or the target doesn't have this branch yet.
                // Just put the source value in.
                target.put(key, sourceValue);
            }
        }
    }

    //
    // Registration
    //

    /// @param localeTag IETF BCP 47 language tag string
    private Map<String, Object> addLanguage(String languageTag) {
        // In Locale:
        //
        // Language is an ISO 639 alpha-2 or alpha-3 code or registered language subtag.
        // Region is an ISO 3166 alpha-2 country code or UN numeric-3 area code.
        // Variant is a case-sensitive value or set of values specifying a variation of a Locale.
        // Script must be a valid ISO 15924 alpha-4 code.
        // Extensions is a map which consists of single character keys and String values.
        //
        // IANA Language Subtag Registry: Language, Region, Variant, Script
        // https://www.iana.org/assignments/language-subtag-registry/language-subtag-registry
        Locale locale = Locale.forLanguageTag(languageTag);
        LanguageOption option = new LanguageOption(locale);
        languages.add(option);
        Map<String, Object> translations = new HashMap<>();
        translationsByLanguage.put(languageTag, translations);
        return translations;
    }

    public static class LanguageOptionProvider extends AbstractObservableOptionProvider {
        private UiLocalizer localizer;

        @Override
        public List<LanguageOption> getOptions(Map<String,Property<?>> contextData, String parameter) {
            return localizer.getLanguages();
        }


        public LanguageOptionProvider(UiLocalizer localizer) {
            this.localizer = localizer;
            localizer.getLanguages().addListener((ListChangeListener<? super SimpleStringOption>)c -> {
                listeners.forEach(Runnable::run);
            });
        }
    }

}
