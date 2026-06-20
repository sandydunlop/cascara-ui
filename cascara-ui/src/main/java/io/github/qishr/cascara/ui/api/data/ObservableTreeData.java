package io.github.qishr.cascara.ui.api.data;

import java.util.Map;

import io.github.qishr.cascara.common.data.TreeData;
import javafx.beans.property.Property;

public interface ObservableTreeData<T extends ObservableTreeData<T,V>,V> extends ObservableTableData, TreeData<T,V> {
    String getNodeName();
    String getTreePath();
    boolean isBranch();
    Map<String,Property<?>> getDataContext();
}
