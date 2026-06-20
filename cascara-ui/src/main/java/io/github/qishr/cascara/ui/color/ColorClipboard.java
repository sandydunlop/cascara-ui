package io.github.qishr.cascara.ui.color;

import io.github.qishr.cascara.lang.yaml.exception.YamlSerializerException;
import io.github.qishr.cascara.lang.yaml.processor.YamlSerializer;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class ColorClipboard {
    private static YamlSerializer serializer = new YamlSerializer();

    private ColorClipboard() {
        // No public constructor
    }

    private static YamlSerializer getSerializer() {
        if (serializer == null) {
            serializer = new YamlSerializer();
        }
        return serializer;
    }

    public static void put(ColorDefinition definition) {
        // YamlSerializerImpl serializer = new YamlSerializerImpl();
        String yaml;
        try {
            yaml = getSerializer().toText(definition);
        } catch (YamlSerializerException e) {
            System.err.println("YamlSerializerException: " + e.getMessage());
            return;
        }
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.put(ColorDefinition.DATA_FORMAT, yaml);
        content.putString(definition.getHexColor().toUpperCase());
        clipboard.setContent(content);
    }

    public static ColorDefinition get() {
        // YamlSerializerImpl serializer = new YamlSerializerImpl();
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasContent(ColorDefinition.DATA_FORMAT)) {
            String yaml = (String) clipboard.getContent(ColorDefinition.DATA_FORMAT);
            try {
                return getSerializer().fromText(yaml, ColorDefinition.class);
            } catch (YamlSerializerException e) {
                System.err.println("YamlSerializerException: " + e.getMessage());
                return null;
            }
        } else if (clipboard.hasString()) {
            String clipText = clipboard.getString().trim().toUpperCase();
            if (clipText.matches("#?[0-9A-F]{6}")) {
                try {
                    String hex = clipText.startsWith("#") ? clipText.substring(1) : clipText;
                    return new ColorDefinition(hex);
                } catch (IllegalArgumentException e) {
                    System.err.println("IllegalArgumentException: " + e.getMessage());
                    return null;
                }
            }
        }
        return null;
    }
}
