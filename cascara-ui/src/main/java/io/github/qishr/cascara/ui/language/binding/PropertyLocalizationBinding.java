package io.github.qishr.cascara.ui.language.binding;

import io.github.qishr.cascara.ui.language.ObservableLocalizer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;

public class PropertyLocalizationBinding extends LocalizationBinding {
    @Override
    protected PropertyLocalizationBinding self() { return this; }

    @SuppressWarnings("unchecked")
    public PropertyLocalizationBinding(ObservableLocalizer localizer, Property<?> target, String key, Object... args) {
        super(localizer, key, args);
        if (target instanceof ObjectProperty objProp) {
            listener = o -> {
                objProp.setValue(localizer.format(key, args));
            };
        } else if (target instanceof StringProperty stringProp) {
            listener = o -> {
                stringProp.setValue(localizer.format(key, args));
            };
        }
        startListening();
    }
}
