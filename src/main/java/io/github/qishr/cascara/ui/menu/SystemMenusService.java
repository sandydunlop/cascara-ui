package io.github.qishr.cascara.ui.menu;

import javafx.stage.Stage;

public interface SystemMenusService {
    ObservableMenuItem buildAppMenu(String appName);
    ObservableMenuItem buildWindowMenu();

    void setMenuRoot(ObservableMenuItem menuRoot);
    void integrate(Stage stage);

    void setOnAbout(Runnable handler);
    void setOnSettings(Runnable handler);
    void setOnQuit(Runnable handler);
}

