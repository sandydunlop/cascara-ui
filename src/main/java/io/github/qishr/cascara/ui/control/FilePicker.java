package io.github.qishr.cascara.ui.control;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

public class FilePicker extends HBox {
    private final TextField pathField = new TextField();
    private final Button browseButton = new Button("...");
    private final SchemaNode schema;
    private URI baseUri = null; // The URI of the document being edited

    public FilePicker(Property<String> node, String[] extensions, FieldMetadata meta) {
        this.schema = meta.getSchema();
        this.setSpacing(5);
        this.getStyleClass().add("file-picker-field");

        pathField.setPromptText("Select file...");
        HBox.setHgrow(pathField, Priority.ALWAYS);

        pathField.textProperty().addListener((obs, old, val) -> node.setValue(val));

        Object currentVal = node.getValue();
        pathField.setText(currentVal != null ? currentVal.toString() : "");

        browseButton.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            if (extensions != null && extensions.length > 0) {
                chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Supported Files", extensions)
                );
            }

            // Set initial directory based on current text if it's a valid path
            try {
                Path current = Path.of(pathField.getText());
                if (Files.exists(current)) {
                    chooser.setInitialDirectory(current.getParent().toFile());
                }
            } catch (Exception ignored) {}

            File selected = chooser.showOpenDialog(this.getScene().getWindow());
            if (selected != null) {
                boolean mustBeAbsolute = schema.getBooleanOption("absolute", false);

                if (mustBeAbsolute) {
                    pathField.setText(selected.getAbsolutePath());
                } else {
                    pathField.setText(relativize(selected));
                }
            }
        });

        this.getChildren().addAll(pathField, browseButton);
    }

    public FilePicker(Property<?> node, boolean mustBeAbsolute, String[] extensions) {
        // this.schema = node.getSchema();
        schema = null;
        this.setSpacing(5);
        this.getStyleClass().add("file-picker-field");

        pathField.setPromptText("Select file...");
        HBox.setHgrow(pathField, Priority.ALWAYS);

        // pathField.textProperty().addListener((obs, old, val) -> node.setValue(val));

        Object currentVal = node.getValue();
        pathField.setText(currentVal != null ? currentVal.toString() : "");

        browseButton.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            if (extensions != null && extensions.length > 0) {
                chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Supported Files", extensions)
                );
            }

            // Set initial directory based on current text if it's a valid path
            try {
                Path current = Path.of(pathField.getText());
                if (Files.exists(current)) {
                    chooser.setInitialDirectory(current.getParent().toFile());
                }
            } catch (Exception ignored) {}

            File selected = chooser.showOpenDialog(this.getScene().getWindow());
            if (selected != null) {
                // boolean mustBeAbsolute = schema.getBooleanOption("absolute", false);

                if (mustBeAbsolute) {
                    pathField.setText(selected.getAbsolutePath());
                } else {
                    pathField.setText(relativize(selected));
                }
            }
        });

        this.getChildren().addAll(pathField, browseButton);
    }

    public StringProperty textProperty() {
        return pathField.textProperty();
    }

    public String getText() {
        return pathField.getText();
    }

    public void setText(String text) {
        pathField.setText(text);
    }

    public void setBaseUri(URI uri) {
        this.baseUri = uri;
    }

    private String relativize(File selected) {
        if (baseUri == null || !baseUri.getScheme().equalsIgnoreCase("file")) {
            return selected.getAbsolutePath();
        }

        try {
            Path docPath = Path.of(baseUri).toAbsolutePath().getParent();
            Path selectedPath = selected.toPath().toAbsolutePath();

            // .normalize() helps resolve any ".." or "." in the paths before relativizing
            return docPath.relativize(selectedPath).normalize().toString().replace("\\", "/");
        } catch (Exception e) {
            // Fallback for different drives or invalid URIs
            return selected.getAbsolutePath();
        }
    }
}