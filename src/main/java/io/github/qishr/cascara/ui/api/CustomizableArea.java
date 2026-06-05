package io.github.qishr.cascara.ui.api;

import javafx.scene.Node;

public interface CustomizableArea {
    void dispose();
    void addContent(String title, Node node);
    void removeContent(Node node);
    void selectContent(Node node);
}
