package io.github.qishr.cascara.ui.language.detect;

import java.util.Locale;
import java.util.Map;

public class EnvVarLocaleDetector {
    public static Locale detect() {
        Map<String, String> env = System.getenv();

        // Check common locale variables (Unix-like systems)
        String lang = env.get("LANG");

        // TODO: LANG is set, but it's null here?
        // ❯❯❯ echo $LANG
        // en_GB.UTF-8

        if (lang == null) lang = env.get("LC_ALL");
        if (lang == null) lang = env.get("LC_MESSAGES");

        // Fallback for Windows
        if (lang == null) lang = env.get("USERLANG");

        if (lang != null) {
            // Parse lang (e.g., "en_US.UTF-8" → "en_US")
            String[] parts = lang.split("[._]");
            if (parts.length >= 2) {
                return new Locale(parts[0], parts[1]); // language, country
            } else if (parts.length == 1) {
                return new Locale(parts[0]); // only language
            }
        }
        return null; // No valid locale found
    }
}