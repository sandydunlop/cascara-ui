package io.github.qishr.cascara.ui.api;

import io.github.qishr.cascara.ui.option.OptionProvider;
import javafx.collections.ObservableList;

public interface OptionListEditor {
    String getContentType();
    void configure (ObservableList<?> chosenTags, OptionProvider provider, String parameter);
}
