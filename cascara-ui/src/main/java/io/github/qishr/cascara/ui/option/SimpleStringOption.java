package io.github.qishr.cascara.ui.option;

public class SimpleStringOption implements StringOption {
    private final String id;
    private final String text;
    private final String translationKey;

    public SimpleStringOption(String id, String text) {
        this(id, text, null);
    }

    public SimpleStringOption(String id, String text, String translationKey) {
        this.id = id;
        this.text = text;
        this.translationKey = translationKey;
    }

    @Override public String getOptionId() { return id; }
    @Override public String getOptionText() { return text; }
    @Override public String getOptionTranslationKey() { return translationKey; }
}