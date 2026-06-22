package io.github.qishr.cascara.ui.language.binding;

import io.github.qishr.cascara.ui.form.FieldLabel;
import io.github.qishr.cascara.ui.language.ObservableLocalizer;

public class FieldLabelLocalizationBinding extends LocalizationBinding {
    @Override
    protected FieldLabelLocalizationBinding self() { return this; }

    public FieldLabelLocalizationBinding(ObservableLocalizer localizer, FieldLabel target, String key, Object... args) {
        super(localizer, key, args);
        listener = o -> {
            target.setText(localizer.formatWithDefault(defaultText, key, args));
        };
        startListening();
    }
}
