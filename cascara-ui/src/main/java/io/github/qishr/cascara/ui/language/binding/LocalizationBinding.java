package io.github.qishr.cascara.ui.language.binding;

import io.github.qishr.cascara.ui.language.ObservableLocalizer;

import javafx.beans.InvalidationListener;

public abstract class LocalizationBinding {
    protected ObservableLocalizer localizer;
    protected String key;
    protected Object[] args;
    protected String defaultText;
    protected InvalidationListener listener;

    protected LocalizationBinding() {
        // No public constructor
    }

    protected abstract LocalizationBinding self();

    protected LocalizationBinding(ObservableLocalizer localizer, String key, Object... args) {
        this.localizer = localizer;
        this.key = key;
        this.args = args;
    }

    protected void startListening() {
        listener.invalidated(null);
        if (localizer.activeLocaleProperty() != null) {
            localizer.activeLocaleProperty().addListener(listener);
        }
    }

    public LocalizationBinding withDefault(String text) {
        this.defaultText = text;
        if (listener != null) {
            listener.invalidated(null);
        }
        return self();
    }

    // private void applyDefault() {
    //     if (target instanceof Labeled labeled) {
    //         labeled.setText(defaultText);
    //     }
    // }
}
