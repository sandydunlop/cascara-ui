package io.github.qishr.cascara.ui.language.detect;

import java.util.Locale;

import io.github.qishr.cascara.common.diagnostic.GlobalReporter;
import io.github.qishr.cascara.common.diagnostic.Reporter;

public class HybridLocaleDetector {
    private static final Reporter REPORTER = GlobalReporter.forClass(HybridLocaleDetector.class);

    public static Locale detectOSLocale() {
        Locale locale;

        // Step 1: Check environment variables
        locale = EnvVarLocaleDetector.detect();
        if (locale != null) {
            REPORTER.debug("Found locale via EnvVarLocaleDetector: " + locale.toLanguageTag());
            return locale;
        }

        // TODO: Move this to its own module and get the Windows side working

        // There should probalby be something like:
        //   cascara.platform
        //   cascara.platform.linux
        //   cascara.platform.macos
        //   cascara.platform.windows

        // Or, since we already have cascara.macos:
        //   cascara.common - handle the loading of platform-dependent things
        //   cascara.linux
        //   cascara.macos
        //   cascara.windows

        // https://www.javathinking.com/blog/how-to-detect-operating-system-language-locale-from-java-code/

        // Step 2: Platform-specific detection
        String os = System.getProperty("os.name").toLowerCase();
        // if (os.contains("win")) {
        //     locale = WindowsRegistryLocaleDetector.detectFromRegistry();
        // } else
        if (os.contains("nix") || os.contains("nux")) {
            locale = LinuxFileLocaleDetector.detect();
            if (locale != null) {
                REPORTER.debug("Found locale via LinuxFileLocaleDetector: " + locale.toLanguageTag());
                return locale;
            }
        } else if (os.contains("mac")) {
            locale = MacOSLocaleDetector.detect();
            if (locale != null) {
                REPORTER.debug("Found locale via MacOSLocaleDetector: " + locale.toLanguageTag());
                return locale;
            }
        }

        // Step 3: Fallback to JVM default
        locale = Locale.getDefault();
        REPORTER.debug("Using JVM default locale: " + locale.toLanguageTag());
        return locale;
    }
}