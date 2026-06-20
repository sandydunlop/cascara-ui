package io.github.qishr.cascara.ui.demo;

import io.github.qishr.cascara.ui.language.Localization;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class Samples {
    private VBox view;

    public Samples() {
        view = new VBox(16);
        view.setPadding(new Insets(0,0,0,0));

        TabPane tabs = new TabPane();

        Tab controls = new Tab("Controls");
        Localization.bind(controls, "title.controls");
        controls.setContent(new SamplesControls().getView());
        controls.setClosable(false);
        tabs.getTabs().add(controls);

        Tab lists = new Tab("Lists");
        Localization.bind(lists, "title.lists");
        lists.setContent(new SamplesTabular().getView());
        lists.setClosable(false);
        tabs.getTabs().add(lists);

        // Tab boxes = new Tab("Boxes");
        // Localization.bind(boxes, "title.boxes");
        // boxes.setContent(buildBoxesShowcase());
        // boxes.setClosable(false);
        // tabs.getTabs().add(boxes);

        Tab boxes = new Tab("Schema-Driven");
        Localization.bind(boxes, "title.schema-driven-controls");
        boxes.setContent(new SamplesSchemaDriven().getView());
        boxes.setClosable(false);
        tabs.getTabs().add(boxes);

        view.getChildren().addAll(tabs);
    }

    public VBox getView() { return view; }

    // public VBox buildBoxesShowcase() {
    //     Label test = new Label("Test");
    //     TitledPane titledPane = new TitledPane();
    //     titledPane.setText("Text");
    //     titledPane.setContent(test);
    //     VBox box = new VBox();
    //     box.setSpacing(20);
    //     box.setPadding(new Insets(20, 10, 10, 10));
    //     box.getChildren().addAll(titledPane);
    //     return box;
    // }
}
