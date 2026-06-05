package io.github.qishr.cascara.ui.theme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.qishr.cascara.ui.option.AbstractObservableOptionProvider;
import io.github.qishr.cascara.ui.option.Option;

import javafx.beans.property.Property;

public class VariationOptionProvider extends AbstractObservableOptionProvider {
    @Override
    public List<? extends Option> getOptions(Map<String,Property<?>> dataContext, String parameter) {
        List<Option> optionList = new ArrayList<>();
        Property<?> themeProperty = dataContext.get("theme");
        if (themeProperty != null && themeProperty.getValue() instanceof String themeId) {
            CascaraTheme theme = ThemeEngine.instance().getTheme(themeId);
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

