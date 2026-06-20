package io.github.qishr.cascara.ui.demo;

import java.io.InputStream;

import io.github.qishr.cascara.common.io.filewatcher.FileWatcher;
import io.github.qishr.cascara.ui.control.OptionChooser;
import io.github.qishr.cascara.ui.language.Localization;
import io.github.qishr.cascara.ui.language.UiLocalizer;
import io.github.qishr.cascara.ui.theme.ThemeEngine;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Launcher extends Application {
    private UiLocalizer localizer;
    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        if (Localization.getLocalizer() instanceof UiLocalizer localiser) {
            this.localizer = localiser;
        }

        registerTranslations();

        Label themeLabel = new Label();
        Localization.bind(themeLabel, "label.theme");

        Label languageLabel = new Label();
        Localization.bind(languageLabel, "label.language");

        OptionChooser themeChooser = new OptionChooser(
            ThemeEngine.getThemeOptionProvider()
        );

        themeChooser.getSelectionModel().selectedItemProperty().addListener((obs, old, theme) -> {
            ThemeEngine.setTheme(theme);
        });

        OptionChooser languageChooser = new OptionChooser(
            localizer.getLanguageOptionProvider()
        );

        languageChooser.getSelectionModel().selectedItemProperty().addListener((obs, old, language) -> {
            localizer.setActiveLanguage(language);
        });

        HBox choserBox = new HBox(
            16,
            themeLabel,
            themeChooser,
            new Rectangle(24, 0),
            languageLabel,
            languageChooser
        );
        choserBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox();
        layout.setSpacing(8);
        layout.setPadding(new Insets(16));
        layout.getChildren().add(choserBox);

        layout.getChildren().add(new Samples().getView());

        scene = new Scene(layout, 800, 500);

        ThemeEngine.bind(scene);

        primaryStage.setScene(scene);
        primaryStage.show();

        Localization.bind(
            primaryStage, "app.window-title",
            ThemeEngine.class.getModule().getDescriptor().toNameAndVersion()
        );
    }

    @Override
    public void stop() throws Exception {
        // When the OptionProvider is used, the theme engine uses a
        // background thread to watch the theme directory for updates.
        // We close it here to allow the app to close cleanly.
        FileWatcher.clearAll();
    }

    private void registerTranslations() {
        registerLanguage("ar-AE");
        registerLanguage("en-US");
        registerLanguage("es-ES");
        registerLanguage("fr-FR");
    }

    private void registerLanguage(String languageTag) {
        InputStream masterLang = getClass().getResourceAsStream(languageTag + ".yaml");
        if (masterLang != null) {
            localizer.registerTranslations(masterLang);
        }
    }
}

