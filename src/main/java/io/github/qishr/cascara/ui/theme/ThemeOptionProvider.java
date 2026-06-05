package io.github.qishr.cascara.ui.theme;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.qishr.cascara.common.io.filewatcher.FileWatcher;
import io.github.qishr.cascara.ui.option.AbstractObservableOptionProvider;
import io.github.qishr.cascara.ui.option.SimpleStringOption;
import io.github.qishr.cascara.ui.option.StringOption;

import javafx.beans.property.Property;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class ThemeOptionProvider extends AbstractObservableOptionProvider implements AutoCloseable {
    private final Path cascaraDir = Paths.get(System.getProperty("user.home")).resolve(".cascara");
    private final Path themesDir = cascaraDir.resolve("themes");
    private final Path packagesDir = cascaraDir.resolve("packages");
    private final FileWatcher themesWatcher;

    private final ObservableSet<StringOption> cascThemeSet = FXCollections.observableSet();
    private final SetProperty<StringOption> cascThemes = new SimpleSetProperty<>(cascThemeSet);
    private final StringOption defaultTheme = new SimpleStringOption("default", "Default");

    private final ObservableSet<StringOption> vsixThemeSet = FXCollections.observableSet();
    private final SetProperty<StringOption> vsixThemes = new SimpleSetProperty<>(vsixThemeSet);

    public ThemeOptionProvider() throws IOException {
        if (!Files.isDirectory(themesDir)) {
            Files.createDirectories(themesDir);
        }
        themesWatcher = new FileWatcher();
        themesWatcher.watchDirectory(themesDir, () -> {
            enumerateThemes();
        });
        themesWatcher.watchDirectory(packagesDir, () -> {
            enumeratePackages();
        });
        cascThemes.addListener((obs, oldSet, newSet) -> {
            listeners.forEach(Runnable::run);
        });
        enumerateThemes();
        enumeratePackages();
    }

    @Override
    public void close() {
        themesWatcher.clear();
    }

    @Override
    public List<StringOption> getOptions(Map<String,Property<?>> contextData, String parameter) {
        List<StringOption> result = new ArrayList<>();
        result.addAll(cascThemes.get());
        result.addAll(vsixThemes.get());
        return result;
    }

    private void enumerateThemes() {
        cascThemes.clear();
        cascThemes.add(defaultTheme);
        File[] files = themesDir.toFile().listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.getName().endsWith(".casc")) {
                    String themeId = f.getName();
                    CascaraTheme theme = ThemeEngine.instance().getTheme(themeId);
                    String themeName = theme.getName();
                    SimpleStringOption option = new SimpleStringOption(themeId, themeName);
                    cascThemes.add(option);
                }
            }
        }
    }

    private void enumeratePackages() {
        vsixThemes.clear();
        File[] files = packagesDir.toFile().listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.getName().endsWith(".vsix")) {
                    String themeId = f.getName();
                    CascaraTheme theme = ThemeEngine.instance().getTheme(themeId);
                    String themeName = theme.getName();
                    SimpleStringOption option = new SimpleStringOption(themeId, themeName);
                    vsixThemes.add(option);
                }
            }
        }
    }
}
