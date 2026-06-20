package io.github.qishr.cascara.ui.data;

import java.util.HashMap;
import java.util.Map;

import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.schema.annotation.SchemaProperty;
import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.ui.api.data.ObservableTreeData;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public abstract class ObservableTreeNode<T extends ObservableTreeNode<T,V>,V extends Object> extends ObservableObject implements ObservableTreeData<T,V> {
    private final ObjectProperty<SchemaType> schemaType = new SimpleObjectProperty<>();
    private final ObjectProperty<SchemaNode> schema = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<V> physicalValue;
    private final SimpleObjectProperty<T> parent;
    private final ObservableList<T> children = FXCollections.observableArrayList();

    @SchemaProperty
    private ObjectProperty<String> nodeName; // AbstractObservableAstNode.NODE_NAME_PROPERTY imust match this exactly

    public ObservableTreeNode(T parent, String nodeName) {
        super();
        this.nodeName.set(nodeName);
        this.parent = new SimpleObjectProperty<>(this, "parent", parent);
        this.physicalValue = new SimpleObjectProperty<>(parent, "physicalValue");
        setupListeners();
    }

    protected abstract T self();

    //
    // Properties
    //

    public ObjectProperty<SchemaType> schemaTypeProperty() { return schemaType; }
    public ObjectProperty<SchemaType> typeProperty() { return schemaType; }
    public ObjectProperty<SchemaNode> schemaProperty() { return schema; }

    public final ObjectProperty<String> nodeNameProperty() {return nodeName;}
    public final SimpleObjectProperty<T> parentProperty() {return parent;}
    public SimpleObjectProperty<V> payloadProperty() { return physicalValue; }

    //
    // Identity
    //

    public final String getNodeName() {return this.nodeName.get();}
    public final void setNodeName(String v) {this.nodeName.set(v);}

    //
    // Schema
    //

    public SchemaNode getSchema() {
        return schema.get();
    }
    public void setSchema(SchemaNode schema) { this.schema.set(schema); }

    public final SchemaType getSchemaType() {
        return schema.get() == null ? SchemaType.NULL : schema.get().getType();
    }

    //
    // Structure
    //

    /// Called when a child is added to this node.
    protected void onChildAdded(T node) {}

    /// Called when a child is removed from this node.
    protected void onChildRemoved(T node) {}

    /// Called when the value held by this node changes.
    protected void onValueChanged(V value) {}

    public T getParent() {return this.parent.get();}

    public ObservableList<T> getChildren() { return children; }

    @Override
    public final void setParent(T v) {
        this.parent.set(v);
    }

    public T getChild(String name) {
        for (T child : children) {
            if (child.internalGetName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    @Override
    public boolean isBranch() {
        // If the list has items, it's a branch.
        if (!children.isEmpty()) {
            return true;
        }

        // Otherwise, ask the specific implementation if it "expects" children.
        // This defaults to false so non-lazy trees don't show phantom arrows.
        return canHaveChildren();
    }

    /// Override this only in classes that load data on demand.
    protected boolean canHaveChildren() {
        return false;
    }

    public String getTreePathInsertParent(String parentName) {
        String path = getTreePath();
        int i = path.indexOf("/");
        String root = path.substring(0, i);
        String subPath = path.substring(i);
        return root + "/" + parentName + subPath;
    }

    public String getTreePath() {
        String path = internalGetName();
        ObservableTreeNode<?,?> current = this;

        while (current.getParent() != null) {
            current = current.getParent();
            if (current.getParent() == null) {
                path = "#/" + path;
            } else {
                path = current.internalGetName() + "/" + path;
            }
        }

        return path;
    }

    public T getByPath(String path) {
        if (path.startsWith("#")) {
            path = path.substring(1);
        }
        String[] segments = path.split("/");
        T currentItem = self();
        for (String segment : segments) {
            if (segment.isEmpty()) continue;

            if (currentItem.getChild(segment) instanceof T item) {
                currentItem = item;
            } else {
                return null;
            }
        }
        return currentItem;
    }

    //
    // Values
    //

    public V getPayload() {
        return physicalValue.getValue();
    }

    public void setPayload(V v) {
        physicalValue.setValue(v);
    }

    public Map<String,Property<?>> getChildMap() {
        return propertyMap(this);
    }

    @Override
    public Map<String,Property<?>> getDataContext() {
        return propertyMap(getParent());
    }

    private Map<String,Property<?>> propertyMap(ObservableTreeNode<T,V> parent) {
        Map<String,Property<?>> contextMap = new HashMap<>();
        if (parent != null) {
            for (ObservableTreeNode<T,V> child : parent.getChildren()) {
                contextMap.put(child.internalGetName(), child.payloadProperty());
            }
        }
        return contextMap;
    }

    //
    // Private Methods
    //

    String internalGetName() {
        return this.nodeName.get();
    }

    private void setupListeners() {
        physicalValue.addListener((obs,old,val) -> {
            onValueChanged(val);
        });
        getChildren().addListener((ListChangeListener.Change<? extends T> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (T item : c.getAddedSubList()) {
                        item.setParent(self());
                        onChildAdded(item);
                    }
                }
                if (c.wasRemoved()) {
                    for (T item : c.getRemoved()) {
                        item.setParent(null);
                        onChildRemoved(item);
                    }
                }
            }
        });
    }
}
