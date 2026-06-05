package io.github.qishr.cascara.ui.render.control;

import java.util.List;
import java.util.Map;

import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.control.OptionChooser;
import io.github.qishr.cascara.ui.data.UiDataException;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.option.Option;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Labeled;

public class OptionChooserRenderer extends AbstractScalarRenderer implements ScalarEditorRenderer {
    private boolean updating = false;

    @Override
    public String getContentType() { return "cascara/option"; }

    @Override
    public String getSchemaType() { return null; }

    @Override
    public String getSchemaFormat() { return null; }

    @Override
    public Node render(Labeled view, Observable data, DataProvider dataProvider, FieldMetadata meta) {
        Option initialValue = null;

        if (meta.getOptionProvider() == null) {
            throw new UiDataException("OptionProvider must not be null");
        }

        if (data instanceof ObjectProperty prop) {
            if (prop.get() instanceof Option option) {
                initialValue = option;
            } else if (prop.get() instanceof String string) {
                initialValue = extractOption(meta, string);
            }
        }

        OptionChooser chooser = new OptionChooser(
            meta.getRenderers().getScalarRenderer(), // This is for the individual options
            meta.getOptionProvider(),
            meta.getProviderParameter(),
            initialValue,
            meta.getDataContext()
        );

        ReadOnlyObjectProperty<Option> selectedItem = chooser.getSelectionModel().selectedItemProperty();
        if (data instanceof ObjectProperty<?> prop) {
            if (prop.get() instanceof Option || prop.get() == null) {
                @SuppressWarnings("unchecked")
                ObjectProperty<Option> optionProp = ((ObjectProperty<Option>)prop);
                selectedItem.addListener((obs, old, val) -> {
                    if (updating || val == null || !valueChanged(val, optionProp)) return;
                    updating = true;
                    optionProp.set(val);
                    if (meta.getOnChange() != null) meta.getOnChange().run();
                    updating = false;
                });
                optionProp.addListener((obs,old,val) -> {
                    if (updating || val == null) return;
                    updating = true;
                    chooser.setValue(val);
                    updating = false;
                });
            } else if (prop.get() instanceof String) {
                @SuppressWarnings("unchecked")
                ObjectProperty<String> stringProp = ((ObjectProperty<String>)prop);
                selectedItem.addListener((obs, old, val) -> {
                    if (updating || val == null || !valueChanged(val, stringProp.getValue())) return;
                    updating = true;
                    stringProp.set(val.getOptionId());
                    if (meta.getOnChange() != null) meta.getOnChange().run();
                    updating = false;
                });
                stringProp.addListener((obs,old,val) -> {
                    if (updating || val == null) return;
                    updating = true;
                    chooser.setValue(extractOption(meta, val));
                    updating = false;
                });
            }
        }

        // comboBox.makeSearchable();
        chooser.setMaxWidth(Double.MAX_VALUE);
        view.setGraphic(chooser);
        view.setText(null);
        return chooser;
    }

    private boolean valueChanged(Option value, Property<Option> old) {
        if (value == null) return false;
        if (old.getValue() == null) return true;
        if (old.getValue().getOptionId().equals(value.getOptionId())) {
            return false;
        }
        return true;
    }

    private boolean valueChanged(Option value, String old) {
        if (value == null) return false;
        if (old == null) return true;
        if (old.equals(value.getOptionId())) {
            return false;
        }
        return true;
    }

    private Option extractOption(FieldMetadata meta, String string) {
        // String initialString = String.valueOf(observableNode.getValue());
        List<? extends Option> options;
        if (!meta.hasProviderParameter()) {
            // AstNode contextNode = (observableNode.getParent() != null) ? observableNode.getParent().getRawAstNode() : null;
            // options = meta.getOptionProvider().getOptions(meta.getSchema(), contextNode, null);

            // Map<String,Property<?>> contextData = extractContextData(observableNode);
            Map<String,Property<?>> contextData = meta.getDataContext();

            options = meta.getOptionProvider().getOptions(contextData, null);
            // meta.setContextNode(contextNode);
        } else {
            options = meta.getOptionProvider().getOptions(null, meta.getProviderParameter());
        }
        for (Option option : options) {
            if (option.getOptionText().equals(string)) {
                return option;
            }
            if (option.getOptionId().equals(string)) {
                return option;
            }
        }
        return null;
    }
}