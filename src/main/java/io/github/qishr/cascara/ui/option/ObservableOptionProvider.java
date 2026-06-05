package io.github.qishr.cascara.ui.option;

public interface ObservableOptionProvider extends OptionProvider {
    void addListener(Runnable listener);
    void removeListener(Runnable listener);
}