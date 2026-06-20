package io.github.qishr.cascara.ui.language;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.function.Consumer;

import io.github.qishr.cascara.common.diagnostic.code.DiagnosticCode;
import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.schema.util.SchemaGenerator;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class Localization {
    private static volatile ObservableLocalizer localizer = new UiLocalizer();

    public static ObservableLocalizer getLocalizer() {
        return localizer;
    }

    public static void setLocalizer(ObservableLocalizer customLocalizer) {
        localizer = customLocalizer != null ? customLocalizer : new UiLocalizer();
    }

    public static ReadOnlyObjectProperty<Locale> localeProperty() {
        return localizer.activeLocaleProperty();
    }

    // TODO: Is this really needed?
    public static Locale getLocale() {
        return localeProperty().get();
    }

    public static String format(String key, Object... details) {
        return localizer.format(key, details);
    }

    public static String format(DiagnosticCode diagnosticCode, Object... details) {
        return localizer.format(diagnosticCode, details);
    }

    @SuppressWarnings("unchecked")
    public static void bind(Property<?> target, String key, Object... args) {
        InvalidationListener listener = null;

        if (target instanceof ObjectProperty objProp) {
            listener = o -> {
                objProp.set(localizer.format(key, args));
            };
        } else if (target instanceof StringProperty stringProp) {
            listener = o -> {
                stringProp.setValue(localizer.format(key, args));
            };
        }

        if (listener != null) {
            listener.invalidated(null); // Run immediate initial evaluation pass
            if (localizer.activeLocaleProperty() != null) {
                localizer.activeLocaleProperty().addListener(listener);
            }
        }
    }

    public static void bind(Labeled target, String key, Object... args) {
        InvalidationListener listener = o -> {
            target.setText(localizer.format(key, args));
        };
        listener.invalidated(null);
        if (localizer.activeLocaleProperty() != null) {
            localizer.activeLocaleProperty().addListener(listener);
        }
    }

    public static void bind(TextField target, String key, Object... args) {
        InvalidationListener listener = o -> {
            target.setText(localizer.format(key, args));
        };
        listener.invalidated(null);
        if (localizer.activeLocaleProperty() != null) {
            localizer.activeLocaleProperty().addListener(listener);
        }
    }

    public static void bind(Tab target, String key, Object... args) {
        InvalidationListener listener = o -> target.setText(localizer.format(key, args));
        listener.invalidated(null);
        if (localizer.activeLocaleProperty() != null) {
            localizer.activeLocaleProperty().addListener(listener);
        }
    }

    public static void bind(MenuItem target, String key, Object... args) {
        InvalidationListener listener = o -> target.setText(localizer.format(key, args));
        listener.invalidated(null);
        if (localizer.activeLocaleProperty() != null) {
            localizer.activeLocaleProperty().addListener(listener);
        }
    }

    public static void bind(Stage target, String key, Object... args) {
        InvalidationListener listener = o -> target.setTitle(localizer.format(key, args));
        listener.invalidated(null);
        if (localizer.activeLocaleProperty() != null) {
            localizer.activeLocaleProperty().addListener(listener);
        }
    }

    // TODO: FieldLabel, Field, LabeedField
    public static void bind() {

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

    //
    // Schema Translation
    //

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

    // cascara://organizer/CASC-00028C57
    // TODO: names of title key and description key need to be user-overridable
    public static String getTitleKey(SchemaNode schema) {
        Object extKey = schema.getExtension(SchemaGenerator.TITLE_KEY);
        return determineKey(schema.getTitle(), extKey);
    }

    public static String getDescriptionKey(SchemaNode schema) {
        Object extKey = schema.getExtension(SchemaGenerator.DESCRIPTION_KEY);
        return determineKey(schema.getDescription(), extKey);
    }

    private static String determineKey(String value, Object extKey) {
        String extractedKey = extractKey(value);
        if (extKey instanceof String extValue) {
            return extValue;
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
}
