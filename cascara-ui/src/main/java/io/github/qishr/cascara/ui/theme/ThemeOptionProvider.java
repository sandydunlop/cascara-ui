package io.github.qishr.cascara.ui.theme;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.qishr.cascara.common.diagnostic.LocalizableRuntimeException;
import io.github.qishr.cascara.common.io.filewatcher.FileWatcher;
import io.github.qishr.cascara.ui.api.UiDiagnosticCode;
import io.github.qishr.cascara.ui.option.AbstractOptionProvider;
import io.github.qishr.cascara.ui.option.Option;
import io.github.qishr.cascara.ui.option.SimpleStringOption;
import io.github.qishr.cascara.ui.option.StringOption;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class ThemeOptionProvider extends AbstractOptionProvider implements AutoCloseable {
    public static final String NAME = "ui-theme";

    private final Path cascaraDir = Paths.get(System.getProperty("user.home")).resolve(".cascara");
    private final Path themesDir = cascaraDir.resolve("themes");
    private final Path packagesDir = cascaraDir.resolve("packages");
    private FileWatcher themesWatcher;

    private final ObservableSet<StringOption> cascThemeSet = FXCollections.observableSet();
    private SetProperty<StringOption> cascThemes = new SimpleSetProperty<>(cascThemeSet);
    private final StringOption defaultTheme = new SimpleStringOption("default", "Default", "title.default");

    private final ObservableSet<StringOption> vsixThemeSet = FXCollections.observableSet();
    private final SetProperty<StringOption> vsixThemes = new SimpleSetProperty<>(vsixThemeSet);

    private ObjectProperty<Option> activeOption;

    public ThemeOptionProvider() {
        super(NAME, null, null, null);
    }

    @Override
    public void initialize() {
        try {
            this.activeOption = ThemeEngine.activeThemeOptionProperty();
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
        } catch (Exception e) {
            throw new LocalizableRuntimeException(e, UiDiagnosticCode.OPTION_PROVIDER_INIT_ERROR, getName(), e.getLocalizedMessage());
        }
    }

    @Override
    public void close() {
        themesWatcher.clear();
    }

    @Override
    public Option getActiveOption(Map<String,Property<?>> contextData, String parameter) {
        return activeOption.get();
    }

    @Override
    public List<Option> getOptions(Map<String,Property<?>> contextData, String parameter) {
        List<Option> result = new ArrayList<>();
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
                    CascaraTheme theme = ThemeEngine.getTheme(themeId);
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
                    CascaraTheme theme = ThemeEngine.getTheme(themeId);
                    String themeName = theme.getName();
                    SimpleStringOption option = new SimpleStringOption(themeId, themeName);
                    vsixThemes.add(option);
                }
            }
        }
    }
}
