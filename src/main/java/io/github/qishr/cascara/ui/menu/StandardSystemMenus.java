package io.github.qishr.cascara.ui.menu;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class StandardSystemMenus implements SystemMenusService {

    public StandardSystemMenus() {
    }

    public void set(Scene mainWindow) {
    }

    private MenuOptionHandler onAbout = null;
    public void setOnAbout(MenuOptionHandler handler) {this.onAbout = handler;}
    public void onAbout(MenuItem i) {if (onAbout != null) {onAbout.onMenuOption(i);}}

    private MenuOptionHandler onSettings = null;
    public void setOnSettings(MenuOptionHandler handler) {this.onSettings = handler;}
    public void onSettings(MenuItem i) {if (onSettings != null) {onSettings.onMenuOption(i);}}

    private MenuOptionHandler onQuit = null;
    public void setOnQuit(MenuOptionHandler handler) {this.onQuit = handler;}
    public void onQuit(MenuItem i) {if (onQuit != null) {onQuit.onMenuOption(i);}}

    @Override
    public void integrate(Stage stage, MenuBar menuBar, Menu appMenu) {
    }

    @Override
    public Menu buildAppMenu() {
        Menu appMenu = new Menu("AppMenu");
        return appMenu;
    }

    @Override
    public Menu buildWindowMenu() {
        Menu windowMenu = new Menu("WindowMenu");
        return windowMenu;
    }

    @Override
    public void setAppName(String name) {
    }
}
