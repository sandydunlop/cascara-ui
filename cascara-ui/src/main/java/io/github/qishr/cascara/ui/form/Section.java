package io.github.qishr.cascara.ui.form;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class Section extends AbstractFormComponent {

    private Label arrow = new Label("▼ ");
    private HBox collapsibleArea = null;

    public Section() {
        this(null);
    }

    public Section(FieldLabel title) {
        super();
        this.title = title;
        setPadding(new Insets(4, 4, 4, 4));
        HBox.setHgrow(this, Priority.ALWAYS);
    }

    public Section(FieldLabel title, int depth) {
        super();
        this.title = title;
        setPadding(new Insets(4, 4, 4, 4));
    }

    public FieldLabel getTitle() {
        return title;
    }

    @Override
    protected void onTextChanged() {}

    @Override
    protected void performLayout() {
        title.setHeading(true);

        // TODO: This was in the old code. Make it work again.
        // title.getStyleClass().add(StucturalEditorStyle.CATEGORY_HEADING);

        List<Node> nodes = new ArrayList<>();

        if (collapsibleArea == null || collapsibleArea.getChildren().isEmpty()) {
            getChildren().setAll(title);
            if (description != null) {
                getChildren().setAll(description);
            }
        } else {
            HBox headerArea = new HBox();

            arrow.setStyle("-fx-font-family: 'monospace'; -fx-cursor: hand;");
            headerArea.setCursor(Cursor.HAND);
            headerArea.getChildren().add(arrow);
            headerArea.getChildren().add(title);

            // Toggle logic
            headerArea.setOnMouseClicked(e -> {
                boolean isVisible = collapsibleArea.isVisible();
                collapsibleArea.setVisible(!isVisible);
                collapsibleArea.setManaged(!isVisible);
                arrow.setText(!isVisible ? "▼ " : "▶ ");
            });

            collapsibleArea.setSpacing(8);
            collapsibleArea.setPadding(new Insets(16, 4 ,0, 32));

            nodes.add(headerArea);
            if (description != null) {
                getChildren().setAll(description);
            }
            nodes.add(collapsibleArea);
        }
        getChildren().setAll(nodes);
    }


    public Node getCollapsibleContent() {
        return collapsibleArea;
    }

    public void addCollapsibleContent(Node node) {
        if (collapsibleArea == null) {
            collapsibleArea = new HBox();
        }
        collapsibleArea.getChildren().add(node);
        performLayout();
    }
}
