package io.github.qishr.cascara.ui.l10n;

import java.util.Locale;

import io.github.qishr.cascara.common.diagnostic.DiagnosticLocalizer;
import io.github.qishr.cascara.ui.option.LanguageOption;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

public interface UiLocalizer extends Localizer, DiagnosticLocalizer {
    ObservableList<LanguageOption> getLanguages();
    ObjectProperty<Locale> activeLocaleProperty();
}
