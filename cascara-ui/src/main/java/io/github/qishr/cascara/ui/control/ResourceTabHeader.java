package io.github.qishr.cascara.ui.control;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import io.github.qishr.cascara.ui.style.custom.DocumentTabHeaderStyle;


public class ResourceTabHeader extends HBox {
    private static final PseudoClass ACTIVE_PSEUDO_CLASS = PseudoClass.getPseudoClass("active");
    private static final PseudoClass TRANSIENT_PSEUDO_CLASS = PseudoClass.getPseudoClass("transient");

    private static final String RESOURCE_ROOT = "/io/github/qishr/cascara/ui/control/tab/";
    public static final String ICON_CLOSE = RESOURCE_ROOT + "close.svg";
    public static final String ICON_CIRCLE = RESOURCE_ROOT + "circle-solid.svg";
    public static final String ICON_PIN = RESOURCE_ROOT + "pin.svg";
    public static final String ICON_PIN_CIRCLE = RESOURCE_ROOT + "pin-circle.svg";

    private Button button;
    private Label label;

    private BooleanProperty activeProp; // = new SimpleBooleanProperty(this, "active");
    public BooleanProperty activeProperty() { return activeProp; }
    public boolean isActive() { return activeProp.get(); }
    public void setActive(final boolean t) { this.activeProp.set(t); }

    private BooleanProperty transientProp;
    public BooleanProperty transientProperty() {return transientProp;}
    public boolean isTransient() {return transientProp.get();}
    public void setTransient(final boolean t) {this.transientProp.set(t);}

    private BooleanProperty modifiedProp = new SimpleBooleanProperty(this, "modified");
    public BooleanProperty modifiedProperty() {return modifiedProp;}
    public boolean isModified() {return modifiedProp.get();}
    public void setModified(final boolean t) {this.modifiedProp.set(t);}

    private BooleanProperty pinnedProp = new SimpleBooleanProperty(this, "pinned");
    public BooleanProperty pinnedProperty() {return pinnedProp;}
    public boolean isPinned() {return pinnedProp.get();}
    public void setPinned(final boolean pinned) {this.pinnedProp.set(pinned);}

    private SimpleStringProperty titleProp = new SimpleStringProperty(this, "title");
    public SimpleStringProperty titleProperty() {return titleProp;}
    public String getTitle() {return this.titleProp.get();}
    public void setTitle(String text) {this.titleProp.set(text);}

    private OnButtonClickHandler onButtonClick = null;

    public ResourceTabHeader() {
        this("Untitled");
    }

    public ResourceTabHeader(String text) {
        this.activeProp = new BooleanPropertyBase(false) {
            @Override protected void invalidated() {
                pseudoClassStateChanged(ACTIVE_PSEUDO_CLASS, get());
            }
            @Override public Object getBean() { return this; }
            @Override public String getName() { return "active"; }
        };

        this.transientProp = new BooleanPropertyBase(false) {
            @Override protected void invalidated() {
                pseudoClassStateChanged(TRANSIENT_PSEUDO_CLASS, get());
            }
            @Override public Object getBean() { return this; }
            @Override public String getName() { return "transient"; }
        };

        new DocumentTabHeaderStyle().applyTo(this);
        getStyleClass().add(DocumentTabHeaderStyle.DOCUMENT_TAB_HEADER);

        initGraphics();
        registerListeners();
    }

    // Close button

    public interface OnButtonClickHandler {
        void onButtonClick();
    }

    public void setOnCloseButtonClick(OnButtonClickHandler onButtonClick) {
        this.onButtonClick = onButtonClick;
    }

    public void onButtonClick() {
        if (onButtonClick != null) {
            onButtonClick.onButtonClick();
        }
    }

    private void initGraphics() {
        button = new Button("");
        button.getStyleClass().add("tab-button");
        button.setOnAction(click -> {
            onButtonClick();
        });
        setOnMouseEntered(mouse -> updateIcon(true));
        setOnMouseExited(mouse -> updateIcon(false));

        label = new Label(titleProp.get());
        label.getStyleClass().add("tab-label");
        label.setMouseTransparent(true);

        getChildren().addAll(button, label);
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(4);
        updateIcon(false);
    }

    private void updateIcon(boolean isMouseOver) {
        SvgIcon icon = null;
        if (isPinned()) {
            if (isModified()) {
                icon = new SvgIcon(getTextResource(ICON_PIN_CIRCLE), 14);
            } else {
                icon = new SvgIcon(getTextResource(ICON_PIN), 14);
            }
        } else {
            if (isMouseOver) {
                icon = new SvgIcon(getTextResource(ICON_CLOSE), 14);
            } else {
                if (isModified()) {
                    icon = new SvgIcon(getTextResource(ICON_CIRCLE), 14);
                } else if (isActive()){
                    icon = new SvgIcon(getTextResource(ICON_CLOSE), 14);
                }
            }
        }
        button.setGraphic(icon);
    }

    private String getTextResource(String resourcePath) {
        InputStream is = getClass().getResourceAsStream(resourcePath);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (Exception _) {
            return "";
        }
    }

    private void registerListeners() {
        titleProperty().addListener((observable, oldValue, newValue) -> {
            label.setText(titleProperty().get());
        });
        activeProperty().addListener((observable, oldValue, newValue) -> {
            updateIcon(false);
        });
        transientProperty().addListener((observable, oldValue, newValue) -> {
            updateIcon(false);
        });
        modifiedProperty().addListener((observable, oldValue, newValue) -> {
            updateIcon(false);
        });
        pinnedProperty().addListener((observable, oldValue, newValue) -> {
            updateIcon(false);
        });
    }
}