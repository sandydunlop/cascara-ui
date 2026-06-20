package io.github.qishr.cascara.ui.api.data;

import java.util.Map;

import io.github.qishr.cascara.common.data.TableData;
import javafx.beans.Observable;

public interface ObservableTableData extends TableData {
    Observable[] getObservables();
    Map<String,Observable> getObservablesMap();
    Observable getObservable(String key);
}
