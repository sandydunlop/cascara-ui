package io.github.qishr.cascara.ui.demo;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SamplesTabular {
    private VBox view;

    @SuppressWarnings("rawtypes")
    ListView listView;
    @SuppressWarnings("rawtypes")
    TableView tableView;
    @SuppressWarnings("rawtypes")
    TreeView treeView;
    @SuppressWarnings("rawtypes")
    TreeTableView treeTableView;

    public SamplesTabular() {
        view = buildTablesAndTreesShowcase();
    }

    public VBox getView() {
        return view;
    }

    private VBox buildTablesAndTreesShowcase() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        listView = buildListView();
        tableView = buildTableView();
        treeView = buildTreeView();
        treeTableView = buildTreeTableView();
        gridPane.add(listView, 0, 0, 1, 1);
        gridPane.add(tableView, 1, 0, 1, 1);
        gridPane.add(treeView, 0, 1, 1, 1);
        gridPane.add(treeTableView, 1, 1, 1, 1);
        VBox.setVgrow(gridPane, Priority.ALWAYS);
        HBox.setHgrow(gridPane, Priority.ALWAYS);


        CheckBox cellCheckBox = new CheckBox("Cell Selection");
        cellCheckBox.setOnAction(action -> {
            tableView.getSelectionModel().setCellSelectionEnabled(cellCheckBox.isSelected());
            treeTableView.getSelectionModel().setCellSelectionEnabled(cellCheckBox.isSelected());
        });

        VBox samples = new VBox(cellCheckBox, gridPane);
        samples.setSpacing(20);
        samples.setPadding(new Insets(20, 10, 10, 10));
        return samples;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private ListView buildListView() {
        ListView view = new ListView<>();
        view.getItems().add("Item 1");
        view.getItems().add("Item 2");
        view.getItems().add("Item 3");
        return view;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private TableView buildTableView() {
        TableView view = new TableView<>();

        // 1. Instantiate the Manager
        // CellReferenceManager<Person, String> manager = new CellReferenceManager<>();

        TableColumn<Person, String> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Person, String> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        view.getColumns().add(column1);
        view.getColumns().add(column2);
        view.getItems().add(new Person("John", "Doe"));
        view.getItems().add(new Person("Jane", "Deer"));

        // AbstractPage page = this;
        view.setId("myTable");

        return view;
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    private TreeView buildTreeView() {
        TreeItem rootItem = new TreeItem("Tutorials");

        TreeItem webItem = new TreeItem("Web Tutorials");
        webItem.getChildren().add(new TreeItem("HTML  Tutorial"));
        webItem.getChildren().add(new TreeItem("HTML5 Tutorial"));
        webItem.getChildren().add(new TreeItem("CSS Tutorial"));
        webItem.getChildren().add(new TreeItem("SVG Tutorial"));
        rootItem.getChildren().add(webItem);

        TreeItem javaItem = new TreeItem("Java Tutorials");
        javaItem.getChildren().add(new TreeItem("Java Language"));
        javaItem.getChildren().add(new TreeItem("Java Collections"));
        javaItem.getChildren().add(new TreeItem("Java Concurrency"));
        rootItem.getChildren().add(javaItem);

        TreeView treeView = new TreeView();
        treeView.setRoot(rootItem);
        return treeView;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private TreeTableView buildTreeTableView() {
        TreeTableView<Car> treeTableView = new TreeTableView<Car>();

        TreeTableColumn<Car, String> treeTableColumn1 = new TreeTableColumn<>("Brand");
        TreeTableColumn<Car, String> treeTableColumn2 = new TreeTableColumn<>("Model");

        treeTableColumn1.setCellValueFactory(new TreeItemPropertyValueFactory<>("brand"));
        treeTableColumn2.setCellValueFactory(new TreeItemPropertyValueFactory<>("model"));

        treeTableView.getColumns().add(treeTableColumn1);
        treeTableView.getColumns().add(treeTableColumn2);

        TreeItem mercedes1 = new TreeItem(new Car("Mercedes", "SL500"));
        TreeItem mercedes2 = new TreeItem(new Car("Mercedes", "SL500 AMG"));
        // TreeItem mercedes3 = new TreeItem(new Car("Mercedes", "CLA 200"));

        TreeItem mercedes = new TreeItem(new Car("Mercedes", "..."));
        mercedes.getChildren().add(mercedes1);
        mercedes.getChildren().add(mercedes2);

        TreeItem audi1 = new TreeItem(new Car("Audi", "A1"));
        TreeItem audi2 = new TreeItem(new Car("Audi", "A5"));
        TreeItem audi3 = new TreeItem(new Car("Audi", "A7"));

        TreeItem audi = new TreeItem(new Car("Audi", "..."));
        audi.getChildren().add(audi1);
        audi.getChildren().add(audi2);
        audi.getChildren().add(audi3);

        TreeItem cars = new TreeItem(new Car("Cars", "..."));
        cars.getChildren().add(audi);
        cars.getChildren().add(mercedes);

        treeTableView.setRoot(cars);

        return treeTableView;
    }

    public static class Person {
        String firstName = "";
        String lastName = "";

        public Person(String fn, String ln) {
            firstName = fn;
            lastName = ln;
        }

        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        public String getLastName() {
            return lastName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    public class Car {

        private String brand = null;
        private String model = null;

        public Car() {
        }

        public Car(String brand, String model) {
            this.brand = brand;
            this.model = model;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }
}
