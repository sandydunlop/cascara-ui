package io.github.qishr.cascara.ui.language;

import java.util.Locale;

import io.github.qishr.cascara.ui.option.SimpleStringOption;

public class LanguageOption extends SimpleStringOption {
    private Locale locale;

    public LanguageOption(String systemDisplayName, String translationKey) {
        super("system", systemDisplayName, translationKey);
    }

    public LanguageOption(Locale locale, String localizedName) {
        super(locale.toLanguageTag(), localizedName);
        this.locale = locale;
    }

    public Locale getLocale() { return locale; }
}