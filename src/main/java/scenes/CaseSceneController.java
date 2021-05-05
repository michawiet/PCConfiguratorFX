package scenes;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.Case;
import helpers.CheckBoxRoot;
import helpers.DatabaseData;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CaseSceneController extends BaseScene<Case> {

    @FXML
    private TreeView<String> powerSupplyStandardTreeView;

    @FXML
    private TreeView<String> maxMotherboardSizeTreeView;

    @FXML
    private TreeView<String> typeTreeView;

    @FXML
    private TreeView<String> sidePanelWindowTreeView;
    
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
            this.productList = data.getCaseList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.observableList = FXCollections.observableList(this.productList);
        this.filteredList = new FilteredList<>(observableList, cpu -> true);
        //initialize the tableview
        tableView.getColumns().addAll(Case.getColumns());
        tableView.getItems().addAll(filteredList);
        //initialize the TreeView Filters
        CheckBoxRoot nameRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(nameRoot.getRoot());

        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctNames());
        this.nameTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.nameTreeView.setRoot(brandRoot.getRoot());

        CheckBoxRoot standardRoot = new CheckBoxRoot(this.getDistinctStandards());
        this.powerSupplyStandardTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.powerSupplyStandardTreeView.setRoot(standardRoot.getRoot());

        CheckBoxRoot maxMotherboardSizeRoot = new CheckBoxRoot(this.getDistinctMaxMotherboardSizes());
        this.maxMotherboardSizeTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.maxMotherboardSizeTreeView.setRoot(maxMotherboardSizeRoot.getRoot());

        CheckBoxRoot typeRoot = new CheckBoxRoot(this.getDistinctTypes());
        this.typeTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.typeTreeView.setRoot(typeRoot.getRoot());

        CheckBoxRoot sidePanelWindowRoot = new CheckBoxRoot(this.getDistinctSidePanelWindows());
        this.sidePanelWindowTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.sidePanelWindowTreeView.setRoot(sidePanelWindowRoot.getRoot());
        //initialize the TextFields filters
    }

    private List<String> getDistinctSidePanelWindows() {
        return new ArrayList(this.productList.stream().map(Case::getSidePanelWindow).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctTypes() {
        return new ArrayList(this.productList.stream().map(Case::getType).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctMaxMotherboardSizes() {
        return new ArrayList(this.productList.stream().map(Case::getMaxMotherboardSize).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctStandards() {
        return new ArrayList(this.productList.stream().map(Case::getPowerSupplyStandard).distinct().collect(Collectors.toList()));
    }
}
