package io.github.qishr.cascara.ui.demo;

import io.github.qishr.cascara.ui.demo.SampleData.SampleEnum;
import io.github.qishr.cascara.ui.form.Field;
import io.github.qishr.cascara.ui.form.ObjectFieldFactory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SamplesSchemaDriven {
    private VBox view = new VBox(20);

    public SamplesSchemaDriven() {
        SampleData sampleData = new SampleData();
        sampleData.sampleEnum.set(SampleEnum.ONE);

        ObjectFieldFactory factory = new ObjectFieldFactory(sampleData);

        Node[] controls = new Node[] {
            // Row 0
            factory.createField("sampleEnum"),
            factory.createField("sampleText")
        };

        GridPane grid = layout(controls);

        view.getChildren().add(grid);
        view.setAlignment(Pos.TOP_CENTER);
        view.setPadding(new Insets(20, 10, 10, 10));
    }

    public VBox getView() {
        return view;
    }

    private GridPane layout(Node[] controls) {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.TOP_CENTER);

        int row = 0;
        int col = 0;

        for (Node control : controls) {
            String title = control.getClass().getSimpleName();
            if (control instanceof Field field) {
                title = field.getName();
            }
            TitledPane titledPane = new TitledPane(
                title,
                control
            );
            titledPane.setCollapsible(false);

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
        return grid;
    }
}
