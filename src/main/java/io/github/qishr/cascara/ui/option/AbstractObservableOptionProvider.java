package io.github.qishr.cascara.ui.option;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObservableOptionProvider implements ObservableOptionProvider {
    protected final List<Runnable> listeners = new ArrayList<>();
    @Override public void addListener(Runnable listener) { listeners.add(listener); }
    @Override public void removeListener(Runnable listener) { listeners.remove(listener); }
}
