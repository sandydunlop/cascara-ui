package io.github.qishr.cascara.ui.theme;

import java.util.Map;

import io.github.qishr.cascara.ui.color.ColorDefinition;
import io.github.qishr.cascara.ui.option.Option;

public interface Variation extends Option {
    public String getName();
    public void setName(String name);
    public String getPath();
    public void setPath(String path);
    public Map<String, ColorDefinition> getBaseColors();
    public ColorDefinition getBaseColor(String id);
    public void setBaseColor(String id, String hexColor);
    public Map<String, ColorDefinition> getTransformDefinitions();
    public ColorDefinition getTransformDefinition(String id);
    public void setTransformDefinition(String id, String transform);
    public Map<String, ColorDefinition> getPaletteColors();
    public ColorDefinition getPaletteColor(String id);
    public void setPaletteColor(String id, ColorDefinition definition);
    public void setPaletteColor(String id, String hexColor);
    public Map<String, ColorDefinition> getUiColors();
    public ColorDefinition getUiColor(String id);
    public void setUiColor(String id, String hexColor);
    public Map<String, ColorDefinition> getCodeColors();
    public ColorDefinition getCodeColor(String id);
    public void setCodeColor(String id, String hexColor);
    public Map<String, ColorDefinition> getGroup(String groupId);
}
