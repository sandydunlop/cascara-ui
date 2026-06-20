package io.github.qishr.cascara.ui.render.control;

import java.net.URI;

import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Labeled;

public class UriRenderer extends AbstractScalarRenderer implements ScalarRenderer {
    public UriRenderer() {
        super(null, SchemaType.STRING, "uri");
    }

    @Override
    public Node render(Labeled view, Object data, DataProvider dataProvider, FieldMetadata meta) {
        if (data instanceof String text) {
            Hyperlink link = new Hyperlink(text);
            URI uri = URI.create(text);
            link.setOnMouseClicked(mouse -> {
                System.out.println("Unimplemented: URI renderer: " + uri);
                // try {
                //     appCtx.getDocumentService().openDocument(uri, true);
                // } catch (DocumentException e) {
                //     dialogService.showErrorMessage("Failed to open document: " + uri, e.getMessage(), e);
                // }
            });

            view.setGraphic(null);
            view.setText(null);
        }
        return null;
    }
}