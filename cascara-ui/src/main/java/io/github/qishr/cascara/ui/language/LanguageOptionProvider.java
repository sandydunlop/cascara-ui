package io.github.qishr.cascara.ui.language;

import java.util.List;
import java.util.Map;

import io.github.qishr.cascara.ui.option.AbstractOptionProvider;
import io.github.qishr.cascara.ui.option.Option;

import javafx.beans.property.Property;
import javafx.collections.ListChangeListener;

public class LanguageOptionProvider extends AbstractOptionProvider {
    public static final String NAME = "ui-language";

    // private UiLocalizer localizer;
    private ObservableLocalizer localizer;

    public LanguageOptionProvider() {
        // TODO: Implement one of these for flags
        super(NAME, null, null, null);
    }

    @Override
    public void initialize() {
        try {
            this.localizer = Localization.getLocalizer();
            localizer.getLanguageOptions().addListener((ListChangeListener<? super Option>)c -> {
                listeners.forEach(Runnable::run);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Option getActiveOption(Map<String,Property<?>> contextData, String parameter) {
        if (localizer == null) initialize();
        return localizer.activeLanguageOptionProperty().get();
    }

    @Override
    public List<Option> getOptions(Map<String,Property<?>> contextData, String parameter) {
        if (localizer == null) initialize();
        return localizer.getLanguageOptions();
    }
}