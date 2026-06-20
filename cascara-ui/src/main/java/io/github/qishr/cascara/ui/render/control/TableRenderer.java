package io.github.qishr.cascara.ui.render.control;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ArrayEditorRenderer;
import io.github.qishr.cascara.ui.control.CascaraTable;
import io.github.qishr.cascara.ui.data.ColumnMetadata;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.form.FieldMetadata.ColumnMeta;
import io.github.qishr.cascara.ui.option.OptionProviderRegistry;
import io.github.qishr.cascara.ui.render.AbstractArrayRenderer;
import io.github.qishr.cascara.ui.render.RendererAllocator;
import io.github.qishr.cascara.ui.render.RendererFactory;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class TableRenderer extends AbstractArrayRenderer implements ArrayEditorRenderer {
    private FieldMetadata tableMeta;

    public TableRenderer() {
        super("cascara/table-row", null, null);
    }

    @SuppressWarnings("unchecked")
	@Override
    public Node render(Labeled view, @SuppressWarnings("rawtypes") ObservableList list, DataProvider dataProvider, FieldMetadata tableMeta) {

        OptionProviderRegistry optionProviderRegistry = tableMeta.getOptionProviderRegistry();
        RendererFactory rendererFactory = tableMeta.getRendererFactory();
        RendererAllocator rendererAllocator = new RendererAllocator(rendererFactory);
        this.tableMeta = tableMeta;

        // Bind height to: (Number of Items * Row Height) + Header Height + Border
        int addButtonSize = tableMeta.allowAdd() ? 48 : 0;
        double rowHeight = 32.0;

        CascaraTable table = new CascaraTable();
        table.setFixedCellSize(rowHeight);
        table.prefHeightProperty().bind(
            // Bindings.size(FXCollections.observableList(arrayNode.getChildren()))
            Bindings.size(list)
                .multiply(table.fixedCellSizeProperty())
                .add(35 + addButtonSize) // Header
                .add(2)  // Border
        );
        table.setMinHeight(Region.USE_PREF_SIZE);
        table.setMaxHeight(Region.USE_PREF_SIZE);

        // Convert Schema Properties to Table Columns
        List<ColumnMetadata> columns = new ArrayList<>();
        for (ColumnMeta entry : tableMeta.getColumnMetaList()) {

            SchemaNode columnSchema = entry.getSchema();
            String columnName = entry.getName();
            String columnTitle = columnSchema.getTitle();
            if (columnTitle == null || columnTitle.isEmpty()) { columnTitle = columnName; }

            // Hidden columns (TODO: Make this optional)
            boolean isIdField = columnName.toLowerCase().equals("id");

            ColumnMetadata columnMeta = new ColumnMetadata(columnName, columnTitle, columnSchema, optionProviderRegistry, rendererFactory);
            columnMeta.setRenderers(rendererAllocator.allocate(columnMeta));

            if (columnMeta.isHidden() || isIdField) {
                continue;
            }

            if (!tableMeta.allowEdit()) {
                columnMeta.setAllowEdit(false);
            }

            columnMeta.setOnChange(() -> notifyDocumentChanged());

            if (columnSchema.getType() == SchemaType.BOOLEAN) {
                // Boolean columns don't need much room
                columnMeta.setMinWidth(50);
                columnMeta.setMaxWidth(250);
                columnMeta.setPrefWidth(50);
            } else {
                // Text/Object columns should grow
                columnMeta.setMinWidth(20);
                columnMeta.setPrefWidth(250);
            }

            columns.add(columnMeta);
        }

        VBox layout = new VBox(5);
        layout.getChildren().add(table);

        layout.setMinHeight(Region.USE_PREF_SIZE);
        layout.setFillWidth(true);

        if (tableMeta.allowAdd()) {
            table.setOnAddRowClicked(() -> {
                if (tableMeta.getAddRowHandler() != null) tableMeta.getAddRowHandler().run();
            });
        }

        if (tableMeta.allowDelete()) {
            table.setOnRemoveRowClicked(row-> {
                if (tableMeta.getRemoveRowHandler() != null) tableMeta.getRemoveRowHandler().accept(row);
            });
        }

        table.setAllowEdit(tableMeta.allowEdit());

        table.setColumnMetadata(columns);
        table.setDataSource(list);
        // return layout;

        view.setGraphic(layout);
        view.setText(null);
        return table;
    }

    private void notifyDocumentChanged() {
        if (tableMeta.getOnChange() != null) {
            tableMeta.getOnChange().run();
        }
    }
}
