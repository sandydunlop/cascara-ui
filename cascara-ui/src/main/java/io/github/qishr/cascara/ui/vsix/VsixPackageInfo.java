package io.github.qishr.cascara.ui.vsix;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.common.util.Properties;

public class VsixPackageInfo {
    private String name;
    private String displayName;
    private Path path;

    private Properties properties = new Properties();
    private List<String> categories = new ArrayList<>();
    private List<VsixThemeInfo> themes = new ArrayList<>();

    public VsixPackageInfo(Path path, String name, String displayName) {
        this.path = path;
        this.name = name;
        this.displayName = displayName;
    }

    public Properties getProperties() {
        return properties;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<VsixThemeInfo> getThemes() {
        return themes;
    }

    public String getName() {
        return name;
    }
    public String getDisplayName() {
        return displayName;
    }

    public Path getPath() { return path; }
}
