package io.github.qishr.cascara.ui.render.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.qishr.cascara.common.diagnostic.code.GenericDiagnosticCode;
import io.github.qishr.cascara.ui.api.UiException;
import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
import io.github.qishr.cascara.ui.control.EnumOptionChooser;
import io.github.qishr.cascara.ui.control.OptionChooser;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.option.EnumOptionProvider;
import io.github.qishr.cascara.ui.option.Option;
import io.github.qishr.cascara.ui.option.OptionProvider;
import io.github.qishr.cascara.ui.render.AbstractScalarRenderer;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Labeled;

public class OptionChooserRenderer extends AbstractScalarRenderer implements ScalarEditorRenderer {
    private boolean updating = false;

    public OptionChooserRenderer() {
        super("cascara/option", null, null);
    }

    @Override
    public Node render(Labeled view, Observable data, DataProvider dataProvider, FieldMetadata meta) {
        Option initialOption = null;
        Enum<?> initialEnumValue = null;
        String initialStringValue = null;
        boolean isJvmEnum = false;

        OptionProvider optionProvider = meta.getOptionProvider();

        if (data instanceof ObjectProperty prop) {
            Object propValue = prop.get();
            if (propValue != null) {
                if (propValue instanceof Option option) {
                    initialOption = option;
                } else if (propValue instanceof String string) {
                    initialOption = extractOption(meta, string);
                } else if (propValue instanceof Enum<?> e) {
                    initialEnumValue = e;
                    isJvmEnum = true;
                }
            }
        }

        OptionChooser chooser;
        EnumOptionChooser enumChooser;

        if (optionProvider == null) {
            if (isJvmEnum) {
                enumChooser = new EnumOptionChooser(
                    meta.getSchema(),
                    initialEnumValue,
                    meta.getDataContext()
                );
            } else {
                if (initialStringValue == null && initialOption != null) {
                    initialStringValue = initialOption.getOptionId();
                }
                enumChooser = new EnumOptionChooser(
                    meta.getSchema(),
                    initialStringValue,
                    meta.getDataContext()
                );
            }
            chooser = enumChooser;
        } else {
            enumChooser = null;
            if (meta.getOptionProvider() == null) {
                throw new UiException(GenericDiagnosticCode.UNEXPECTED_NULL, "OptionProvider");
            }
            chooser = new OptionChooser(
                meta.getOptionProvider(),
                meta.getProviderParameter(),
                meta.getDataContext()
            );
        }

        ReadOnlyObjectProperty<Option> selectedItem = chooser.getSelectionModel().selectedItemProperty();
        if (data instanceof ObjectProperty<?> prop) {
            if (isJvmEnum) {
                @SuppressWarnings("unchecked")
                ObjectProperty<Enum<?>> enumProp = ((ObjectProperty<Enum<?>>)prop);
                selectedItem.addListener((obs, old, val) -> {
                    if (updating || val == null || !valueChanged(val, enumProp.getValue())) return;
                    updating = true;
                    enumProp.set(enumChooser.getEnumValue());
                    if (meta.getOnChange() != null) meta.getOnChange().run();
                    updating = false;
                });
                enumProp.addListener((obs,old,val) -> {
                    if (updating || val == null) return;
                    updating = true;
                    enumChooser.setEnumValue(val);
                    updating = false;
                });
            } else if (prop.get() instanceof Option || prop.get() == null) {
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

        // TODO: comboBox.makeSearchable();
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

    private boolean valueChanged(Option value, Enum<?> old) {
        if (value == null) return false;
        if (old == null) return true;
        if (old.toString().equals(value.getOptionId())) {
            return false;
        }
        return true;
    }

    private Option extractOption(FieldMetadata meta, String string) {
        List<Option> options;
        if (!meta.hasProviderParameter()) {
            Map<String,Property<?>> contextData = meta.getDataContext();
            if (meta.getOptionProvider() == null) {
                options = new ArrayList<>();
                EnumOptionProvider.populateEnumOptions(options, meta.getSchema(), null);
            } else {
                options = meta.getOptionProvider().getOptions(contextData, null);
            }
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