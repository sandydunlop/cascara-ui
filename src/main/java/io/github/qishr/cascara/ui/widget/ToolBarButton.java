package io.github.qishr.cascara.ui.widget;

import javafx.application.Platform;

public class ToolBarButton extends ToolBarItem {
    String text = "";
    String iconPath = "";
    private OnClickHandler onClick = null;

    public ToolBarButton(String text, OnClickHandler onClick) {
        this.text = text;
        this.onClick = onClick;
    }

    public ToolBarButton(String text, String iconPath) {
        this.text = text;
        this.iconPath = iconPath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public interface OnClickHandler {
        void onClick();
    }

    public void onClick() {
        Platform.runLater(() -> {
            if (onClick != null) {
                onClick.onClick();
            }
        });
    }

    public void setOnClick(OnClickHandler handler) {
        this.onClick = handler;
    }
}
