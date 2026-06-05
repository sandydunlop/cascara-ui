package io.github.qishr.cascara.ui.data;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import io.github.qishr.cascara.common.type.TypeDescriptor;
import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.ui.api.render.Renderer;
import io.github.qishr.cascara.ui.api.render.RendererFactory;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.option.OptionProviderRegistry;
import io.github.qishr.cascara.ui.render.Renderers;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ColumnMetadata extends FieldMetadata {

    private Comparator<ObservableValue<?>> comparator;
    private TypeDescriptor typeDescriptor;

    private String headerStyle = "";
    private String cellStyle = "";
    private double minWidth = -1;
    private double prefWidth = -1;
    private double maxWidth = -1;


    public ColumnMetadata(String name, String title, SchemaNode schema,
                OptionProviderRegistry optionProviderRegistry,
                List<RendererFactory<? extends Renderer>> rendererFactories,
                Callback<TableColumn<ObservableObject, Object>, TableCell<ObservableObject, Object>> cellFactory) {

        super(name, schema, optionProviderRegistry, rendererFactories);
        if (title != null) setTitle(title);
    }

    public ColumnMetadata(String name, String title, SchemaNode schema,
                OptionProviderRegistry optionProviderRegistry,
                List<RendererFactory<? extends Renderer>> rendererFactories) {

        super(name, schema, optionProviderRegistry, rendererFactories);
        if (title != null) setTitle(title);
    }

    public ColumnMetadata(String name, String title, Renderer renderer) {
        super(name, null, null, null);
        if (renderer instanceof ScalarEditorRenderer s) {
            this.setRenderers(new Renderers(null, s, null));
            this.allowEdit = true;
        } else if (renderer instanceof ScalarRenderer s) {
            this.setRenderers(new Renderers(s, null, null));
            this.allowEdit = false;
        }
        if (title != null) setTitle(title);
    }

    public ColumnMetadata(String name, String title) {
        super(name, null, null, null);
        if (title != null) setTitle(title);
    }

    public TypeDescriptor getTypeDescriptor() { return typeDescriptor; }
    public Comparator<ObservableValue<?>> getComparator() {
        if (comparator == null) {
            configureComparator();
        }
        return comparator;
    }
    public double getMinWidth() { return minWidth; }
    public double getPrefWidth() { return prefWidth; }
    public double getMaxWidth() { return maxWidth; }
    public String getCellStyle() { return cellStyle; }
    public String getHeaderStyle() { return headerStyle; }

    public ColumnMetadata setTypeDescriptor(TypeDescriptor v) { typeDescriptor = v; return this;}
    public ColumnMetadata setComparator(Comparator<ObservableValue<?>> v) { comparator = v; return this; }
    public ColumnMetadata setMinWidth(double value) { this.minWidth = value; return this; }
    public ColumnMetadata setPrefWidth(double value) { this.prefWidth = value; return this; }
    public ColumnMetadata setMaxWidth(double value) { this.maxWidth = value; return this; }
    public ColumnMetadata setCellStyle(String value) { this.cellStyle = value; return this; }
    public ColumnMetadata setHeaderStyle(String value) { this.headerStyle = value; return this; }

    private void configureComparator() {
        comparator = (ObservableValue<?> v1, ObservableValue<?> v2) -> {
            Object o1 = v1.getValue();
            Object o2 = v2.getValue();

            if (typeDescriptor != null) {
                String s1 = typeDescriptor.toText(o1);
                String s2 = typeDescriptor.toText(o2);
                return s1.compareTo(s2);
            }

            if (o1 instanceof Comparable s1 && o2 instanceof Comparable s2) {
                return s1.compareTo(s2);
            }

            return 0;
        };
    }
}
