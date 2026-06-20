package io.github.qishr.cascara.ui.widget;

public class ToolBarTextBox extends ToolBarItem {
    String text = "";
    private OnPressEnterHandler onPressEnterHandler = null;
    private OnTypeHandler onTypeHandler = null;

    public ToolBarTextBox(String text, OnTypeHandler onTypeHandler, OnPressEnterHandler onPressEnter) {
        this.text = text;
        this.onTypeHandler = onTypeHandler;
        this.onPressEnterHandler = onPressEnter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public interface OnPressEnterHandler {
        void onPressEnter(String text);
    }

    public void onPressEnter(String text) {
        if (onPressEnterHandler != null) {
            onPressEnterHandler.onPressEnter(text);
        }
    }

    public interface OnTypeHandler {
        void onType();
    }

    public void onType() {
        if (onTypeHandler != null) {
            onTypeHandler.onType();
        }
    }
}
