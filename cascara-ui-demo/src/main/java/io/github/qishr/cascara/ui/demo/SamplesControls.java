package io.github.qishr.cascara.ui.demo;

import java.time.LocalDate;
import java.util.Arrays;

import io.github.qishr.cascara.common.diagnostic.GlobalReporter;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.diagnostic.code.GenericDiagnosticCode;
import io.github.qishr.cascara.ui.language.Localization;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SamplesControls {
    private static final Reporter REPORTER = GlobalReporter.forClass(SamplesControls.class);

    private VBox view;

    public SamplesControls() {
        view = buildControlShowcase();
    }

    public VBox getView() {
        return view;
    }

    /**
     * Creates a VBox containing a GridPane with all controls.
     * @return The VBox container.
     */
    public VBox buildControlShowcase() {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.TOP_CENTER);

        // --- Helper for creating a titled TitledPane for each control ---
        int row = 0;
        int col = 0;

        Button button = new Button("Button");
        Localization.bind(button, "title.button");

        ToggleButton toggleButton = new ToggleButton("Toggle Button");
        Localization.bind(toggleButton, "title.toggle-button");

        TextField textField = new TextField("Text Field");
        Localization.bind(textField, "title.text-field");

        // The controls list is defined here. We will iterate through them to place them in the grid.
        Node[] controls = new Node[] {
            // Row 0
            createHyperlink(),
            createLabel(),
            button,
            createMenuButton(),

            // Row 1
            createSplitMenuButton(),
            toggleButton,
            createRadioButton(),
            createCheckBox(),

            // Row 2
            createChoiceBox(),
            createComboBox(),
            createDatePicker(),
            new ColorPicker(),

            // Row 3
            textField,
            new PasswordField(),
            createSlider(),
            createProgressBar(),
        };

        // --- Populate the 4x4 Grid ---
        for (Node control : controls) {
            TitledPane titledPane = new TitledPane(
                control.getClass().getSimpleName(), // Use class name as title
                control
            );
            titledPane.setCollapsible(false); // Prevents collapsing

            // Set max width to force controls to stretch and align in the grid
            titledPane.setMaxWidth(Double.MAX_VALUE);
            // grid.setBackground(Background.fill(Paint.valueOf("#14452f")));

            // Ensure the control itself takes up space
            if (control instanceof Control) {
                ((Control)control).setMaxWidth(Double.MAX_VALUE);
            }

            grid.add(titledPane, col, row);

            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }

        // --- VBox Setup ---
        VBox box = new VBox();
        box.getChildren().add(grid);
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(20);
        box.setPadding(new Insets(20, 10, 10, 10));

        // vBox.setPadding(new Insets(10));

        return box;
    }

    private Node createHyperlink() {
        Hyperlink hyperlink = new Hyperlink("Hyperlink");
        Localization.bind(hyperlink, "title.hyperlink");
        hyperlink.setId("myHyperlink");
        hyperlink.setOnMouseClicked(mouse-> {
            Node node = view.lookup("#myHyperlink");
            printComputedCssValuesFixed(node);
        });
        return hyperlink;
    }

    private Node createLabel() {
        Label label = new Label("label");
        Localization.bind(label, "title.label");
        label.setId("myLabel");
        label.setOnMouseClicked(mouse-> {
            Node node = view.lookup("#myLabel");
            printComputedCssValuesFixed(node);
        });
        return label;
    }

    private Node createRadioButton() {
        RadioButton radioButton = new RadioButton("Radio Button");
        Localization.bind(radioButton, "title.radio-button");
        radioButton.setId("myRadionButton");
        radioButton.setOnMouseClicked(mouse-> {
            Node node = view.lookup("#myRadionButton");
            printComputedCssValuesFixed(node);
        });
        return radioButton;
    }
    private Node createCheckBox() {
        CheckBox checkBox = new CheckBox("Check Box");
        Localization.bind(checkBox, "title.check-box");
        checkBox.setId("myCheckBox");
        checkBox.setOnMouseClicked(mouse-> {
            Node node = view.lookup("#myCheckBox");
            printComputedCssValuesFixed(node);
        });
        return checkBox;
    }

    // --- Control Creation Helpers ---
    private Node createMenuButton() {
        MenuButton mb = new MenuButton("MenuButton");
        Localization.bind(mb, "title.menu-button");
        mb.getItems().add(new MenuItem("Item 1"));
        return mb;
    }

    private Node createSplitMenuButton() {
        SplitMenuButton smb = new SplitMenuButton(new MenuItem("Item 1"));
        Localization.bind(smb, "title.split-menu-button");
        smb.setText("SplitMenuButton");
        return smb;
    }

    private Node createChoiceBox() {
        ChoiceBox<String> cb = new ChoiceBox<>();
        cb.getItems().addAll(Arrays.asList(
            "Choice A", "Choice B"
        ));
        cb.setValue("Choice A");
        cb.setId("myChoiceBox");
        cb.setOnMouseClicked(mouse-> {
            Node node = view.lookup("#myChoiceBox");
            printComputedCssValuesFixed(node);
        });
        return cb;
    }

    private Node createComboBox() {
        ComboBox<String> cbb = new ComboBox<>();
        cbb.getItems().addAll(Arrays.asList(
            "Combo 1", "Combo 2"
        ));
        cbb.setValue("Combo 1");
        cbb.setId("myComboBox");
        cbb.setOnMouseClicked(mouse-> {
            Node node = view.lookup("#myComboBox");
            printComputedCssValuesFixed(node);
        });
        return cbb;
    }

    private Node createDatePicker() {
        DatePicker datePicker = new DatePicker(LocalDate.now());
        Localization.bindLocale(datePicker);
        datePicker.setId("myDatePicker");
        datePicker.setOnMouseClicked(mouse-> {
            Node node = view.lookup("#myDatePicker");
            printComputedCssValuesFixed(node);
        });
        return datePicker;
    }

    private Node createSlider() {
        Slider slider = new Slider(0, 100, 50);
        slider.setId("mySlider");
        slider.setShowTickLabels(true);
        slider.setOnMouseClicked(mouse-> {
            Node node = view.lookup("#mySlider");
            printComputedCssValuesFixed(node);
        });
        return slider;
    }

    private Node createProgressBar() {
        ProgressBar pb = new ProgressBar(0.6);
        return pb;
    }

        public void printComputedCssValuesFixed(Node node) {

        REPORTER.info(GenericDiagnosticCode.INFO, "--- Computed CSS Values for Node: " + node.getClass().getSimpleName() + " ---");

        // 1. Iterate using raw types (or the less restrictive wildcard form)
        for (CssMetaData<?, ?> metaData : node.getCssMetaData()) {
            try {
                // 2. Cast the metaData to its most basic Styleable form.
                //    This is an unchecked operation, but is necessary due to type erasure
                //    and the structure of the JavaFX CSS internal API.
                @SuppressWarnings("unchecked")
                CssMetaData<Styleable, ?> styleableMetaData = (CssMetaData<Styleable, ?>) metaData;

                // 3. Now, call the method, casting the Node to the required Styleable interface
                @SuppressWarnings("unchecked")
                StyleableProperty<Object> styleableProperty =
                    (StyleableProperty<Object>) styleableMetaData.getStyleableProperty((Styleable) node);

                if (styleableProperty != null) {
                    Object computedValue = styleableProperty.getValue();

                    REPORTER.debug(
                        "%-25s: %s",
                        metaData.getProperty(),
                        computedValue
                    );
                } else {
                    REPORTER.debug("%-25s: (Property instance not found)%n", metaData.getProperty());
                }
            } catch (Exception e) {
                // If the property lookup fails (e.g., internal error), log the exception
                REPORTER.error(GenericDiagnosticCode.ERROR, "Error fetching "+metaData.getProperty()+": "+e.getMessage()+"\n");
            }
        }

        REPORTER.debug("--------------------------------------------------");
    }
}
