package io.github.qishr.cascara.ui.widget;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.ui.menu.ObservableMenuItem;
import javafx.collections.ObservableList;

public class ToolBarSection {
    String ownerExtension = "";
    String name = "";
    List<ToolBarItem> items = new ArrayList<>();
    ObservableList<ObservableMenuItem> menu = null;

    public ToolBarSection(String ownerExtension, String name) {
        this.ownerExtension = ownerExtension;
        this.name = name;
    }

    public void add(ToolBarItem item) {
        items.add(item);
    }

    public void add(ObservableList<ObservableMenuItem> item) {
        menu = item;
    }

    public List<ToolBarItem> getItems() {
        return items;
    }

    public ObservableList<ObservableMenuItem> getMenu() {
        return menu;
    }

    public String getName() {
        return name;
    }

    public String getOwnerExtension() {
        return ownerExtension;
    }
}
