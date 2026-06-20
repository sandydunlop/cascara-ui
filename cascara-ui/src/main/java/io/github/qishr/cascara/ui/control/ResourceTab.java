package io.github.qishr.cascara.ui.control;

import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * A draggable tab that can optionally be detached from its tab pane and shown
 * in a separate window. This can be added to any normal TabPane, however a
 * TabPane with draggable tabs must *only* have DraggableTabs, normal tabs and
 * DrragableTabs mixed will cause issues!
 * <p>
 * @author Michael Berry
 */
public class ResourceTab extends Tab {
    private static final Set<TabPane> tabPanes = new HashSet<>();
    private static final Stage markerStage;

    private TabPane tabPane;
    private ResourceTabHeader header;
    private Text dragText;
    private Stage dragStage;

    private boolean isDetachable = false;
    private EventHandler<? super ContextMenuEvent> onContextMenuRequested = null;

    private BooleanProperty activeProp = new SimpleBooleanProperty(this, "active");
    public BooleanProperty activeProperty() { return activeProp; }
    public boolean isActive() { return activeProp.get(); }
    public void setActive(final boolean t) { this.activeProp.set(t); }

    private BooleanProperty modifiedProp = new SimpleBooleanProperty(this, "modified");
    public BooleanProperty modifiedProperty() { return modifiedProp; }
    public boolean isModified() { return modifiedProp.get();}
    public void setModified(final boolean t) {
        this.modifiedProp.set(t);
        if (t) {
            setTransient(false);
        }
    }

    private BooleanProperty pinnedProp = new SimpleBooleanProperty(this, "pinned");
    public BooleanProperty pinnedProperty() { return pinnedProp; }
    public boolean isPinned() { return pinnedProp.get();}
    public void setPinned(final boolean pinned) {
        this.pinnedProp.set(pinned);
        if (pinned) {
            setTransient(false);
        }
    }

    private BooleanProperty transientProp = new SimpleBooleanProperty(false);
    public BooleanProperty transientProperty() { return transientProp; }
    public boolean isTransient() { return transientProp.get(); }
    public void setTransient(final boolean t) { this.transientProp.set(t); }

    private SimpleStringProperty titleProp = new SimpleStringProperty(this, "title");
    public SimpleStringProperty titleProperty() { return titleProp; }
    public String getTitle() { return this.titleProp.get(); }
    public void setTitle(String text) { this.titleProp.set(text); }

    static {
        markerStage = new Stage();
        markerStage.initStyle(StageStyle.UNDECORATED);
        Rectangle dummy = new Rectangle(3, 10, Color.web("#555555"));
        StackPane markerStack = new StackPane();
        markerStack.getChildren().add(dummy);
        markerStage.setScene(new Scene(markerStack));
    }

    public ResourceTab(TabPane tabPane) {
        super("");
        this.tabPane = tabPane;

        isDetachable = false;
        dragStage = new Stage();
        dragStage.initStyle(StageStyle.UNDECORATED);
        StackPane dragStagePane = new StackPane();

        // TODO: I think this fails because CSS isn't loaded in this stage
        // dragStagePane.setStyle("-fx-background-color: -color-control-background;");

        dragText = new Text("");
        StackPane.setAlignment(dragText, Pos.CENTER);
        dragStagePane.getChildren().add(dragText);
        dragStage.setScene(new Scene(dragStagePane));

        header = new ResourceTabHeader();
        header.setOnCloseButtonClick(() -> closeButtonClicked());
        header.setOnContextMenuRequested(event -> contextMenuRequested(event));
        header.setOnMouseDragged(mouse -> mouseDragged(mouse));
        header.setOnMouseReleased(mouse -> mouseReleased(mouse));
        header.setOnMouseClicked(mouse -> mouseClicked(mouse));
        setGraphic(header);

        setClosable(false);
        registerListeners();
    }

    private void registerListeners() {
        header.activeProperty().bind(activeProp);
        header.modifiedProperty().bind(modifiedProp);

        // cascara://organizer/CASC-0002EA17
        // TODO: This stops the header being able to unpin the tab
        header.pinnedProperty().bind(pinnedProp);

        header.transientProperty().bind(transientProp);

        titleProp.addListener((obs,old,val) -> {
            header.setTitle(val);
            dragText.setText(val);
        });
    }

    public int getIndex() {
        int index = 0;
        for (Tab tab : tabPane.getTabs()) {
            if (tab == this) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public void setDetachable(boolean detachable) {this.isDetachable = detachable;}

    public void setOnContextMenuRequested(EventHandler<? super ContextMenuEvent> onContextMenuRequested) {
        this.onContextMenuRequested = onContextMenuRequested;
    }

    //
    // Mouse event handlers
    //

    private void contextMenuRequested(ContextMenuEvent event) {
        if (onContextMenuRequested != null) {
            onContextMenuRequested.handle(event);
        }
    }

    private void closeButtonClicked() {
        if (isPinned()) {
            setPinned(false);
        } else {
            EventHandler<Event> handler = onCloseRequestProperty().get();
            if (null != handler) {
                handler.handle(null);
            } else {
                getTabPane().getTabs().remove(this);
            }
        }
    }

    private void mouseClicked(MouseEvent mouse) {
        this.tabPane.getSelectionModel().select(this);
        if (mouse.getButton() == MouseButton.PRIMARY) {
            if (mouse.getClickCount() == 2) {
                setTransient(false);
            }
            // Hide the context menu if it's visibble
            contextMenuRequested(null);
        }
    }

    private void mouseDragged(MouseEvent mouse) {
        dragStage.setWidth(header.getWidth() + 10);
        dragStage.setHeight(header.getHeight() + 10);
        dragStage.setX(mouse.getScreenX());
        dragStage.setY(mouse.getScreenY());
        dragStage.show();
        Point2D screenPoint = new Point2D(mouse.getScreenX(), mouse.getScreenY());
        tabPanes.add(getTabPane());
        InsertData data = getInsertData(screenPoint);
        if(data == null || data.getInsertPane().getTabs().isEmpty()) {
            markerStage.hide();
        }
        else {
            int index = data.getIndex();
            boolean end = false;
            if(index == data.getInsertPane().getTabs().size()) {
                end = true;
                index--;
            }
            Rectangle2D rect = getAbsoluteRect(data.getInsertPane().getTabs().get(index));
            if(end) {
                markerStage.setX(rect.getMaxX() + 13);
            }
            else {
                markerStage.setX(rect.getMinX());
            }
            markerStage.setY(rect.getMaxY() + 10);
            markerStage.show();
        }
    }

    private void mouseReleased(MouseEvent mouse) {
        markerStage.hide();
        dragStage.hide();
        if(!mouse.isStillSincePress()) {
            Point2D screenPoint = new Point2D(mouse.getScreenX(), mouse.getScreenY());
            TabPane oldTabPane = getTabPane();
            int oldIndex = oldTabPane.getTabs().indexOf(ResourceTab.this);
            tabPanes.add(oldTabPane);
            InsertData insertData = getInsertData(screenPoint);
            if(insertData != null) {
                int addIndex = insertData.getIndex();
                if(oldTabPane == insertData.getInsertPane() && oldTabPane.getTabs().size() == 1) {
                    return;
                }
                oldTabPane.getTabs().remove(ResourceTab.this);
                if(oldIndex < addIndex && oldTabPane == insertData.getInsertPane()) {
                    addIndex--;
                }
                if(addIndex > insertData.getInsertPane().getTabs().size()) {
                    addIndex = insertData.getInsertPane().getTabs().size();
                }
                insertData.getInsertPane().getTabs().add(addIndex, ResourceTab.this);
                insertData.getInsertPane().selectionModelProperty().get().select(addIndex);
                return;
            }
            if(!isDetachable) {
                return;
            }
            final Stage newStage = new Stage();
            final TabPane pane = new TabPane();
            tabPanes.add(pane);
            newStage.setOnHiding(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent t) {
                    tabPanes.remove(pane);
                }
            });
            getTabPane().getTabs().remove(ResourceTab.this);
            pane.getTabs().add(ResourceTab.this);
            pane.getTabs().addListener(new ListChangeListener<Tab>() {

                @Override
                public void onChanged(ListChangeListener.Change<? extends Tab> change) {
                    if(pane.getTabs().isEmpty()) {
                        newStage.hide();
                    }
                }
            });
            newStage.setScene(new Scene(pane));
            newStage.initStyle(StageStyle.UTILITY);
            newStage.setX(mouse.getScreenX());
            newStage.setY(mouse.getScreenY());
            newStage.show();
            pane.requestLayout();
            pane.requestFocus();
        }
    }

    //
    // Helpers
    //

    private InsertData getInsertData(Point2D screenPoint) {
        for(TabPane tabPane : tabPanes) {
            Rectangle2D tabAbsolute = getAbsoluteRect(tabPane);
            if(tabAbsolute.contains(screenPoint)) {
                int tabInsertIndex = 0;
                if(!tabPane.getTabs().isEmpty()) {
                    Rectangle2D firstTabRect = getAbsoluteRect(tabPane.getTabs().get(0));
                    if(firstTabRect.getMaxY()+60 < screenPoint.getY() || firstTabRect.getMinY() > screenPoint.getY()) {
                        return null;
                    }
                    Rectangle2D lastTabRect = getAbsoluteRect(tabPane.getTabs().get(tabPane.getTabs().size() - 1));
                    if(screenPoint.getX() < (firstTabRect.getMinX() + firstTabRect.getWidth() / 2)) {
                        tabInsertIndex = 0;
                    }
                    else if(screenPoint.getX() > (lastTabRect.getMaxX() - lastTabRect.getWidth() / 2)) {
                        tabInsertIndex = tabPane.getTabs().size();
                    }
                    else {
                        for(int i = 0; i < tabPane.getTabs().size() - 1; i++) {
                            Tab leftTab = tabPane.getTabs().get(i);
                            Tab rightTab = tabPane.getTabs().get(i + 1);
                            if(leftTab instanceof ResourceTab && rightTab instanceof ResourceTab) {
                                Rectangle2D leftTabRect = getAbsoluteRect(leftTab);
                                Rectangle2D rightTabRect = getAbsoluteRect(rightTab);
                                if(betweenX(leftTabRect, rightTabRect, screenPoint.getX())) {
                                    tabInsertIndex = i + 1;
                                    break;
                                }
                            }
                        }
                    }
                }
                return new InsertData(tabInsertIndex, tabPane);
            }
        }
        return null;
    }

    private Rectangle2D getAbsoluteRect(Region node) {
        return new Rectangle2D(node.localToScene(node.getLayoutBounds().getMinX(), node.getLayoutBounds().getMinY()).getX() + node.getScene().getWindow().getX(),
                node.localToScene(node.getLayoutBounds().getMinX(), node.getLayoutBounds().getMinY()).getY() + node.getScene().getWindow().getY(),
                node.getWidth(),
                node.getHeight());
    }

    private Rectangle2D getAbsoluteRect(Tab tab) {
        HBox node = ((ResourceTab) tab).getHeader();
        return getAbsoluteRect(node);
    }

    private ResourceTabHeader getHeader() {
        return header;
    }

    private boolean betweenX(Rectangle2D r1, Rectangle2D r2, double xPoint) {
        double lowerBound = r1.getMinX() + r1.getWidth() / 2;
        double upperBound = r2.getMaxX() - r2.getWidth() / 2;
        return xPoint >= lowerBound && xPoint <= upperBound;
    }

    private static class InsertData {
        private final int index;
        private final TabPane insertPane;

        public InsertData(int index, TabPane insertPane) {
            this.index = index;
            this.insertPane = insertPane;
        }

        public int getIndex() {
            return index;
        }

        public TabPane getInsertPane() {
            return insertPane;
        }
    }
}