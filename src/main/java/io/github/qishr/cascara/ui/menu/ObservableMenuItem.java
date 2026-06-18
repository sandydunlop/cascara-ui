package io.github.qishr.cascara.ui.menu;

import io.github.qishr.cascara.common.diagnostic.GlobalReporter;
import io.github.qishr.cascara.common.diagnostic.LocalizableRuntimeException;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.diagnostic.code.GenericDiagnosticCode;
import io.github.qishr.cascara.schema.annotation.SchemaDefinition;
import io.github.qishr.cascara.schema.annotation.SchemaProperty;
import io.github.qishr.cascara.ui.api.UiDiagnosticCode;
import io.github.qishr.cascara.ui.api.UiException;
import io.github.qishr.cascara.ui.data.ObservableTreeNode;
import io.github.qishr.cascara.ui.language.Localization;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

@SchemaDefinition
public class ObservableMenuItem extends ObservableTreeNode<ObservableMenuItem, Object> {
    public static final ObservableMenuItem SEPARATOR = new ObservableMenuItem();

    private static final Reporter REPORTER = GlobalReporter.forClass(ObservableMenuItem.class);

    @SchemaProperty
    private ObjectProperty<KeyCombination> accelerator;

    @SchemaProperty
    private ObjectProperty<Runnable> onChoose;

    @SchemaProperty
    private ObjectProperty<Runnable> onShowing;

    @SchemaProperty
    private ObjectProperty<String> text;

    @SchemaProperty
    private ObjectProperty<Boolean> enabled;

    private ObservableMenuItem root;
    private ObservableMenuItem destination;

    private boolean isRoot;
    private boolean isSeparator;
    private boolean isTranslatable;

    private MenuBar menuBar;
    private MenuItem fxMenuItem;

    /// Package-private constructor for separator menu items
    ObservableMenuItem() {
        super(null, "--");
        fxMenuItem = new SeparatorMenuItem();
        isSeparator = true;
    }

    /// Package-private constructor for menu bars
    ObservableMenuItem(MenuBar fxItem) {
        super(null, "#");
        menuBar = fxItem;
        isRoot = true;
    }

    private void translate() {
        String nodeName = nodeNameProperty().get();

        if (nodeName != null) {
            REPORTER.debug("Menu item: " + nodeName);
            if (isTranslatable) {
                REPORTER.debug("  Translating");

                String translationKey;

                if (nodeName.contains("/")) {
                    // Menu item has full translation key path
                    translationKey = nodeName;
                } else if (nodeName.contains(".")) {
                    // Menu item has full translation key name
                    translationKey = nodeName.replace(".", "/");
                } else {
                    // Menu item has only the leaf name
                    REPORTER.debug("  Tree Path = " + getTreePath());

                    translationKey = getTreePathInsertParent("menubar");

                    if (fxMenuItem instanceof Menu) {
                        translationKey = translationKey + "/$title";
                    }
                }

                REPORTER.debug("  Translation Key = " + translationKey);

                Localization.bind(fxMenuItem, translationKey);
            } else {
                REPORTER.debug("  Not translating");
            }
        }
    }

    ObservableMenuItem(String nodeName, String text, MenuItem fxMenuItem) {
        super(null, cleanName(nodeName));

        this.text.set(text);
        fxMenuItem.setText(text);

        if (nodeName != null && !nodeName.startsWith("#")) {
            isTranslatable = true;
        }

        this.fxMenuItem = fxMenuItem;
        if (fxMenuItem instanceof Menu fxMenu) {
            onShowing.addListener(i -> {
                fxMenu.setOnShowing(v -> {
                    if (onShowing.get() != null) {
                        onShowing.get().run();
                    }
                });
            });
        } else {
            onChoose.addListener(i -> {
                fxMenuItem.setOnAction(a -> {
                    if (onChoose.get() != null) {
                        onChoose.get().run();
                    }
                });
            });

            accelerator.addListener(i -> {
                fxMenuItem.setAccelerator(getAccelerator());
            });

            this.text.addListener(i -> {
                fxMenuItem.setText(getText());
            });

            enabled.addListener(i -> {
                fxMenuItem.setDisable(!isEnabled());
            });
        }
    }

    public final ObjectProperty<KeyCombination> acceleratorProperty() { return accelerator; }
    public final ObjectProperty<Runnable> onChooseProperty() { return onChoose; }
    public final ObjectProperty<Runnable> onShowingProperty() { return onShowing; }
    public final ObjectProperty<String> textProperty() { return text; }
    public final ObjectProperty<Boolean> enabledProperty() { return enabled; }

    @Override
    protected ObservableMenuItem self() { return this; }

    public ObservableMenuItem addMenu(String name, String text) {
        ObservableMenuItem menu = ObservableMenuFactory.createMenu(name, text);
        getChildren().add(menu);
        return menu;
    }

    public ObservableMenuItem addMenuItem(String name, String text) {
        ObservableMenuItem menuItem = ObservableMenuFactory.createMenuItem(name, text);
        getChildren().add(menuItem);
        return menuItem;
    }

    public ObservableMenuItem addMenuItem(String name, String text, MenuItem fxItem) {
        ObservableMenuItem menuItem = ObservableMenuFactory.createMenuItem(name, text, fxItem);
        getChildren().add(menuItem);
        return menuItem;
    }

    public void addSeparator() {
        getChildren().add(SEPARATOR);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public MenuItem getMenuItem() { return fxMenuItem; }

    public ObservableMenuItem getDestination() { return destination; }

    public ObservableMenuItem setDestination(ObservableMenuItem menu) {
        this.destination = menu;
        return this;
    }

    public String getText() { return text.get(); }

    public ObservableMenuItem setText(String text) { this.text.set(text); return this; }

    public boolean isEnabled() { return enabled.get(); }

    public ObservableMenuItem setEnabled(boolean v) { enabled.set(v); return this; }

    public final KeyCombination getAccelerator() { return this.accelerator.get(); }

    public ObservableMenuItem setAccelerator(KeyCombination v) {
        this.accelerator.set(v);
        return this;
    }

    public final Runnable getOnShowing() { return this.onShowing.get(); }

    public ObservableMenuItem setOnShowing(Runnable handler) {
        this.onShowing.set(handler);
        return this;
    }

    public final Runnable getOnChoose() { return this.onChoose.get(); }

    public ObservableMenuItem setOnChoose(Runnable handler) {
        this.onChoose.set(handler);
        return this;
    }

    public void choose() {
        if (onChoose.get() != null) {
            onChoose.get().run();
        }
    }

    public boolean isSeparator() { return isSeparator; }

    public void remove() {
        ObservableMenuItem parent = parentProperty().get();
        if (parent != null) {
            parent.getChildren().remove(this);
        }
    }

    //
    // Child Handlers
    //

    @Override
    protected void onChildAdded(ObservableMenuItem child) {
        child.root = root;
        if (child.fxMenuItem instanceof Menu fxMenu) {
            if (isRoot) {
                menuBar.getMenus().add(fxMenu);
            } else {
                if (fxMenuItem instanceof Menu fxParentMenu) {
                    fxParentMenu.getItems().add(fxMenu);
                }
            }
        } else {
            if (isRoot) {
                throw new LocalizableRuntimeException(UiDiagnosticCode.ADD_UNSUPPORTED);
            }
            if (this.fxMenuItem instanceof Menu fxParentMenu) {
                try {
                    if (child.isSeparator) {
                        fxParentMenu.getItems().add(new SeparatorMenuItem());
                    } else {
                        fxParentMenu.getItems().add(child.fxMenuItem);
                    }
                } catch (UnsupportedOperationException e) {
                    throw new UiException(e, GenericDiagnosticCode.UNSUPPORTED_OPERATION, e.getMessage());
                }
            }
        }
        child.translate();
    }

    @Override
    protected void onChildRemoved(ObservableMenuItem child) {
        if (isRoot) {
            menuBar.getMenus().remove(fxMenuItem);
        } else {
            if (this.fxMenuItem instanceof Menu fxParentMenu) {
                fxParentMenu.getItems().remove(child.fxMenuItem);
            }
        }
    }

    private static String cleanName(String name) {
        if (name != null && (name.startsWith("$") || name.startsWith("#"))) {
            return name.substring(1);
        }
        return name;
    }
}
