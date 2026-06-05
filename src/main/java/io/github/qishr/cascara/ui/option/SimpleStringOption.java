package io.github.qishr.cascara.ui.option;

public class SimpleStringOption implements StringOption {
    private final String id;
    private final String text;

    public SimpleStringOption(String id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override public String getOptionId() { return id; }
    @Override public String getOptionText() { return text; }
}