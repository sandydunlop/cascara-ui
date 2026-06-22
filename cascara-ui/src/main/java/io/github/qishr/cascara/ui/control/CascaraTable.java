package io.github.qishr.cascara.ui.control;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import io.github.qishr.cascara.ui.api.CustomizableArea;
import io.github.qishr.cascara.ui.api.data.ObservableTableData;
import io.github.qishr.cascara.ui.data.ObservableObject;
import io.github.qishr.cascara.ui.data.ObservableTreeNode;
import io.github.qishr.cascara.ui.data.ColumnMetadata;
import io.github.qishr.cascara.common.data.TableData;
import io.github.qishr.cascara.ui.render.RenderDispatcher;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.skin.TableViewSkin;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class CascaraTable extends StackPane {
    private static final String NO_HEADERS = "no-headers";
    private static final String DELETE_BUTTON = "_delete_button";

    private final TableView<ObservableTableData> tableView = new TableView<>();

    private final ObjectProperty<ObservableTableData> selectedRow = new SimpleObjectProperty<>();
    private Consumer<ObservableTableData> onRowClickedHandler;
    private Consumer<ObservableTableData> onRowDoubleClickedHandler;
    private ContextMenuHandler onContextMenuRequested;
    private Consumer<ObservableTableData> onRemoveRowHandler;
    private Runnable onAddRowClicked;

    private SortType st = null;
    private TableColumn<ObservableTableData, ObservableValue<?>> sortColumn = null;
    private ListChangeListener<ObservableTableData> scrollListener;

    private boolean allowReorder = false;
    private boolean allowEdit = false;

    private HBox bottomContainer;
    private VBox tableContainer;
    private BottomArea bottomArea = new BottomArea();

    public CascaraTable() {
        tableContainer = initializeView();
        getChildren().add(tableContainer);
        initializeScroll();
    }

    @FunctionalInterface
    public interface ContextMenuHandler {
        ContextMenu requestMenu(TableData data, MouseEvent event);
    }

    public void setAllowEdit(boolean v) {
        allowEdit = v;
    }

    public DoubleProperty fixedCellSizeProperty() { return tableView.fixedCellSizeProperty(); }

    public Double getFixedCellSize() { return tableView.fixedCellSizeProperty().get(); }
    public void setFixedCellSize(Double v) { tableView.fixedCellSizeProperty().set(v); }

    public void setColumnMetadata(List<? extends ColumnMetadata> metadata) {
        tableView.getColumns().clear();
        for (ColumnMetadata columnMetadata : metadata) {
            tableView.getColumns().add(createColumn(columnMetadata));
        }
        if (onRemoveRowHandler != null) {
            ColumnMetadata deleteColumn = new ColumnMetadata(DELETE_BUTTON, "", null, null, col -> new TableCell<>() {
                private final Button delBtn = new Button("×");
                {

                    // cascara://organizer/CASC-000257D1 Use theme colors
                    delBtn.setStyle("-fx-text-fill: #f48771; -fx-background-color: transparent; -fx-cursor: hand;");
                    delBtn.setOnAction(e -> {
                        ObservableObject rowData = getTableRow().getItem();
                        if (rowData != null && onRemoveRowHandler != null) {
                            onRemoveRowHandler.accept(rowData);
                        }
                    });
                }
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : delBtn);
                }
            });
            tableView.getColumns().add(createColumn(deleteColumn));
        }
    }

    @SuppressWarnings("unchecked")
    public void setDataSource(ObservableList<? extends ObservableTableData> dataSource) {
        tableView.setItems((ObservableList<ObservableTableData>)dataSource);
        if (sortColumn != null) {
            tableView.getSortOrder().clear();
            tableView.getSortOrder().add(sortColumn);
            sortColumn.setSortType(st);
        }
    }

    public boolean canReorder() { return allowReorder; }

    public void setAllowReorder(boolean b) {
        allowReorder = b;
    }

    public void setOnRemoveRowClicked(Consumer<ObservableTableData> handler) {
        this.onRemoveRowHandler = handler;
    }

    public void setOnAddRowClicked(Runnable handler) {
        this.onAddRowClicked = handler;
        if (handler == null) {
            tableContainer.getChildren().remove(bottomContainer);
        } else {
            tableContainer.getChildren().add(bottomContainer);
        }
    }

    @SuppressWarnings("unchecked")
    public void setSortColumnIndex(int n) {
        TableColumn<ObservableTableData, ?> obj = tableView.getColumns().get(n);
        sortColumn = (TableColumn<ObservableTableData, ObservableValue<?>>) obj;
        st = sortColumn.getSortType();
    }

    public void setOnRowClicked(Consumer<ObservableTableData> handler) {
        this.onRowClickedHandler = handler;
    }

    public void setOnRowDoubleClicked(Consumer<ObservableTableData> handler) {
        this.onRowDoubleClickedHandler = handler;
    }

    public void setOnContextMenuRequested(ContextMenuHandler handler) {
        this.onContextMenuRequested = handler;
    }

    public void setShowHeaders(boolean show) {
        if (show) {
            tableView.getStyleClass().remove(NO_HEADERS);
        } else {
            if (!tableView.getStyleClass().contains(NO_HEADERS)) {
                tableView.getStyleClass().add(NO_HEADERS);
            }
        }
    }

    public void setAutoScroll(boolean autoScroll) {
        if (autoScroll) {
            if (scrollListener == null) {
                scrollListener = c -> {
                    while (c.next()) {
                        if (c.wasAdded()) {
                            // 1. Find the last visible index
                            // We use a bit of a trick here: if the last index is visible, scroll.
                            boolean isAtBottom = false;

                            // Access the internal skin to find the flow
                            if (tableView.getSkin() instanceof TableViewSkin<?> skin) {
                                VirtualFlow<?> flow = (VirtualFlow<?>) skin.getChildren().stream()
                                        .filter(node -> node instanceof VirtualFlow)
                                        .findFirst()
                                        .orElse(null);

                                if (flow != null && flow.getLastVisibleCell() != null) {
                                    int lastVisible = flow.getLastVisibleCell().getIndex();
                                    int totalItems = tableView.getItems().size();
                                    // If the last visible item is within 1 or 2 of the end
                                    isAtBottom = lastVisible >= totalItems - 2;
                                }
                            }

                            if (isAtBottom) {
                                Platform.runLater(() -> tableView.scrollTo(tableView.getItems().size() - 1));
                            }
                        }
                    }
                };
            }
            tableView.getItems().addListener(scrollListener);
        } else {
            if (scrollListener != null) {
                tableView.getItems().removeListener(scrollListener);
            }
        }
    }

    public ObjectProperty<ObservableTableData> selectedRowProperty() {
        return selectedRow;
    }

    public TableData getSelectedRow() {
        return selectedRow.get();
    }

    public void setSelectedRow(ObservableTableData data) {
        selectedRow.set(data);
        if (data != null) {
            tableView.getSelectionModel().select(data);
        } else {
            tableView.getSelectionModel().clearSelection();
        }
    }

    public ObservableTableData getRow(int n) {
        return tableView.getItems().get(n);
    }

    public ObservableList<? extends TableData> getItems() {
        return tableView.getItems();
    }

    //
    // Init
    //

    @SuppressWarnings("deprecation")
    private VBox initializeView() {
        tableView.setUserData(this);
        tableView.setRowFactory(tv -> {
            TableRow<ObservableTableData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    ObservableTableData rowData = row.getItem();
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
                        handleRightClick(rowData, event);
                        event.consume();
                    }
                }
            });
            row.setOnDragDetected(event -> onDragDetected(row, event));
            return row;
        });
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedRow.set(newVal);
        });
        tableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY && event.getTarget() == tableView) {
                handleRightClick(null, event);
            }
        });
        tableView.setOnDragOver(event -> onDragOver(tableView, event));
        tableView.setOnDragDropped(event -> onDragDropped(tableView, event));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setEditable(true);

        VBox tableContainer = new VBox(tableView);
        VBox.setVgrow(tableView, javafx.scene.layout.Priority.ALWAYS);
        tableView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Button addRowButton = new Button("+ New Item");
        addRowButton.getStyleClass().add("add-button");
        addRowButton.setOnAction(e -> {
            onAddRowClicked.run();
        });
        bottomContainer = new HBox(bottomArea, addRowButton);
        bottomContainer.setPadding(new Insets(8, 0, 20, 0));
        bottomContainer.setAlignment(Pos.CENTER_RIGHT);

        return tableContainer;
    }

    private void handleRightClick(ObservableTableData item, MouseEvent event) {
        if (item != null) {
            tableView.getSelectionModel().select(item);
        }
        if (onContextMenuRequested != null) {
            ContextMenu menu = onContextMenuRequested.requestMenu(item, event);
            if (menu.isShowing()) {
                menu.hide();
            }
            if (menu != null) {
                menu.show(tableView, event.getScreenX(), event.getScreenY());
            }
        }
    }

    private TableColumn<ObservableTableData, ObservableValue<?>> createColumn(ColumnMetadata def) {
        TableColumn<ObservableTableData, ObservableValue<?>> col = new TableColumn<>(def.getTitle());

        def.titleProperty().addListener((obs,old,val) -> {
            col.setText(val);
        });

        if (def.getHeaderStyle() != null && !def.getHeaderStyle().isEmpty()) {
            col.getStyleClass().add(def.getHeaderStyle());
        }

        col.setCellValueFactory(data -> {
            try {
                String columnName = def.getName();
                if (columnName.equals(DELETE_BUTTON)) {
                    return new SimpleObjectProperty<>();
                }

                Object obj;
                if (data.getValue() instanceof ObservableTreeNode treeNode) {
                    Map<?,?> dataContext = treeNode.getChildMap();
                    obj = dataContext.get(columnName);
                } else {
                    obj = data.getValue().getObservable(columnName);
                }

                if (obj instanceof ObservableValue obs) {
                    ObservableValue<ObservableValue<?>> wrapped = new SimpleObjectProperty<>(obs);
                    return wrapped;
                }
                return null;
            } catch (RuntimeException e) {
                return null;
            }
        });

        col.setCellFactory(buildCellFactory(def));

        if (def.getComparator() != null) {
            col.setComparator(def.getComparator());
        }

        if (def.getMinWidth() != -1) col.setMinWidth(def.getMinWidth());
        if (def.getPrefWidth() != -1) col.setPrefWidth(def.getPrefWidth());
        if (def.getMaxWidth() != -1) col.setMaxWidth(def.getMaxWidth());
        if (def.getCellStyle() != null) col.setStyle(def.getCellStyle());

        return col;
    }

    private Callback<TableColumn<ObservableTableData,ObservableValue<?>>, TableCell<ObservableTableData,ObservableValue<?>>> buildCellFactory(ColumnMetadata meta) {
        return new Callback<>() {
            @Override public TableCell<ObservableTableData,ObservableValue<?>> call(TableColumn<ObservableTableData,ObservableValue<?>> param) {
                return new TableCell<>() {
                    @Override protected void updateItem(ObservableValue<?> item, boolean empty) {
                        if (meta.getName().equals(DELETE_BUTTON)) {
                            renderDeleteButton(this);
                            return;
                        }

                        if (item == getItem()) return;
                        super.updateItem(item, empty);
                        if (item == null) {
                            super.setText(null);
                            super.setGraphic(null);
                        } else {
                            if (!allowEdit) {
                                meta.setAllowEdit(false);
                            }
                            try {
                                RenderDispatcher.render(this, item, null, meta);
                            } catch (Exception e) {
                                System.out.println("Table failed to render " + meta.getName());
                                e.printStackTrace();
                            }
                        }
                    }
                };
            }
        };
    }

    private void renderDeleteButton(TableCell<?,?> view) {
        Button delButton = new Button("×");
        // cascara://organizer/CASC-000257D1 Use theme colors
        delButton.setStyle("-fx-text-fill: #f48771; -fx-background-color: transparent; -fx-cursor: hand;");
        delButton.setOnAction(e -> {

            if (view.getTableRow().getItem() instanceof ObservableObject rowData) {
                if (rowData != null && onRemoveRowHandler != null) {
                    onRemoveRowHandler.accept(rowData);
                }
            }
        });
        view.setGraphic(delButton);
        view.setText(null);
    }

    public void initializeScroll() {
        tableView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(() -> {
                    if (scrollListener != null) {
                        tableView.scrollTo(tableView.getItems().size() - 1);
                    }
                });
            }
        });
    }

    //
    // Drag & Drop
    //

    @FunctionalInterface
    public interface MoveFromHandler<T extends ObservableObject> {
        boolean onDraggedAndDropped(CascaraTable sourceTable, TableRow<T> sourceRow, DragEvent event);
    }

    private MoveFromHandler<ObservableObject> onDraggedAndDropped;

    public void setOnDraggedAndDropped(MoveFromHandler<ObservableObject> handler) {
        this.onDraggedAndDropped = handler;
    }

    public class DragDropConstants {
        public static final String TASK_MIME_TYPE = "application/x-java-object;class=java.lang.String";
        public static final DataFormat DATA_FORMAT = new DataFormat(TASK_MIME_TYPE);
        private DragDropConstants() {
            // Prevent instantiation
        }
    }

    private void onDragDetected(TableRow<? extends TableData> row, MouseEvent event) {
        if (!row.isEmpty()) {
            // movingRow = row;
            WritableImage dragViewImage = row.snapshot(null, null);
            ClipboardContent content = new ClipboardContent();
            content.put(DragDropConstants.DATA_FORMAT, "data"); // Doesn't work unless something is here
            Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
            db.setContent(content);
            db.setDragView(dragViewImage);
            db.setDragViewOffsetX(dragViewImage.getWidth() / 2);
            db.setDragViewOffsetY(dragViewImage.getHeight() / 2);
            event.consume();
        }
    }

    private void onDragOver(TableView<? extends TableData> tableView, DragEvent event) {
        if (event.getGestureSource() != tableView && event.getDragboard().hasContent(DragDropConstants.DATA_FORMAT)) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @SuppressWarnings("unchecked")
    private void onDragDropped(TableView<? extends ObservableTableData> targetTable, DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasContent(DragDropConstants.DATA_FORMAT)) {
            Node sourceNode = (Node)event.getGestureSource();
            if (sourceNode instanceof TableRow movingRow &&
                movingRow.getItem() instanceof ObservableTableData sourceData &&
                movingRow.getTableView() instanceof TableView sourceTable &&
                sourceTable != targetTable && sourceTable.getItems().contains(sourceData))
            {
                if (sourceTable.getUserData() instanceof CascaraTable sourceObservantTable &&
                    targetTable.getUserData() instanceof CascaraTable) {

                    if (onDraggedAndDropped != null) {
                        success = true;
                        if (!onDraggedAndDropped.onDraggedAndDropped(sourceObservantTable, movingRow, event)) {
                            success = false;
                        }
                    }
                }

            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    //
    //
    //

    public CustomizableArea getBottomArea() {
        return bottomArea;
    }

    public static class BottomArea extends HBox implements CustomizableArea {

        @Override
        public void dispose() {
        }

        @Override
        public void addContent(String title, Node node) {
            getChildren().add(node);
        }

        @Override
        public void removeContent(Node node) {
            getChildren().remove(node);
        }

        @Override
        public void selectContent(Node node) {
        }
    }
}
