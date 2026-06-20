package io.github.qishr.cascara.ui.language.detect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

public class MacOSLocaleDetector {
    public static Locale detect() {
        Locale locale;
        //  = detectFromEnv();
        // if (locale != null) {
        //     return locale;
        // }
        locale = detectFromDefaults();
        return locale;
    }

    // private static Locale detectFromEnv() {
    //     try {
    //         Process process = new ProcessBuilder("echo", "$LANG")
    //             .redirectErrorStream(true)
    //             .start();
    //         BufferedReader reader = new BufferedReader(
    //             new InputStreamReader(process.getInputStream())
    //         );
    //         String localeStr = reader.readLine(); // e.g., "en_US"
    //         process.waitFor();

    //         Locale locale = Locale.forLanguageTag(localeStr.replace("_", "-")); // "en_US" → "en-US"

    //         // if (locale.get)

    //         return locale;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    private static Locale detectFromDefaults() {
        try {
            Process process = new ProcessBuilder("defaults", "read", "-g", "AppleLocale")
                .redirectErrorStream(true)
                .start();
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );
            String localeStr = reader.readLine(); // e.g., "en_US"
            process.waitFor();

            if (localeStr.length() < 5) {
                return null;
            }

            if (localeStr.length() > 5) {
                localeStr = localeStr.substring(0, 5);
            }

            Locale locale = Locale.forLanguageTag(localeStr.replace("_", "-")); // "en_US" → "en-US"

            // if (locale.get)

            return locale;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}