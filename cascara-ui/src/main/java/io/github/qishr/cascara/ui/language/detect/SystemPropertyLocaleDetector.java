package io.github.qishr.cascara.ui.language.detect;

import java.util.Locale;

public class SystemPropertyLocaleDetector {
    public static Locale detectFromSystemProps() {
        String language = System.getProperty("user.language");
        String country = System.getProperty("user.country");
        String variant = System.getProperty("user.variant");

        if (language == null) return null;
        return (country == null) ? new Locale(language) : new Locale(language, country, variant);
    }
}