package io.github.qishr.cascara.ui.render;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.qishr.cascara.common.diagnostic.GlobalReporter;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.ui.api.UiDiagnosticCode;
import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ArrayEditorRenderer;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.data.ObservableObject;
import io.github.qishr.cascara.ui.data.ObservableTreeNode;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.option.Option;
import io.github.qishr.cascara.ui.render.standard.StandardStringRenderer;

import javafx.animation.FadeTransition;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class RenderDispatcher {
    private static final Reporter REPORTER = GlobalReporter.forClass(RenderDispatcher.class);

    @SuppressWarnings("unchecked")
    public static Node render(Labeled view, Observable data, DataProvider dataProvider, FieldMetadata meta) {
        Node control = null;

        Renderers renderers = meta.getRenderers();

        if (data instanceof ObservableList list) {
            ArrayEditorRenderer renderer = renderers.getArrayEditorRenderer();
            if (renderer != null) {
                control = renderer.render(view, list, dataProvider, meta);
            } else {
                REPORTER.warn(
                    UiDiagnosticCode.NO_RENDERER_SET,
                    meta.getName(), meta.getSchemaType(), meta.getContentType()
                );
            }
        } else if (data instanceof ObjectProperty objectProperty) {
            // If the observable property has a beam and the bean is
            // a tree node, use it to get context data.
            Object bean = objectProperty.getBean();
            if (bean instanceof ObservableTreeNode treeNode) {
                meta.setDataContext(treeNode.getDataContext());
            } else if (bean instanceof ObservableObject object) {
                meta.setDataContext(object.getDataContext());
            }

            SchemaNode schema = meta.getSchema();
            Object displayString = null;

            // If this is an object and it has ui-display-string set, display whatever
            // property ui-display-string says to display.
            if (schema != null && schema.getType() == SchemaType.OBJECT) {
                displayString = meta.getSchema().getExtension("ui-display-string");
            }

            if (displayString instanceof String ds) {

                // TODO: We need to use a property instead of a string here
                view.setText(formatDisplayString(objectProperty, ds));

                // TODO: This isn't right either...
                // StandardStringRenderer renderer = new StandardStringRenderer();
                // renderer.render(view, objectProperty, dataProvider, meta);

            } else if (meta.allowEdit() && renderers.getScalarEditorRenderer() != null) {
                boolean switchable = meta.hasDisplayToggle();
                if (renderers.getScalarRenderer() != null && switchable) {
                    renderSwitchableView(view, data, dataProvider,renderers,  meta);
                } else {
                    control = renderers.getScalarEditorRenderer().render(view, data, dataProvider, meta);
                }
            }  else if (renderers.getScalarRenderer() instanceof ScalarRenderer renderer) {
                control = renderer.render(view, objectProperty.getValue(), dataProvider, meta);
            } else {
                Object value = objectProperty.getValue();
                if (value == null) {
                    view.setText(null);
                } else {
                    StandardStringRenderer renderer = new StandardStringRenderer();
                    renderer.render(view, objectProperty, dataProvider, meta);
                    // view.setText(value.toString());
                }
                view.setGraphic(null);
            }
        } else if (renderers.getScalarRenderer() != null) {
            control = renderers.getScalarRenderer().render(view, data, dataProvider, meta);
        } else if (data instanceof ObservableObject object) {
            StandardStringRenderer renderer = new StandardStringRenderer();
            renderer.render(view, object.displayStringProperty(), dataProvider, meta);
            // view.setText(object.displayStringProperty().get());
        } else if (data instanceof ObservableValue v){
            // If none of the above worked, check if it's a table data objects and extract its display value
            Object obj = v.getValue();
            String displayString = "";
            if (obj != null) {
                if (obj instanceof Option option) {
                    displayString = option.getOptionText();
                }

                // If that failed, use its toString as a last resort
                if (displayString == null || displayString.isEmpty()) {
                    displayString = String.valueOf(obj);
                }
            }

            view.setText(displayString);
            view.setGraphic(null);

            // TODO: Either use a display string method like below, or set up a listener for the option text

            // StandardStringRenderer renderer = new StandardStringRenderer();
            // renderer.render(view, object.displayStringProperty(), dataProvider, meta);

        } else {
            view.setText(data.toString());
            view.setGraphic(null);
        }
        return control;
    }

    private static String formatDisplayString(ObjectProperty<?> objectProperty, String displayStringFormat) {
        Object propValue = objectProperty.get();
        if (propValue == null) {
            return null;
        }
        if (propValue instanceof ObservableObject entity) {
            String result = displayStringFormat;
            Set<String> propNames = entity.getPropertyNames();
            for (String propName : propNames) {
                Object value = entity.get(propName);
                if (value == null) continue;

                String replacement = "";
                // if (value instanceof CemaEntity valueEntity) {
                //     replacement = getDisplayString(valueEntity, depth + 1);
                // } else if (value instanceof Collection) {
                //     // This is intentioally left blank.
                //     // It prevents infinite recursion.
                // } else {
                    replacement = value.toString();
                // }

                // Use \b (word boundary) to ensure we only match the full property name.
                // Pattern.quote ensures propNames with regex characters don't break the logic.
                String regex = "\\b" + Pattern.quote(propName) + "\\b";

                result = result.replaceAll(regex, Matcher.quoteReplacement(replacement));
            }
            return result;
        }
        return propValue.toString();
    }

    private static void renderSwitchableView(Labeled view, Observable property, DataProvider dataProvider, Renderers renderers, FieldMetadata meta) {
        StackPane stack = new StackPane();

        // 1. Create the two states
        Label viewNode = new Label();
        Label editorNode = new Label();
        renderers.getScalarRenderer().render(viewNode, property, dataProvider, meta);
        renderers.getScalarEditorRenderer().render(editorNode, property, dataProvider, meta);

        // 2. Initial State
        editorNode.setOpacity(0);
        editorNode.setVisible(false);
        editorNode.setManaged(false);

        // 3. The Toggle Button
        Button toggleBtn = new Button("Edit");

        toggleBtn.setOnAction(e -> {
            boolean showingEditor = editorNode.isVisible();
            Node outNode = showingEditor ? editorNode : viewNode;
            Node inNode = showingEditor ? viewNode : editorNode;
            Duration duration = Duration.millis(150);
            FadeTransition fadeOut = new FadeTransition(duration, outNode);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(evt -> {
                outNode.setVisible(false);
                outNode.setManaged(false);

                inNode.setVisible(true);
                inNode.setManaged(true);
                FadeTransition fadeIn = new FadeTransition(duration, inNode);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();

                toggleBtn.setText(showingEditor ? "Edit" : "Done");
            });
            fadeOut.play();
        });

        stack.getChildren().addAll(viewNode, editorNode, toggleBtn);
        StackPane.setAlignment(viewNode, Pos.TOP_LEFT);
        StackPane.setAlignment(editorNode, Pos.TOP_LEFT);
        StackPane.setAlignment(toggleBtn, Pos.TOP_RIGHT);


        HBox.setHgrow(stack, Priority.ALWAYS);
        VBox.setVgrow(stack, Priority.ALWAYS);
        // stack.setBackground(Background.fill(Paint.valueOf("red")));


        view.setGraphic(stack);
    }
}