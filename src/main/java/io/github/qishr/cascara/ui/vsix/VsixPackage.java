package io.github.qishr.cascara.ui.vsix;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.*;

import io.github.qishr.cascara.common.util.ArchiveFile;
import io.github.qishr.cascara.common.io.IOUtils;
import io.github.qishr.cascara.common.util.Properties;
import io.github.qishr.cascara.common.content.ResourceContent;
import io.github.qishr.cascara.common.lang.ast.ScalarAstNode;
import io.github.qishr.cascara.common.lang.exception.ParserException;
import io.github.qishr.cascara.lang.json.processor.JsonParser;
import io.github.qishr.cascara.lang.json.ast.JsonMapEntryNode;
import io.github.qishr.cascara.lang.json.ast.JsonMapNode;
import io.github.qishr.cascara.lang.json.ast.JsonNode;
import io.github.qishr.cascara.lang.json.ast.JsonSequenceNode;
import io.github.qishr.cascara.lang.xml.processor.XmlParser;
import io.github.qishr.cascara.ui.data.UiDataException;
import io.github.qishr.cascara.lang.xml.ast.XmlNode;

public class VsixPackage extends ArchiveFile { // implements Importable {
    private Properties properties = new Properties();
    private Properties manifest = new Properties();
    private List<String> categories = new ArrayList<>();
    private List<VsixThemeInfo> themes = new ArrayList<>();
    private URI downloadOrigin = null;
    private URI previewUri = null;

    public static VsixPackage load(Path vsixPath) throws IOException {
        String packageInfo = new String(extractFile(vsixPath, "extension/package.json"));
        String vsixManifest = new String(extractFile(vsixPath, "extension.vsixmanifest"));
        VsixPackage vsix;
        try {
            vsix = new VsixPackage(vsixPath);
        } catch (ParserException e) {
            throw new UiDataException("Error parsing JSON: " + e.getMessage(), e);
        }
        vsix.parseManifest(vsixManifest);
        vsix.parsePackageManifest(packageInfo);
        return vsix;
    }

    private VsixPackage(Path vsixPath) {
        super(vsixPath);
    }

    public static VsixPackage fromJson(String jsonString) {
        JsonParser jsonParser = new JsonParser();
        JsonMapNode json = null;
        try {
            if (jsonParser.parse(jsonString) instanceof JsonMapNode m) {
                json = m;
            } else {
                throw new UiDataException("Error parsing JSON: Root is not a map", null);
            }
        } catch (ParserException e) {
            throw new UiDataException("Error parsing JSON: " + e.getMessage(), e);
        }

        // Use getEntries() from MappingAstNode
        io.github.qishr.cascara.lang.json.ast.JsonNode extensionFiles =
            (io.github.qishr.cascara.lang.json.ast.JsonNode) json.get("files");

        VsixPackage vsix = new VsixPackage(null);

        if (extensionFiles instanceof JsonMapNode filesMap) {
            vsix.getProperties().set("iconUri", getPropertyAsString(filesMap, "icon"));
            vsix.getProperties().set("manifestUri", getPropertyAsString(filesMap, "manifest"));
            vsix.getProperties().set("readmeUri", getPropertyAsString(filesMap, "readme"));
            vsix.getProperties().set("changelogUri", getPropertyAsString(filesMap, "changelog"));
            vsix.getProperties().set("licenseUri", getPropertyAsString(filesMap, "license"));
            vsix.getProperties().set("signatureUri", getPropertyAsString(filesMap, "signature"));
            vsix.getProperties().set("sha256uri", getPropertyAsString(filesMap, "sha256"));
            vsix.getProperties().set("publickeyUri", getPropertyAsString(filesMap, "publicKey"));
            vsix.getProperties().set("vsixmanifestUri", getPropertyAsString(filesMap, "vsixmanifest"));

            try {
                var downloadNode = filesMap.get("download");
                if (downloadNode instanceof ScalarAstNode scalar) {
                    vsix.downloadOrigin = new URI(scalar.asString());
                }
            } catch (URISyntaxException e) {
                System.err.println("Invalid download URL in package file: " + e.getMessage());
            }
        }

        vsix.getProperties().set("name", getPropertyAsString(json, "name"));
        vsix.getProperties().set("displayName", getPropertyAsString(json, "displayName"));
        //TODO: The rest

        try {
            // Get package info...
            String manifestUri = vsix.getProperties().getString("manifestUri");
            ResourceContent manifest = IOUtils.getResource(new URI(manifestUri));
            vsix.parsePackageManifest(manifest.content());
        } catch (URISyntaxException | IOException e) {
            throw new UiDataException("Error parsing VSIX from URL: " + e.getMessage(), e);
        }


        // String displayName = json.get("displayName").asText();
        return vsix;
    }

    private static String getPropertyAsString(JsonMapNode node, String name) {
        if (node == null) return "";
        var valueNode = node.get(name);
        if (valueNode instanceof ScalarAstNode scalar) {
            return scalar.asString();
        }
        return "";
    }

    private void parsePackageManifest(String jsonString) throws IOException {
        if (jsonString == null || jsonString.isBlank()) return;
        JsonParser jsonParser = new JsonParser();
        JsonMapNode json;
        try {
            JsonNode rootNode = jsonParser.parse(jsonString);
            if (rootNode instanceof JsonMapNode m) {
                json = m;
            } else {
                throw new UiDataException("Error parsing JSON: Root is not a map", null);
            }
        } catch (ParserException e) {
            throw new UiDataException("Error parsing JSON: " + e.getMessage(), e);
        }

        for (JsonMapEntryNode entry : json.getEntries()) {
            String name = entry.getKey().asString();
            if (entry.getValue() instanceof ScalarAstNode scalar) {
                String value = resolveVariables(scalar.asString());
                manifest.set(name, value);
            } else if (name.equals("categories") && entry.getValue() instanceof JsonSequenceNode seq) {
                for (var item : seq) {
                    if (item instanceof ScalarAstNode s) getCategories().add(s.asString());
                }
            } else if (name.equals("contributes") && entry.getValue() instanceof JsonMapNode contributes) {
                var themesNode = contributes.get("themes");
                if (themesNode instanceof JsonSequenceNode themesSeq) {
                    for (var themeEntry : themesSeq) {
                        if (themeEntry instanceof JsonMapNode themeMap) {
                            VsixThemeInfo themeInfo = new VsixThemeInfo();
                            for (JsonMapEntryNode propEntry : themeMap.getEntries()) {
                                String propKey = propEntry.getKey().asString();
                                if (propEntry.getValue() instanceof ScalarAstNode s) {
                                    themeInfo.getProperties().set(propKey, resolveVariables(s.asString()));
                                }
                            }
                            getThemes().add(themeInfo);
                        }
                    }
                }
            }
        }
    }

    private String resolveVariables(String value) {
        // TODO: Improve this
        if (value.startsWith("%")) {
            if (value.length() > 2) {
                String varName = value.substring(1, value.length() - 1);
                String varValue = properties.getString(varName);
                if (varValue != null) {
                    value = varValue;
                }
            }
        }
        return value;
    }

    public URI getDownloadUri() {
        return downloadOrigin;
    }

    public URI getPreviewUri() {
        return previewUri;
    }

    public String getName() {
        if (manifest.getString("name") instanceof String s) {
            return s;
        }
        return properties.getString("name");
    }

    public String getDescription() {
        if (manifest.getString("description") instanceof String s) {
            return s;
        }
        return properties.getString("description");
    }

    public Path getPath() {
        return archivePath;
    }

    public Properties getProperties() {
        return properties;
    }

    public Properties getManifest() {
        return manifest;
    }

    public String getIconUri() {
        if (manifest.getString("iconUri") instanceof String s) {
            return s;
        }
        return properties.getString("iconUri");
    }

    public String getDisplayName() {
        if (manifest.getString("displayName") instanceof String s) {
            return s;
        }
        return properties.getString("displayName");
    }

    // TODO: Other getters for properties

    public List<String> getCategories() {
        return categories;
    }

    public List<VsixThemeInfo> getThemes() {
        return themes;
    }

    private void parseManifest(String manifest) throws IOException {
        if (manifest == null || manifest.isBlank()) return;
        try {
            XmlParser xmlParser = new XmlParser();

            XmlNode xml = xmlParser.parse(manifest);
            XmlNode metadataNode = xml.getChild("Metadata");
            XmlNode iconNode = metadataNode.getChild("Icon");
            if (iconNode != null) {
                String iconPath = iconNode.getTextValue();
                properties.set("iconUri", iconPath);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new UiDataException(e.getMessage(), e);
        }
    }
}
