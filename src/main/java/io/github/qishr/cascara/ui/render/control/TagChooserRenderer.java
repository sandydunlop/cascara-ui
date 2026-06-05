package io.github.qishr.cascara.ui.render.control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.github.qishr.cascara.ui.api.data.DataProvider;
import io.github.qishr.cascara.ui.api.render.ArrayEditorRenderer;
import io.github.qishr.cascara.ui.control.TagChooser;
import io.github.qishr.cascara.ui.form.FieldMetadata;
import io.github.qishr.cascara.ui.option.TagOption;
import io.github.qishr.cascara.ui.render.AbstractArrayRenderer;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Labeled;

public class TagChooserRenderer extends AbstractArrayRenderer implements ArrayEditorRenderer {

    @Override
    public String getSchemaType() {
        return null;
    }

    @Override
    public String getSchemaFormat() { return null; }

    @Override
    public String getContentType() {
        return "cascara/tag";
    }

    @Override
    public Node render(Labeled view, @SuppressWarnings("rawtypes") ObservableList list, DataProvider dataProvider, FieldMetadata meta) {

        // OptionListEditor editor = provider.get();

        final AtomicBoolean isUpdating = new AtomicBoolean(false);
        final ObservableList<TagOption> uiSelectedIds = FXCollections.observableArrayList();

        TagChooser editor = new TagChooser(uiSelectedIds, meta.getOptionProvider(), meta.getProviderParameter());
        editor.setPrefHeight(10);


        // Helper to sync from AST to UI
        final Runnable syncFromAst = () -> {
            if (isUpdating.get()) return;
            List<TagOption> current = new ArrayList<>();
            for (Object o : list) {
                current.add((TagOption)o);
            }
            // Prevent recursive listener triggers
            if (!uiSelectedIds.equals(current)) {
                uiSelectedIds.setAll(current);
            }
        };
        syncFromAst.run();

        // UI -> AST: Listen to the TagChooser's list
        uiSelectedIds.addListener((ListChangeListener.Change<? extends TagOption> c) -> {
            if (isUpdating.get()) return;
            isUpdating.set(true);
            try {
                while (c.next()) {
                    if (c.wasAdded()) {
                        for (TagOption id : c.getAddedSubList()) {
                            boolean exists = list.contains(id);
                            if (!exists) {
                                try {
                                    list.add(id);
                                } catch (UnsupportedOperationException e) {
                                    // System.out.println("Debug AFF: " + list.getClass().getName());
                                }
                            }
                        }
                    }
                    if (c.wasRemoved()) {
                        for (TagOption id : c.getRemoved()) {
                            try {
                                list.remove(id);
                            } catch (UnsupportedOperationException e) {
                                // System.out.println("Debug AFF: " + list.getClass().getName());
                            }
                        }
                    }
                }
                // notifyDocumentChanged(false);
                if (meta.getOnChange() != null) {
                    meta.getOnChange().run();
                }
            } finally {
                isUpdating.set(false);
            }
        });

        // AST -> UI: Listen for external changes (e.g., Undo, Table edits)
        list.addListener(new ListChangeListener<TagOption>() {
            public void onChanged(Change<? extends TagOption> c) {
                if (!isUpdating.get()) {
                    syncFromAst.run();
                }
            }
        });

        // editor.configure(uiSelectedIds, meta.getOptionProvider(), meta.getProviderParameter());
        // if (editor instanceof Node control) {
        //     return control;
        // }

        view.setGraphic(editor);
        view.setText(null);
        return editor;
    }
}
