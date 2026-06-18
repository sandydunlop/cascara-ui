package io.github.qishr.cascara.ui.menu;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class StandardSystemMenus implements SystemMenusService {

    private ObservableMenuItem menuRoot;

    public StandardSystemMenus() {
    }

    public void set(Scene mainWindow) {
    }

    private Runnable onAbout = null;
    public void setOnAbout(Runnable handler) {this.onAbout = handler;}
    public void onAbout() {if (onAbout != null) {onAbout.run();}}

    private Runnable onSettings = null;
    public void setOnSettings(Runnable handler) {this.onSettings = handler;}
    public void onSettings() {if (onSettings != null) {onSettings.run();}}

    private Runnable onQuit = null;
    public void setOnQuit(Runnable handler) {this.onQuit = handler;}
    public void onQuit() {if (onQuit != null) {onQuit.run();}}

    @Override
    public void setMenuRoot(ObservableMenuItem menuRoot) {
        this.menuRoot = menuRoot;
    }

    @Override
    public void integrate(Stage stage) {
    }

    @Override
    public ObservableMenuItem buildAppMenu(String appName) {
        ObservableMenuItem appMenu = menuRoot.addMenu("app", "AppMenu");
        return appMenu;
    }

    @Override
    public ObservableMenuItem buildWindowMenu() {
        ObservableMenuItem windowMenu = menuRoot.addMenu("window", "Window");
        // Menu windowMenu = new Menu("WindowMenu");
        return windowMenu;
    }
}
