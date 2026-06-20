package io.github.qishr.cascara.ui.menu;

import io.github.qishr.cascara.common.util.Properties;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StandardSystemMenus implements SystemMenusService {
    private Properties properties;
    private ObservableMenuItem menuRoot;
    private Runnable onAbout = null;
    private Runnable onSettings = null;
    private Runnable onQuit = null;

    public StandardSystemMenus() {
    }

    @Override
    public Properties getServiceProperties() {
        if (properties == null) {
            properties = new Properties();
        }
        return properties;
    }

    @Override
    public void setOnAbout(Runnable handler) {this.onAbout = handler;}

    public void onAbout() {if (onAbout != null) {onAbout.run();}}

    @Override
    public void setOnSettings(Runnable handler) {this.onSettings = handler;}

    public void onSettings() {if (onSettings != null) {onSettings.run();}}

    @Override
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
