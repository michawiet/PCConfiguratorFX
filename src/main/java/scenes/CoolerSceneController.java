package scenes;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.Cooler;
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

public class CoolerSceneController extends BaseScene<Cooler> {

    @FXML
    private TextField tierLowerTextField;

    @FXML
    private TextField tierUpperTextField;

    @FXML
    private TextField noiseLevelLowerTextField;

    @FXML
    private TextField noiseLevelUpperTextField;

    @FXML
    private TreeView<String> coolerTypeTreeView;

    @FXML
    private TreeView<String> coolerPurposeTreeView;

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
            this.productList = data.getCoolerList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.observableList = FXCollections.observableList(this.productList);
        this.filteredList = new FilteredList<>(observableList, cooler -> true);
        //initialize the tableview
        tableView.getColumns().addAll(Cooler.getColumns());
        tableView.getItems().addAll(filteredList);
        //initialize the TreeView Filters
        CheckBoxRoot nameRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(nameRoot.getRoot());

        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctNames());
        this.nameTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.nameTreeView.setRoot(brandRoot.getRoot());

        CheckBoxRoot coolerTypeRoot = new CheckBoxRoot(this.getDistinctCoolerTypes());
        this.coolerTypeTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.coolerTypeTreeView.setRoot(coolerTypeRoot.getRoot());

        CheckBoxRoot purposeRoot = new CheckBoxRoot(this.getDistinctPurpose());
        this.coolerPurposeTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.coolerPurposeTreeView.setRoot(purposeRoot.getRoot());

        //initialize the TextFields filters
    }

    private List<String> getDistinctPurpose() {
        return new ArrayList(this.productList.stream().map(Cooler::isForWorkstation).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctCoolerTypes() {
        return new ArrayList(this.productList.stream().map(Cooler::getCoolerType).distinct().collect(Collectors.toList()));
    }
}
