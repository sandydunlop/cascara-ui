package io.github.qishr.cascara.ui.control;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.option.ObservableOptionProvider;
import io.github.qishr.cascara.ui.option.Option;
import io.github.qishr.cascara.ui.option.OptionProvider;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListCell;

public class OptionChooser extends ComboBox<Option> {
    private final ScalarRenderer renderer;
    private final Map<String,Property<?>> dataContext;
    private final OptionProvider provider;
    private final String providerParameter;
    private final Runnable updateHook = this::refreshItems;
    private List<? extends Option> cachedOptions = List.of();
    private String initialId;

    public OptionChooser(OptionProvider provider) {
        this (null, provider, null, null, null);
    }

    public OptionChooser(OptionProvider provider, Option initialValue) {
        this (null, provider, null, initialValue, null);
    }

    public OptionChooser(ScalarRenderer renderer, OptionProvider provider, String providerParameter, Option initialValue) {
        this (renderer, provider, providerParameter, initialValue, null);
    }

    public OptionChooser(ScalarRenderer renderer, OptionProvider provider, String providerParameter, Option initialValue, Map<String,Property<?>> context) {
        this.renderer = renderer;
        this.provider = provider;
        this.providerParameter = providerParameter;
        this.dataContext = context;
        setup(initialValue);
    }

    private void setup(Option initialValue) {
        initialId = initialValue == null ? null : initialValue.getOptionId();

        // For items in the expanded list
        this.setCellFactory(lv -> createListCell());

        // For the item in the unexpanded list (i.e. the "button")
        this.setButtonCell(createListCell());

        loadItems();

        // React to changes in the option list
        if (provider instanceof ObservableOptionProvider observable) {
            observable.addListener(updateHook);
        }

        // TODO: Fix this...

        // Data Listeners (e.g., 'canonicalId' sibling changed)
        // We listen to the parent because siblings are part of the same parent map
        if (dataContext != null) {
            for (Entry<String,Property<?>> entry : dataContext.entrySet()) {
                entry.getValue().addListener((obs, old, val) -> {
                    refreshItems();
                });
            }

            // if (dataNode.getParent() != null) {
            //     dataNode.getParent().addChangeListener(this::refreshItems);
            // }

            // this.sceneProperty().addListener((obs, oldScene, newScene) -> {
            //     if (newScene == null) {
            //         // Cleanup Global Provider listener
            //         if (provider instanceof ObservableOptionProvider obsProvider) {
            //             obsProvider.removeListener(updateHook);
            //         }
            //         // Cleanup Local Data listener
            //         if (dataNode.getParent() != null) {
            //             dataNode.getParent().removeChangeListener(this::refreshItems);
            //         }
            //     }
            // });
        }

    //     // if (dataContext != null) {

    //     // }
    }

    private List<? extends Option> fetchCurrentOptions() {
        List<? extends Option> options = List.of();
        if (provider != null) {
            options = provider.getOptions(dataContext, providerParameter);
        }
        return options;
    }

    private void refreshItems() {
        // Since loadItems is called during construction, it can't use
        // runLater or it will trigger an update event as soon as the
        // control is created, so we wrap it here for use after the
        // initial load since subsequent calls may not be from the
        // UI thread.
        Platform.runLater(() -> {
            loadItems();
        });
    }

    private void loadItems() {
        String currentId = initialId;
        if (getValue() instanceof Option option) {
            currentId = option.getOptionId();
        }

        this.cachedOptions = fetchCurrentOptions();
        this.getItems().setAll(cachedOptions);

        // Re-trigger selection to force ButtonCell update
        Option current = find(currentId);
        if (current != null && cachedOptions.contains(current)) {
            this.setValue(current);
        } else {
            this.setValue(null);
        }
    }

    private Option find(String id) {
        for (Option option : cachedOptions) {
            if (option.getOptionId().equals(id)) {
                return option;
            }
        }
        return null;
    }

    private ListCell<Option> createListCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(Option option, boolean empty) {
                super.updateItem(option, empty);
                if (!empty) {
                    if (option != null) {
                        renderOption(this, option);
                    }
                }
            }
        };
    }

    private void renderOption(Labeled view, Option option) {
        if (renderer == null) {
            view.setText(option.getOptionText());
            view.setGraphic(null);
        } else {
            renderer.render(view, option, null, null);
        }
    }

    // TODO: Fix this
    public void makeSearchable() {
        // this.setEditable(true);
        // TextField editor = this.getEditor();
        // editor.textProperty().addListener((obs, oldVal, newVal) -> {
        //     List<? extends Option> currentOptions = fetchCurrentOptions();

        //     if (newVal == null || newVal.isEmpty()) {
        //         this.getItems().setAll(currentOptions.stream().map(Option::getOptionId).toList());
        //     } else {
        //         String filter = newVal.toLowerCase();
        //         List<String> filteredIds = currentOptions.stream()
        //             .filter(o -> o.getOptionText().toLowerCase().contains(filter))
        //             .map(Option::getOptionId)
        //             .toList();
        //         this.getItems().setAll(filteredIds);
        //     }
        //     this.show();
        // });
    }
}