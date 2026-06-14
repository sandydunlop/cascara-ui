package io.github.qishr.cascara.ui.control;

import java.util.List;
import java.util.function.Consumer;

import io.github.qishr.cascara.common.diagnostic.code.GenericDiagnosticCode;
import io.github.qishr.cascara.ui.api.ToolTipProvider;
import io.github.qishr.cascara.ui.api.UiDiagnosticCode;
import io.github.qishr.cascara.ui.api.data.ObservableTableData;
import io.github.qishr.cascara.ui.api.render.ScalarRenderer;
import io.github.qishr.cascara.ui.data.ColumnMetadata;
import io.github.qishr.cascara.ui.data.ObservableObject;
import io.github.qishr.cascara.ui.data.ObservableTreeNode;
import io.github.qishr.cascara.ui.data.UiDataException;
import io.github.qishr.cascara.ui.style.standard.TreeTableViewStyle;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.SortType;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class CascaraTree<T extends ObservableTreeNode<T,?>> extends StackPane {
    private static final String NO_HEADERS = "no-headers";

    private final TreeTableView<T> treeView = new TreeTableView<>();

    private final ObjectProperty<T> selectedRow = new SimpleObjectProperty<>();
    private Consumer<T> onRowClickedHandler;
    private Consumer<T> onRowDoubleClickedHandler;
    private ContextMenuHandler<T> onContextMenuRequested;

    private SortType st = null;
    private TreeTableColumn<T, ObservableValue<?>> sortColumn = null;

    boolean autoExpand = false;
    private boolean lazy = false;

    @FunctionalInterface
    public interface ContextMenuHandler<T> {
        ContextMenu requestMenu(T data, MouseEvent event);
    }

    public CascaraTree() {
        getChildren().add(initializeView());
    }

    public void setAutoExpand(boolean expand) {
        autoExpand = expand;
        if (autoExpand) {
            setExpanded(treeView.getRoot(), expand);
        }
    }

    private void setExpanded(TreeItem<T> item, boolean expand) {
        item.setExpanded(expand);
        for (TreeItem<T> child : item.getChildren()) {
            setExpanded(child, expand);
        }
    }

    public void setDisplayDisclosureToggles(boolean v) {
        if (v) {
            treeView.getStyleClass().remove(TreeTableViewStyle.NO_DISCLOSURE);
        } else {
            treeView.getStyleClass().add(TreeTableViewStyle.NO_DISCLOSURE);
        }
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    public void setColumnMetadata(List<? extends ColumnMetadata> metadata) {
        treeView.getColumns().clear();
        if (metadata != null) {
            for (ColumnMetadata columnMetadata : metadata) {
                treeView.getColumns().add(createColumn(columnMetadata));
            }
        }
    }

    public void setDataSource(ObservableList<T> dataSource) {
        if (dataSource == null) {
            if (treeView.getRoot() != null) {
                treeView.getRoot().getChildren().clear();
            }
            treeView.setRoot(null);
        } else {
            if (treeView.getRoot() == null) {
                treeView.setRoot(new TreeItem<>());
                treeView.setShowRoot(false);
            }
            treeView.getRoot().setExpanded(true);
            setupRecursiveSync(treeView.getRoot(), dataSource);
        }
        if (sortColumn != null) {
            treeView.getSortOrder().clear();
            treeView.getSortOrder().add(sortColumn);
            sortColumn.setSortType(st);
        }
    }

    @SuppressWarnings("unchecked")
    public void setSortColumnIndex(int n) {
        TreeTableColumn<T, ?> obj = treeView.getColumns().get(n);
        sortColumn = (TreeTableColumn<T, ObservableValue<?>>) obj;
        st = sortColumn.getSortType();
    }

    public void setOnRowClicked(Consumer<T> handler) {
        this.onRowClickedHandler = handler;
    }

    public void setOnRowDoubleClicked(Consumer<T> handler) {
        this.onRowDoubleClickedHandler = handler;
    }

    public void setOnContextMenuRequested(ContextMenuHandler<T> handler) {
        this.onContextMenuRequested = handler;
    }

    public void setShowHeaders(boolean show) {
        if (show) {
            treeView.getStyleClass().remove(NO_HEADERS);
        } else {
            if (!treeView.getStyleClass().contains(NO_HEADERS)) {
                treeView.getStyleClass().add(NO_HEADERS);
            }
        }
    }

    public ObjectProperty<T> selectedRowProperty() {
        return selectedRow;
    }

    public T getSelectedRow() {
        return selectedRow.get();
    }

    public void setSelection(T data) {
        if (data == null) {
            treeView.getSelectionModel().clearSelection();
            selectedRow.set(null);
            return;
        }
        TreeItem<T> targetItem = findTreeItem(treeView.getRoot(), data);
        if (targetItem != null) {
            expandPath(targetItem);
            treeView.getSelectionModel().select(targetItem);
            // Scroll to it so the user actually sees the selection
            treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());
            selectedRow.set(data);
        }
    }

    public T findTreeItem(String path) {
        if (path == null || path.isEmpty()) return null;
        String[] segments = path.split("/");
        TreeItem<T> current = treeView.getRoot();
        for (String segment : segments) {
            current = findChildBySegment(current, segment);
            if (current == null) return null;
        }
        return current.getValue();
    }

    public void selectAndScrollTo(T data) {
        if (data == null) return;

        // 1. Ensure the TreeItem exists in the UI
        TreeItem<T> item = findTreeItem(treeView.getRoot(), data);

        if (item != null) {
            // 2. Expand all parents so it's visible
            expandPath(item);

            // 3. Select and Scroll
            treeView.getSelectionModel().select(item);

            // We need to find the index of the item to scroll
            // In a large tree, this index is relative to visible (expanded) items
            int index = treeView.getRow(item);
            treeView.scrollTo(index);
        }
    }

    //
    // Init
    //

    private VBox initializeView() {
        treeView.setRowFactory(tv -> {
            // TreeTableRow<T> row = new TreeTableRow<>();
            TreeTableRow<T> row = new TreeTableRow<T>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setTooltip(null);
                    } else {
                        // We check if the item is a Frame and has comments
                        if (item instanceof ToolTipProvider provider && provider.getToolTip() != null && !provider.getToolTip().isEmpty()) {
                            javafx.scene.control.Tooltip tooltip = new javafx.scene.control.Tooltip(provider.getToolTip());
                            // Optional: make it wrap for long parser explanations
                            tooltip.setPrefWidth(400);
                            tooltip.setWrapText(true);
                            setTooltip(tooltip);
                        } else {
                            setTooltip(null);
                        }
                    }
                }
            };
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    T rowData = row.getItem();
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (event.getClickCount() == 2) {
                            if (onRowDoubleClickedHandler != null) {
                                onRowDoubleClickedHandler.accept(rowData);
                            }
                        } else {
                            if (onRowClickedHandler != null) {
                                onRowClickedHandler.accept(rowData);
                            }
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        handleRightClick(row, event);
                        event.consume();
                    }
                }
            });
            return row;
        });
        treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedRow.set(newVal != null ? newVal.getValue() : null);
        });
        treeView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY && event.getTarget() == treeView) {
                handleRightClick(null, event);
            }
        });
        treeView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        treeView.setShowRoot(false);
        VBox vbox = new VBox(treeView);
        VBox.setVgrow(treeView, javafx.scene.layout.Priority.ALWAYS);
        treeView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return vbox;
    }

    private void handleRightClick(TreeTableRow<T> row, MouseEvent event) {
        if (row != null && !row.isEmpty()) {
            TreeItem<T> treeItem = row.getTreeItem();
            if (treeItem != null) {
                treeView.getSelectionModel().select(treeItem);
                if (onContextMenuRequested != null) {
                    showMenu(treeItem.getValue(), event);
                }
                return;
            }
        }
        if (onContextMenuRequested != null) {
            showMenu(null, event);
        }
    }

    private void showMenu(T data, MouseEvent event) {
        ContextMenu menu = onContextMenuRequested.requestMenu(data, event);
        if (menu != null) {
            if (menu.isShowing()) menu.hide();
            menu.show(treeView, event.getScreenX(), event.getScreenY());
        }
    }

    @SuppressWarnings("unchecked")
    private TreeTableColumn<T,Object> createColumn(ColumnMetadata def) {
        TreeTableColumn<T,Object> col = new TreeTableColumn<>(def.getTitle());

        def.titleProperty().addListener((obs,old,val) -> {
            col.setText(val);
        });

        if (def.getHeaderStyle() != null && !def.getHeaderStyle().isEmpty()) {
            col.getStyleClass().add(def.getHeaderStyle());
        }

        // Two types of tree table
        //
        // 1. The AST style tree that is displayed in a purely
        //    hierarchical way - each node T and its children appear
        //    as part of the tree.
        //    In this structure, T.getValue() can return column data.
        //    This is handled by CascaraTree.
        //
        // 2. The style used by tables in the settings where a node T
        //    represents the table. Children of node T are the rows
        //    nodes R, and children or nodes R are the tables cell
        //    nodes C.
        //    In this structure, for each cell C in row R, C.getValue()
        //    can return the column data.
        //    This is actually handled by CascaraTable.

        col.setCellValueFactory(data -> {
            try {
                TreeItem<T> treeItem = data.getValue();

                if (treeItem == null) {
                    throw new UiDataException(GenericDiagnosticCode.UNEXPECTED_NULL, "TreeItem");
                }
                T observableTreeNode = treeItem.getValue();
                if (observableTreeNode == null) {
                    throw new UiDataException(GenericDiagnosticCode.UNEXPECTED_NULL, "ObservableTreeNode");
                }

                String columnName = def.getName();

                Observable observable = observableTreeNode.getObservable(columnName);
                if (observable == null) {
                    throw new UiDataException(UiDiagnosticCode.PROPERTY_NOT_RECOGNIZED, columnName);
                }

                if (observable instanceof ObjectProperty property) {
                    return property;
                }

                // Fall back to returning the whole observableTreeNode

                ObservableValue<Object> wrapped = new SimpleObjectProperty<>(observableTreeNode);
                return wrapped;
            } catch (RuntimeException e) {
                return null;
            }
        });

        col.setCellFactory(buildCellFactory(def));

        if (def.getMinWidth() != -1) col.setMinWidth(def.getMinWidth());
        if (def.getPrefWidth() != -1) col.setPrefWidth(def.getPrefWidth());
        if (def.getMaxWidth() != -1) col.setMaxWidth(def.getMaxWidth());
        if (def.getCellStyle() != null && !def.getCellStyle().isEmpty()) col.setStyle(def.getCellStyle());
        return col;
    }

    private Callback<TreeTableColumn<T,Object>, TreeTableCell<T,Object>> buildCellFactory(ColumnMetadata meta) {
        return new Callback<>() {
            @Override public TreeTableCell<T,Object> call(TreeTableColumn<T,Object> param) {
                return new TreeTableCell<>() {

                    @Override protected void updateItem(Object item, boolean empty) {
                        if (item == getItem()) return;
                        super.updateItem(item, empty);
                        if (item == null) {
                            super.setText(null);
                            super.setGraphic(null);
                        } else {
                            String columnName = meta.getName();
                            if (meta.getRenderers().getScalarRenderer() instanceof ScalarRenderer renderer) {
                                renderer.render(this, item, null, meta);
                            } else if (item instanceof ObservableObject object) {
                                this.setText(object.getString(columnName));
                            } else {
                                this.setText(item.toString());
                            }
                        }
                    }
                };
            }
        };
    }

    //
    // Sync
    //

    private void setupRecursiveSync(TreeItem<T> treeItem, ObservableList<? extends T> dataItems) {
        // Initial population
        syncChildren(treeItem, dataItems);

        // Listen for future changes
        dataItems.addListener((ListChangeListener<T>) c -> {
            syncChildren(treeItem, dataItems);
            if (autoExpand) {
                treeView.getRoot().setExpanded(true);
            }
        });

        if (autoExpand) {
            setExpanded(treeItem, true);
        }
        treeView.getRoot().setExpanded(true);
    }

    private void syncChildren(TreeItem<T> parentItem, ObservableList<? extends T> dataItems) {
        if (dataItems == null) return;

        parentItem.getChildren().removeIf(treeItem -> !dataItems.contains(treeItem.getValue()));

        for (int i = 0; i < dataItems.size(); i++) {
            T dataItem = dataItems.get(i);

            TreeItem<T> existing = parentItem.getChildren().stream()
                    .filter(ti -> ti.getValue() == dataItem)
                    .findFirst().orElse(null);

            if (existing == null) {
                TreeItem<T> newItem = new TreeItem<>(dataItem);

                // Check if the index is safe for insertion
                if (i >= 0 && i <= parentItem.getChildren().size()) {
                    parentItem.getChildren().add(i, newItem);
                } else {
                    // Fallback: If the index is drifting, add to the end to prevent the crash
                    System.err.println("Index drift detected at " + i + ". Appending instead.");
                    parentItem.getChildren().add(newItem);
                }

                if (lazy) {
                    // --- LAZY MODE (FileSystem) ---
                    if (dataItem.isBranch()) {
                        newItem.getChildren().add(new TreeItem<>(null)); // Add dummy
                    }

                    newItem.expandedProperty().addListener((obs, was, isExpanded) -> {
                        if (isExpanded && hasDummy(newItem)) {
                            newItem.getChildren().clear();
                            setupRecursiveSync(newItem, dataItem.getChildren());
                        }
                    });
                } else {
                    // --- IMMEDIATE MODE (Themes, Variations, etc.) ---
                    // If it has children, sync them now. No dummies, no arrows on leaves.
                    // if (dataItem.isBranch()) {
                        setupRecursiveSync(newItem, dataItem.getChildren());
                    // }
                }
            }
        }
    }

    private boolean hasDummy(TreeItem<T> item) {
        return item.getChildren().size() == 1 && item.getChildren().get(0).getValue() == null;
    }

    private TreeItem<T> findTreeItem(TreeItem<T> root, T target) {
        if (root == null) return null;
        if (root.getValue() == target) return root;

        for (TreeItem<T> child : root.getChildren()) {
            TreeItem<T> found = findTreeItem(child, target);
            if (found != null) return found;
        }
        return null;
    }

    private void expandPath(TreeItem<T> item) {
        TreeItem<T> parent = item.getParent();
        while (parent != null) {
            parent.setExpanded(true);
            parent = parent.getParent();
        }
    }


    /// Helper to find a specific child TreeItem whose data matches the path segment.
    private TreeItem<T> findChildBySegment(TreeItem<T> parent, String segment) {
        if (parent == null) return null;

        for (TreeItem<T> child : parent.getChildren()) {
            T data = child.getValue();
            if (data != null && segment.equals(data.getNodeName())) {
                return child;
            }
        }
        return null;
    }

    public void collapseOthers(T targetData) {
        if (targetData == null || treeView.getRoot() == null) return;

        // 1. Identify the set of nodes we MUST keep expanded (the target and its parents)
        java.util.Set<T> pathSet = new java.util.HashSet<>();
        T current = targetData;
        while (current != null) {
            pathSet.add(current);
            current = current.getParent(); // Ensure your TreeData interface has getParent()
        }

        // 2. Recursively collapse everything not in that path
        collapseRecursive(treeView.getRoot().getChildren().getFirst(), pathSet);
    }

    private void collapseRecursive(TreeItem<T> item, java.util.Set<T> pathSet) {
        if (item == null || item.getValue() == null) return;

        // If this item is NOT in our path, collapse it
        if (!pathSet.contains(item.getValue())) {
            item.setExpanded(false);
        } else {
            // If it IS in the path, keep it open and check its children
            item.setExpanded(true);
            for (TreeItem<T> child : item.getChildren()) {
                collapseRecursive(child, pathSet);
            }
        }
    }
}