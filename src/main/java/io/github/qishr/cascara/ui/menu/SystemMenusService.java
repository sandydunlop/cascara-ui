package io.github.qishr.cascara.ui.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public interface SystemMenusService {
    public Menu buildAppMenu();
    public Menu buildWindowMenu();
    public void integrate(Stage stage, MenuBar menuBar, Menu appMenu);
    public void setAppName(String name);

    public void setOnAbout(MenuOptionHandler handler);
    public void setOnSettings(MenuOptionHandler handler);
    public void setOnQuit(MenuOptionHandler handler);

    public interface MenuOptionHandler {
        void onMenuOption(MenuItem i);
    }
}

