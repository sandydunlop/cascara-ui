package io.github.qishr.cascara.ui.option;

import io.github.qishr.cascara.ui.language.Localization;
import javafx.beans.property.SimpleStringProperty;

public class SimpleStringOption extends AbstractOption implements StringOption {
    private SimpleStringProperty optionText = new SimpleStringProperty(this, "text");
    private final String translationKey;

    public SimpleStringOption(String id, String text) {
        this(id, text, null);
    }

    public SimpleStringOption(String id, String text, String translationKey) {
        super(id);
        optionText.set(text);
        this.translationKey = translationKey;
        if (translationKey != null) {
            Localization.bind(optionText, translationKey);
        }
    }

    @Override public String getOptionText() { return optionText.get(); }
    @Override public String getOptionTranslationKey() { return translationKey; }

    public void setOptionText(String text) {
        optionText.set(text);
    }

    public SimpleStringProperty optionTextProperty() {
        return optionText;
    }
}