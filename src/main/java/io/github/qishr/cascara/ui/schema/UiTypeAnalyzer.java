package io.github.qishr.cascara.ui.schema;

import java.lang.reflect.Field;

import io.github.qishr.cascara.common.lang.reference.ReferenceMapNode;
import io.github.qishr.cascara.common.lang.reference.ReferenceScalarNode;
import io.github.qishr.cascara.common.lang.reference.ReferenceSequenceNode;
import io.github.qishr.cascara.schema.util.TypeAnalyzer;

public class UiTypeAnalyzer implements TypeAnalyzer {
    private static final String ABSOLUTE = "absolute";
    private static final String EXTENSIONS = "extensions";
    private static final String FORMAT = "format";
    // private static final String NAME = "name";


    @Override
    public void analyze(Field field, ReferenceMapNode node) {

        if (field.isAnnotationPresent(FileConstraint.class)) {
            FileConstraint anno = field.getAnnotation(FileConstraint.class);

            // TODO: java.nio.Path? Where is this used?
            // Do we differentiate between paths and URIs here?
            node.put(FORMAT, scalar("path"));

            node.put(ABSOLUTE, scalar(anno.absolute()));

            // cascara://organizer/CASC-00028C57
            // TODO: initialDirectory, mustExist

            if (anno.extensions().length > 0) {
                ReferenceSequenceNode extNode = new ReferenceSequenceNode();
                for (String ext : anno.extensions()) extNode.add(scalar(ext));
                node.put(EXTENSIONS, extNode);
            }
        }

        if (field.isAnnotationPresent(OptionConstraint.class)) {
            OptionConstraint anno = field.getAnnotation(OptionConstraint.class);
            ReferenceMapNode optionMeta = new ReferenceMapNode();
            optionMeta.put(OptionConstraint.NAME, scalar(anno.provider()));
            optionMeta.put(OptionConstraint.PARAMETER, scalar(anno.parameter()));
            node.put(OptionConstraint.UI_OPTION_PROVIDER, optionMeta);
        }

        if (field.isAnnotationPresent(DisplayToggle.class)) {
            DisplayToggle anno = field.getAnnotation(DisplayToggle.class);
            node.put(DisplayToggle.UI_DISPLAY_TOGGLE, scalar(anno.value()));

        }

        if (field.isAnnotationPresent(Hidden.class)) {
            node.put(Hidden.UI_HIDDEN, scalar(true));
        }
    }

    @Override
    public void analyze(Class<?> clazz, ReferenceMapNode node) {
    }

    private ReferenceScalarNode scalar(Object value) {
        return new ReferenceScalarNode(value);
    }
}