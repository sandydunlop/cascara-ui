package io.github.qishr.cascara.ui.option;

import java.util.List;
import java.util.Map;

import javafx.beans.property.Property;

public interface OptionProvider {
    // List<? extends Option> getOptions(SchemaNode schema, AstNode contextData, String parameter);
    List<? extends Option> getOptions(Map<String,Property<?>> contextData, String parameter);
}