package io.github.qishr.cascara.ui.theme;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.common.util.Property;

public class CodeTokenCategory {
    String name = "";
    String color = "";
    List<String> scope = new ArrayList<>();
    List<Property> settings = new ArrayList<>();

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getScope() {
        return scope;
    }
    public void setScope(List<String> scope) {
        this.scope = scope;
    }
    public List<Property> getSettings() {
        return settings;
    }
    public void setSettings(List<Property> settings) {
        this.settings = settings;
    }

    public Property get(String name) {
        for (Property prop : settings) {
            if (prop.getName().equals(name)) {
                return prop;
            }
        }
        return null;
    }

    public void setValue(String name, String value) {
        Property prop = get(name);
        if (prop != null) {
            prop.setValue(value);
        }
    }
}
