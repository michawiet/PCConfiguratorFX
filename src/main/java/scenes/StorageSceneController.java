package scenes;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.Storage;
import helpers.CheckBoxRoot;
import helpers.DatabaseData;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StorageSceneController extends BaseScene<Storage> {

    @FXML
    private TextField capacityLowerTextField;

    @FXML
    private TextField capacityUpperTextField;

    @FXML
    private TextField tierLowerTextField;

    @FXML
    private TextField tierUpperTextField;

    @FXML
    private TreeView<String> typeTreeView;

    @FXML
    private TreeView<String> formFactorTreeView;

    @FXML
    private TreeView<String> interfaceTreeView;

    @FXML
    void AddCpu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainSceneController.fxml"));
        Main.getPrimaryScene().setRoot(root);
    }

    @FXML
    void ApplyFilters(ActionEvent event) {

    }

    @FXML
    void ResetFilters(ActionEvent event) {

    }

    @Override
    public void initialize() {
        //initialize the lists
        DatabaseData data = new DatabaseData();
        try {
            this.productList = data.getStorageList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.observableList = FXCollections.observableList(this.productList);
        this.filteredList = new FilteredList<>(observableList, cpu -> true);
        //initialize the tableview
        tableView.getColumns().addAll(Storage.getColumns());
        tableView.getItems().addAll(filteredList);
        //initialize the TreeView Filters
        CheckBoxRoot nameRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(nameRoot.getRoot());

        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctNames());
        this.nameTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.nameTreeView.setRoot(brandRoot.getRoot());

        CheckBoxRoot typeRoot = new CheckBoxRoot(this.getDistinctTypes());
        this.typeTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.typeTreeView.setRoot(typeRoot.getRoot());

        CheckBoxRoot formFactorRoot = new CheckBoxRoot(this.getDistinctFormFactors());
        this.formFactorTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.formFactorTreeView.setRoot(formFactorRoot.getRoot());

        CheckBoxRoot interfaceRoot = new CheckBoxRoot(this.getDistinctInterfaces());
        this.interfaceTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.interfaceTreeView.setRoot(interfaceRoot.getRoot());

        //initialize the TextFields filters
    }

    private List<String> getDistinctInterfaces() {
        return new ArrayList(this.productList.stream().map(Storage::getConnectionInterface).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctFormFactors() {
        return new ArrayList(this.productList.stream().map(Storage::getFormFactor).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctTypes() {
        return new ArrayList(this.productList.stream().map(Storage::getType).distinct().collect(Collectors.toList()));
    }
}
