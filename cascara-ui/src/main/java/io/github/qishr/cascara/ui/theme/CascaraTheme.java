package io.github.qishr.cascara.ui.theme;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.lang.yaml.ast.YamlMapNode;
import io.github.qishr.cascara.lang.yaml.ast.YamlNode;
import io.github.qishr.cascara.lang.yaml.ast.YamlSequenceNode;
import io.github.qishr.cascara.lang.yaml.processor.YamlParser;

public class CascaraTheme {
    private YamlMapNode projectYaml = null;
    private String name;
    private String themeId;
    private List<Variation> variations = new ArrayList<>();

    public CascaraTheme() {
    }

    public CascaraTheme(String yamlString) {
        loadYamlString(yamlString);

        name = projectYaml.getString("name");

        variations = new ArrayList<>();
        YamlSequenceNode seq = projectYaml.getSequence("variations");
        List<YamlNode> variationsNodes = seq.getChildren();
        for (YamlNode variationNode : variationsNodes) {
            if (variationNode instanceof YamlMapNode map) {
                ThemeVariationFactoryImpl factory = new ThemeVariationFactoryImpl();
                Variation variation = ThemeVariationLoader.load(map, factory);
                variations.add(variation);
            }
        }
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String path) {
        this.themeId = path;
    }

    public List<Variation> getVariations() {
        return variations;
    }

    public Variation getVariation(String variationId) {
        for (Variation variation : variations) {
            if (variationId.equals(variation.getName())) {
                return variation;
            }
        }
        return null;
    }

    protected void loadYamlString(String content) {
        YamlParser parser = new YamlParser();
        if (parser.parse(content) instanceof YamlMapNode map) {
            this.projectYaml = map;
        } else {
            throw new RuntimeException("Expected map as root of project YAML"); // TODO: custom exception type
        }
    }
}
