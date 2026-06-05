package io.github.qishr.cascara.ui.render.control;

import java.net.URI;
import java.nio.file.Path;

import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.control.FilePicker;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Labeled;

public class FileChooserRenderer extends AbstractScalarRenderer implements ScalarEditorRenderer {
    private boolean isUpdatingControl;

    @Override
    public String getContentType() { return null; }

    @Override
    public String getSchemaType() { return "string"; }

    @Override
    public String getSchemaFormat() { return "path"; }

    @Override
    public Node render(Labeled view, Observable data, DataProvider dataProvider, FieldMetadata meta) {
        if (data instanceof ObjectProperty obj) {
            Node node = createPathField(obj, meta);
            if (node != null) {
                view.setGraphic(node);
            }
            view.setText(null);
            return node;
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    protected Node createPathField(@SuppressWarnings("rawtypes") ObjectProperty property, FieldMetadata meta) {
        SchemaNode schema = meta.getSchema();
        String[] extensions = extractExtensions(schema);
        FilePicker filePicker = new FilePicker(property, schema.getBooleanOption("absolute", false), extensions);

        URI uri;


        try {
            uri = URI.create(String.valueOf(extractString(property)));
        } catch(Exception e) {
            Path path = Path.of(extractString(property));
            uri = path.toUri();
        }

        filePicker.setBaseUri(uri);

        filePicker.textProperty().addListener((obs,old,val) -> {
            if (isUpdatingControl) return;
            property.setValue(val);
            if (meta.getOnChange() != null) meta.getOnChange().run();
        });
        property.addListener((obs,old,val) -> {
            isUpdatingControl = true;
            filePicker.setText(String.valueOf(val));
            isUpdatingControl = false;
        });
        return filePicker;
    }

    //
    // Helpers
    //

    private String[] extractExtensions(SchemaNode schema) {

        // TODO: There is no FileExtensionRule any more.
        // This info should now come from the type analyzer via FieldMetadata

        // Iterate through rules and find our specific implementation
        // for (ValidationRule rule : schema.getRules()) {
        //     if (rule instanceof FileExtensionRule fer) {
        //         return fer.getExtensions();
        //     }
        // }
        return new String[0];
    }
}