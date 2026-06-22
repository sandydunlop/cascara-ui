package io.github.qishr.cascara.ui.theme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.qishr.cascara.ui.option.AbstractOptionProvider;
import io.github.qishr.cascara.ui.option.Option;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;

public class VariationOptionProvider extends AbstractOptionProvider {
    public static final String NAME = "ui-theme-variation";

    private ObjectProperty<Option> activeOption;

    public VariationOptionProvider() {
        super(NAME, null, null, null);
    }

    @Override
    public void initialize() {
        this.activeOption = ThemeEngine.activeVariationOptionProperty();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Option getActiveOption(Map<String,Property<?>> contextData, String parameter) {
        return activeOption.get();
    }

    @Override
    public List<Option> getOptions(Map<String,Property<?>> dataContext, String parameter) {
        List<Option> optionList = new ArrayList<>();
        Property<?> themeProperty = dataContext.get("theme");
        if (themeProperty != null && themeProperty.getValue() instanceof String themeId) {
            CascaraTheme theme = ThemeEngine.getTheme(themeId);
            if (theme != null) {
                for (Variation variation : theme.getVariations()) {
                    if (variation instanceof Option option) {
                        optionList.add(option);
                    }
                }
            }
        }
        return optionList;
    }
}

