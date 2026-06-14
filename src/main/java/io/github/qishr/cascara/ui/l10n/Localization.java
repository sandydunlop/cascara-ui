package io.github.qishr.cascara.ui.l10n;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.function.Consumer;

import io.github.qishr.cascara.schema.structure.SchemaNode;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Labeled;
import javafx.util.StringConverter;

public class Localization {
    private static volatile Localizer localizer = new DummyLocalizer();

    public static void setLocalizer(Localizer customLocalizer) {
        localizer = customLocalizer != null ? customLocalizer : new DummyLocalizer();
    }

    public static ObjectProperty<Locale> localeProperty() {
        return localizer.activeLocaleProperty();
    }

    public static Locale getLocale() {
        return localeProperty().get();
    }

    public static void bind(Labeled node, String key, Object... args) {
        InvalidationListener listener = o -> node.setText(localizer.format(key, args));
        listener.invalidated(null);
        if (localizer.activeLocaleProperty() != null) {
            localizer.activeLocaleProperty().addListener(listener);
        }
    }

    @SuppressWarnings("unchecked")
    public static void bind(Property<?> prop, String key, Object... args) {
        InvalidationListener listener = null;

        if (prop instanceof ObjectProperty objProp) {
            listener = o -> objProp.set(localizer.format(key, args));
        } else if (prop instanceof StringProperty stringProp) {
            listener = o -> stringProp.setValue(localizer.format(key, args));
        }

        if (listener != null) {
            listener.invalidated(null); // Run immediate initial evaluation pass
            if (localizer.activeLocaleProperty() != null) {
                localizer.activeLocaleProperty().addListener(listener);
            }
        }
    }

    public static void bindLocale(DatePicker picker) {
        // 1. Define the localized configuration engine
        Consumer<Locale> configurePicker = (locale) -> {
            if (locale == null) return;

            DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(locale);

            picker.setConverter(new StringConverter<>() {
                @Override public String toString(LocalDate d) { return d != null ? formatter.format(d) : ""; }
                @Override public LocalDate fromString(String s) { return (s != null && !s.isBlank()) ? LocalDate.parse(s, formatter) : null; }
            });
        };

        // 2. Bootstrap immediately with the current state
        configurePicker.accept(getLocale());

        // 3. Listen for dynamic hot-swaps down the line
        if (localeProperty() != null) {
            localeProperty().addListener((obs, old, newLocale) -> configurePicker.accept(newLocale));
        }
    }

    // cascara://organizer/CASC-00028C57
    // TODO: %key% format
    public static String getTitle(SchemaNode schema) {
        if (schema == null) return null;
        String titleKey = getTitleKey(schema);
        if (titleKey != null) {
            return localizer.format(titleKey);
        }
        return schema.getTitle();
    }

    public static String getDescription(SchemaNode schema) {
        if (schema == null) return null;
        String descriptionKey = getDescriptionKey(schema);
        if (descriptionKey != null) {
            return localizer.format(descriptionKey);
        }
        return schema.getDescription();
    }

    public static String getTitleKey(SchemaNode schema) {
        return determineKey(schema.getTitle(), schema.getTitleKey());
    }

    public static String getDescriptionKey(SchemaNode schema) {
        return determineKey(schema.getDescription(), schema.getDescriptionKey());
    }

    private static String determineKey(String value, String key) {
        String extractedKey = extractKey(value);
        if (extractedKey == null) {
            if (key == null || key.isBlank()) {
                return null;
            }
            return key;
        }
        return extractedKey;
    }

    private static String extractKey(String value) {
        if (value == null) return null;
        if (value.length() > 2) {
            char first = value.charAt(0);
            char last = value.charAt(value.length()-1);
            if (first == '%' && last == '%') {
                return value.substring(1, value.length()-1);
            }
        }
        return null;
    }

    private static class DummyLocalizer implements Localizer {
        private ObjectProperty<Locale> locale = new SimpleObjectProperty<>();
        public DummyLocalizer() { locale.set(Locale.getDefault()); }
        public String format(String code, Object... details) { return code; }
        public ObjectProperty<Locale> activeLocaleProperty() { return locale; }
    }
}
