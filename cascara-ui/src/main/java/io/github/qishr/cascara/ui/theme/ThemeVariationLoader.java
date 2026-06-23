package io.github.qishr.cascara.ui.theme;

import io.github.qishr.cascara.lang.yaml.ast.YamlMapEntryNode;
import io.github.qishr.cascara.lang.yaml.ast.YamlMapNode;
import io.github.qishr.cascara.lang.yaml.ast.YamlNode;
import io.github.qishr.cascara.lang.yaml.ast.YamlScalarNode;
import io.github.qishr.cascara.ui.color.ColorDefinition;

public class ThemeVariationLoader {
    public static Variation load(YamlMapNode variationNode, ThemeVariationFactory factory) {
        Variation variation = factory.createVariation();
        variation.setName(variationNode.getString("name"));
        variation.setPath(variationNode.getString("path"));

        if (variationNode.getMap("baseColors") instanceof YamlMapNode map) {
            for (YamlMapEntryNode entry : map.getEntries()) {
                ColorDefinition cd = loadDefinition(entry);
                variation.getBaseColors().put(cd.getId(), cd);
            }
        }

        if (variationNode.getMap("transforms") instanceof YamlMapNode map) {
            for (YamlMapEntryNode entry : map.getEntries()) {
                ColorDefinition cd = loadDefinition(entry);
                variation.getTransformDefinitions().put(cd.getId(), cd);
            }
        }

        if (variationNode.getMap("paletteColors") instanceof YamlMapNode map) {
            for (YamlMapEntryNode entry : map.getEntries()) {
                ColorDefinition cd = loadDefinition(entry);
                variation.getPaletteColors().put(cd.getId(), cd);
            }
        }

        if (variationNode.getMap("uiColors") instanceof YamlMapNode map) {
            for (YamlMapEntryNode entry : map.getEntries()) {
                ColorDefinition cd = loadDefinition(entry);
                variation.getUiColors().put(cd.getId(), cd);
            }
        }

        if (variationNode.getMap("codeColors") instanceof YamlMapNode map) {
            for (YamlMapEntryNode entry : map.getEntries()) {
                ColorDefinition cd = loadDefinition(entry);
                variation.getCodeColors().put(cd.getId(), cd);
            }
        }

        return variation;
    }

    private static ColorDefinition loadDefinition(YamlMapEntryNode entry) {
        if (!(entry.getKey() instanceof YamlScalarNode key)) {
            return null;
        }
        String id = key.asString();
        YamlNode valueNode = entry.getValue();
        if (valueNode instanceof YamlMapNode map) {
            ColorDefinition colordef = new ColorDefinition();
            colordef.setId(nonNull(id));
            colordef.setName(nonNull(map.getString("name")));
            colordef.setHexColor(nonNull(map.getString("hexColor")));
            colordef.setLeftHexColor(nonNull(map.getString("leftColor")));
            colordef.setRightHexColor(nonNull(map.getString("rightColor")));
            colordef.setLerp(nonNull(map.getString("lerp")));
            colordef.setTransformDefinition(nonNull(map.getString("transformDefinition")));
            colordef.setTransformId(nonNull(map.getString("transformId")));
            colordef.setBaseColorId(nonNull(map.getString("baseColorId")));
            colordef.setPaletteColorId(nonNull(map.getString("paletteColorId")));
            return colordef;
        } else {
            return null;
        }
    }

    private static String nonNull(String s) {
        return s == null ? "": s;
    }
}
