package io.github.qishr.cascara.ui.language.binding;

import javafx.scene.control.Labeled;
import io.github.qishr.cascara.ui.language.ObservableLocalizer;

public class LabeledLocalizationBinding extends LocalizationBinding {
    @Override
    protected LabeledLocalizationBinding self() { return this; }

    public LabeledLocalizationBinding(ObservableLocalizer localizer, Labeled target, String key, Object... args) {
        super(localizer, key, args);
        listener = o -> {
            // String text = localizer.formatWithDefault(defaultText, key, args);
            String old = target.getText();
            String text = localizer.format(key, args);
            System.out.println(target.getText()+" > "+text);

            if (old != null && old.equals("Noellch Theme")) {
                if (text.equals("Por defecto")) {
                    System.out.println();
                }
            }

            target.setText(text);
            // target.setText(localizer.formatWithDefault(defaultText, key, args));
        };
        startListening();
    }
}
