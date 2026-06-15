package io.github.qishr.cascara.ui.option;

import java.util.Locale;

public class LanguageOption extends SimpleStringOption {
    private Locale locale;

    public LanguageOption(String systemDisplayName) {
        super("system", systemDisplayName);
    }

    public LanguageOption(Locale locale) {
        super(languageTag(locale), locale.getDisplayName());
    }

    public Locale getLocale() { return locale; }

    private static String languageTag(Locale locale) {
        return locale.getLanguage() + "-" + locale.getCountry();
    }
}