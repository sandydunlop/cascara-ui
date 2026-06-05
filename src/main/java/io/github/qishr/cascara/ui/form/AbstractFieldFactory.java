package io.github.qishr.cascara.ui.form;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;

import io.github.qishr.cascara.common.service.ServiceProviderLayer;
import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.Renderer;
import io.github.qishr.cascara.ui.api.render.RendererFactory;
import io.github.qishr.cascara.ui.option.OptionProviderRegistry;
import io.github.qishr.cascara.ui.render.RenderDispatcher;
import io.github.qishr.cascara.ui.render.factory.SpiArrayEditorRendererFactory;
import io.github.qishr.cascara.ui.render.factory.SpiScalarEditorRendererFactory;
import io.github.qishr.cascara.ui.render.factory.SpiScalarRendererFactory;
import io.github.qishr.cascara.ui.render.factory.StandardScalarEditorRendererFactory;
import io.github.qishr.cascara.ui.render.factory.StandardScalarRendererFactory;

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
    protected OptionProviderRegistry optionProviderRegistry;
    protected List<RendererFactory<? extends Renderer>> rendererFactories = new ArrayList<>();

    protected AbstractFieldFactory(ServiceProviderLayer providerLayer) {
        if (providerLayer == null) {
            providerLayer = ServiceProviderLayer.getRootLayer();
        }
        rendererFactories.add(new SpiArrayEditorRendererFactory(providerLayer));
        rendererFactories.add(new SpiScalarRendererFactory(providerLayer));
        rendererFactories.add(new SpiScalarEditorRendererFactory(providerLayer));
        rendererFactories.add(new StandardScalarRendererFactory());
        rendererFactories.add(new StandardScalarEditorRendererFactory());
    }

    public URI getUri() { return uri; }
    public void setUri(URI uri) { this.uri = uri; }
    public void setDataProvider(DataProvider dataProvider) { this.dataProvider = dataProvider; }
    public void setOnChangeFieldValue(Consumer<Boolean> handler) { this.onChangeFieldValue = handler; }
    public void setOnRefreshForm(Runnable handler) { this.onRefreshForm = handler; }
    public void setOptionProviderRegistry(OptionProviderRegistry dataProvider) { this.optionProviderRegistry = dataProvider; }


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
