package io.github.qishr.cascara.ui.form;

import java.net.URI;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;

import io.github.qishr.cascara.common.service.ServiceProviderLayer;
import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.render.RenderDispatcher;
import io.github.qishr.cascara.ui.render.RendererAllocator;
import io.github.qishr.cascara.ui.render.RendererFactory;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.application.Platform;
import javafx.beans.Observable;

public abstract class AbstractFieldFactory {
    protected URI uri;
    protected boolean isUpdatingControl = false;
    protected boolean isUpdatingProperty = false;
    private final Map<Object, ViewAndControl> viewCache = new WeakHashMap<>();

    protected Runnable onRefreshForm;
    protected Consumer<Boolean> onChangeFieldValue;

    protected DataProvider dataProvider;

    protected final RendererFactory rendererFactory;
    protected final RendererAllocator rendererAllocator;

    protected AbstractFieldFactory(ServiceProviderLayer spl) {
        if (spl == null) {
            spl = ServiceProviderLayer.getRootLayer();
        }
        rendererFactory = new RendererFactory(spl);
        rendererAllocator = new RendererAllocator(rendererFactory);
    }

    public URI getUri() { return uri; }
    public void setUri(URI uri) { this.uri = uri; }
    public void setDataProvider(DataProvider dataProvider) { this.dataProvider = dataProvider; }
    public void setOnChangeFieldValue(Consumer<Boolean> handler) { this.onChangeFieldValue = handler; }
    public void setOnRefreshForm(Runnable handler) { this.onRefreshForm = handler; }


    protected ViewAndControl createControl(FieldMetadata meta, Observable data) {
        String key = meta.getName() + data;
        if (viewCache.containsKey(key)) {
            return viewCache.get(key);
        }
        if (meta.isStringField() && !meta.hasOptionProvider()) {
            meta.setOnChange(() -> notifyDocumentChanged(false));
        } else {
            meta.setOnChange(() -> notifyDocumentChanged(true));
        }

        Label view = new Label();
        Node control = RenderDispatcher.render(view, data, dataProvider, meta);
        ViewAndControl viewAndControl = new ViewAndControl(view, control);
        viewCache.put(key, viewAndControl);

        // Non-scalar controls can take up more space...
        if (data instanceof ObservableList) {
            Platform.runLater(() -> {
                view.setMaxWidth(Double.POSITIVE_INFINITY);
                HBox.setHgrow(view, Priority.ALWAYS);
            });
        }

        return viewAndControl;
    }

    protected void notifyDocumentChanged(boolean urgent) {
        if (onChangeFieldValue != null) {
            onChangeFieldValue.accept(urgent);
        }
    }
}
