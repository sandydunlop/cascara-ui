package io.github.qishr.cascara.ui.control;

import io.github.qishr.cascara.ui.style.custom.DocumentTabHeaderStyle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ResourceTabPane extends VBox {
    private TabPane tabPane = null;
    private ObservableList<ResourceTab> allTabs = FXCollections.observableArrayList();
    protected final ObjectProperty<ResourceTab> selectedTab = new SimpleObjectProperty<>();


    public ObjectProperty<ResourceTab> selectedTabProperty() {return selectedTab;}
    public ResourceTab getSelectedTab() {return this.selectedTab.get();}
    public void setUri(ResourceTab v) {this.selectedTab.set(v);}


    public ResourceTabPane() {
        tabPane = new TabPane();
        tabPane.getStyleClass().setAll(DocumentTabHeaderStyle.DOCUMENT_TAB_PANE);
        tabPane.getSelectionModel().selectedItemProperty().addListener(this::onSelectionChanged);
        VBox.setVgrow(tabPane, Priority.ALWAYS);
        HBox.setHgrow(tabPane, Priority.ALWAYS);
        getChildren().add(tabPane);
    }

    private void onSelectionChanged(ObservableValue<? extends Tab> obs, Tab old, Tab tab) {
        selectedTab.set((ResourceTab)tab);
    }

    public void selectTab(ResourceTab tab) {
        tabPane.getSelectionModel().select(tab);
    }

    public ObservableList<ResourceTab> getTabs() {
        return allTabs;
    }

    public ResourceTab createTab(boolean isTransient) {
        ResourceTab tab = new ResourceTab(tabPane);
        tab.setTransient(isTransient);

        tab.setPinned(false);

        // TODO: Bind this to ducment edited property
        tab.setModified(false);

        // tab.setOnContextMenuRequested(contextMenu -> {
        //     showContextMenu(contextMenu, tab);
        // });
        tabPane.getTabs().add(tab);
        allTabs.add(tab);
        return tab;
    }

    public void removeTab(ResourceTab tab) {
        tabPane.getTabs().remove(tab);
        allTabs.remove(tab);
    }
}
