package scenes;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.Psu;
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

public class PsuSceneController extends BaseScene<Psu> {

    @FXML
    private TextField tierLowerTextField;

    @FXML
    private TextField tierUpperTextField;

    @FXML
    private TreeView<String> formFactorTreeView;

    @FXML
    private TreeView<String> efficiencyRatingTreeView;

    @FXML
    private TextField wattageLowerTextField;

    @FXML
    private TextField wattageUpperTextField;

    @FXML
    private TreeView<String> modularityTreeView;

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
            this.productList = data.getPsuList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.observableList = FXCollections.observableList(this.productList);
        this.filteredList = new FilteredList<>(observableList, cpu -> true);
        //initialize the tableview
        tableView.getColumns().addAll(Psu.getColumns());
        tableView.getItems().addAll(filteredList);
        //initialize the TreeView Filters
        CheckBoxRoot nameRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(nameRoot.getRoot());

        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctNames());
        this.nameTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.nameTreeView.setRoot(brandRoot.getRoot());

        CheckBoxRoot formFactorRoot = new CheckBoxRoot(this.getDistinctFormFactors());
        this.formFactorTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.formFactorTreeView.setRoot(formFactorRoot.getRoot());

        CheckBoxRoot modularityRoot = new CheckBoxRoot(this.getDistinctModularity());
        this.modularityTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.modularityTreeView.setRoot(modularityRoot.getRoot());

        CheckBoxRoot efficiencyRatingRoot = new CheckBoxRoot(this.getDistinctEfficiencyRating());
        this.efficiencyRatingTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.efficiencyRatingTreeView.setRoot(efficiencyRatingRoot.getRoot());

        //initialize the TextFields filters
    }

    private List<String> getDistinctEfficiencyRating() {
        return new ArrayList(this.productList.stream().map(Psu::getEfficiencyRating).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctModularity() {
        return new ArrayList(this.productList.stream().map(Psu::getModular).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctFormFactors() {
        return new ArrayList(this.productList.stream().map(Psu::getFormFactor).distinct().collect(Collectors.toList()));
    }
}
