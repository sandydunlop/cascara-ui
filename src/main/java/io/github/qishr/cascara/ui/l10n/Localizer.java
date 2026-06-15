package io.github.qishr.cascara.ui.l10n;

import java.io.InputStream;

public interface Localizer {

    /// Formats the code with dynamic arguments using the environment's current language bundle.
    String format(String code, Object... details);

    void registerTranslations(InputStream yamlStream);
}