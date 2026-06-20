package io.github.qishr.cascara.ui.menu;

import io.github.qishr.cascara.common.service.ServiceProvider;
import javafx.stage.Stage;

public interface SystemMenusService extends ServiceProvider {
    ObservableMenuItem buildAppMenu(String appName);
    ObservableMenuItem buildWindowMenu();

    void setMenuRoot(ObservableMenuItem menuRoot);
    void integrate(Stage stage);

    void setOnAbout(Runnable handler);
    void setOnSettings(Runnable handler);
    void setOnQuit(Runnable handler);
}

