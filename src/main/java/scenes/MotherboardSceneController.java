package scenes;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.Motherboard;
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

public class MotherboardSceneController extends BaseScene<Motherboard> {

    @FXML
    private TextField tierLowerTextField;

    @FXML
    private TextField tierUpperTextField;

    @FXML
    private TreeView<String> chipsetTreeView;

    @FXML
    private TreeView<String> socketTreeView;

    @FXML
    private TreeView<String> formFactorTreeView;

    @FXML
    private TextField memorySlotsLowerTextField;

    @FXML
    private TextField minMemoryCapacityUpperTextField;

    @FXML
    private TextField maxMemoryCapacityUpperTextField;

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
            this.productList = data.getMotherboardList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.observableList = FXCollections.observableList(this.productList);
        this.filteredList = new FilteredList<>(observableList, cpu -> true);
        //initialize the tableview
        tableView.getColumns().addAll(Motherboard.getColumns());
        tableView.getItems().addAll(filteredList);
        //initialize the TreeView Filters
        CheckBoxRoot nameRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(nameRoot.getRoot());

        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctNames());
        this.nameTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.nameTreeView.setRoot(brandRoot.getRoot());

        CheckBoxRoot chipsetRoot = new CheckBoxRoot(this.getDistinctChipsets());
        this.chipsetTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.chipsetTreeView.setRoot(chipsetRoot.getRoot());

        CheckBoxRoot socketRoot = new CheckBoxRoot(this.getDistinctSockets());
        this.socketTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.socketTreeView.setRoot(socketRoot.getRoot());

        CheckBoxRoot formFactorRoot = new CheckBoxRoot(this.getDistinctFormFactors());
        this.formFactorTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.formFactorTreeView.setRoot(formFactorRoot.getRoot());

        //initialize the TextFields filters
    }

    private List<String> getDistinctFormFactors() {
        return new ArrayList(this.productList.stream().map(Motherboard::getFormFactor).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctSockets() {
        return new ArrayList(this.productList.stream().map(Motherboard::getSocket).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctChipsets() {
        return new ArrayList(this.productList.stream().map(Motherboard::getChipset).distinct().collect(Collectors.toList()));
    }


}
