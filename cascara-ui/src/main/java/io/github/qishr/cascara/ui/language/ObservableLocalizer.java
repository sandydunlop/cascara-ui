package io.github.qishr.cascara.ui.language;

import java.util.Locale;

import io.github.qishr.cascara.common.diagnostic.DiagnosticLocalizer;
import io.github.qishr.cascara.ui.option.Option;
import io.github.qishr.cascara.ui.option.OptionProvider;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;

public interface ObservableLocalizer extends Localizer, DiagnosticLocalizer {
    ObservableList<Option> getLanguageOptions();
    ReadOnlyObjectProperty<Locale> activeLocaleProperty();
    ReadOnlyObjectWrapper<LanguageOption> activeLanguageOptionProperty();
    OptionProvider getLanguageOptionProvider();
    void setActiveLanguage(String languageTag);
}
