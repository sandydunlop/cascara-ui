package io.github.qishr.cascara.ui.menu;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class ObservableMenus {
    public static ObservableMenuItem createRoot() {
        ObservableMenuItem root = new ObservableMenuItem(new MenuBar());
        return root;
    }

    public static ObservableMenuItem createMenu(String name, String text) {
        ObservableMenuItem menu = new ObservableMenuItem(name, text, new Menu());
        return menu;
    }

    public static ObservableMenuItem createMenuItem(String name, String text) {
        ObservableMenuItem menuItem = new ObservableMenuItem(name, text, new MenuItem());
        return menuItem;
    }

    public static void attachMenuItems(ObservableList<ObservableMenuItem> items) {
        for (ObservableMenuItem item : items) {
            ObservableMenuItem destination = item.getDestination();
            destination.getChildren().add(item);
        }
    }

    public static void detachMenuItems(ObservableList<ObservableMenuItem> items) {
        for (ObservableMenuItem item : items) {
            ObservableMenuItem destination = item.getDestination();
            destination.getChildren().remove(item);
        }
    }
}
