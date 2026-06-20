package io.github.qishr.cascara.ui.menu;

import io.github.qishr.cascara.common.diagnostic.GlobalReporter;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.diagnostic.code.GenericDiagnosticCode;
import io.github.qishr.cascara.ui.data.UiDataException;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class ObservableMenuFactory {
    private static final Reporter REPORTER = GlobalReporter.forClass(ObservableMenuFactory.class);

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

    public static ObservableMenuItem createMenuItem(String name, String text, MenuItem fxItem) {
        ObservableMenuItem menuItem = new ObservableMenuItem(name, text, fxItem);
        return menuItem;
    }

    public static void attachMenuItems(ObservableList<ObservableMenuItem> items) {
        if (items == null) throw new UiDataException(GenericDiagnosticCode.UNEXPECTED_NULL, "items");
        for (ObservableMenuItem item : items) {
            ObservableMenuItem destination = item.getDestination();
            if (destination == null) {
                // throw new UiDataException(GenericDiagnosticCode.UNEXPECTED_NULL, "item.getDestination()");
                REPORTER.error(GenericDiagnosticCode.UNEXPECTED_NULL, "item.getDestination() in attachMenuItems " + item.getNodeName());
            } else {
                destination.getChildren().add(item);
            }
        }
    }

    public static void detachMenuItems(ObservableList<ObservableMenuItem> items) {
        if (items == null) throw new UiDataException(GenericDiagnosticCode.UNEXPECTED_NULL, "items");
        for (ObservableMenuItem item : items) {
            ObservableMenuItem destination = item.getDestination();
            if (destination == null) {
                // throw new UiDataException(GenericDiagnosticCode.UNEXPECTED_NULL, "item.getDestination()");
                REPORTER.error(GenericDiagnosticCode.UNEXPECTED_NULL, "item.getDestination() in detachMenuItems " + item.getNodeName());
            } else {
                destination.getChildren().remove(item);
            }
        }
    }
}
