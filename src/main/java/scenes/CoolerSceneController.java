package scenes;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.Cooler;
import helpers.CheckBoxRoot;
import helpers.ComboBoxMinMaxValueController;
import helpers.DatabaseData;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CoolerSceneController extends BaseScene<Cooler> {

    @FXML
    private ComboBox<Integer> tierMinComboBox;

    @FXML
    private ComboBox<Integer> tierMaxComboBox;

    @FXML
    private TextField noiseLevelLowerTextField;

    @FXML
    private TextField noiseLevelUpperTextField;

    @FXML
    private TreeView<String> coolerTypeTreeView;

    @FXML
    private TreeView<String> coolerPurposeTreeView;

    private ComboBoxMinMaxValueController tierController;

    @Override
    public void initialize() {
        //initialize the lists
        DatabaseData data = new DatabaseData();
        try {
            this.productList = data.getCoolerList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.filteredList = new FilteredList<>(FXCollections.observableList(this.productList));
        SortedList<Cooler> sortedList = new SortedList<>(this.filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        //initialize the tableview
        tableView.getColumns().addAll(Cooler.getColumns());
        tableView.setItems(sortedList);
        //initialize the TreeView Filters
        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(brandRoot.getRoot());

        CheckBoxRoot coolerTypeRoot = new CheckBoxRoot(this.getDistinctCoolerTypes());
        this.coolerTypeTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.coolerTypeTreeView.setRoot(coolerTypeRoot.getRoot());

        CheckBoxRoot purposeRoot = new CheckBoxRoot(this.getDistinctPurpose());
        this.coolerPurposeTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.coolerPurposeTreeView.setRoot(purposeRoot.getRoot());

        //initialize the TextFields filters
        this.tierController = new ComboBoxMinMaxValueController(this.getDistinctTiers(), this.tierMinComboBox, this.tierMaxComboBox);
    }

    @FXML
    void addAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainSceneController.fxml"));
        Main.getPrimaryScene().setRoot(root);
    }

    @FXML
    void applyFilters(ActionEvent event) {
        Predicate<Cooler> brand = (o) -> getSelectedValues(brandTreeView).contains(o.getBrand());
        Predicate<Cooler> type = (o) -> getSelectedValues(coolerTypeTreeView).contains(o.getCoolerType());
        Predicate<Cooler> purpose = (o) -> getSelectedValues(coolerPurposeTreeView).contains(o.isForWorkstation());
        Predicate<Cooler> tier = (o) -> (o.getTier() >= tierMinComboBox.getValue()) &&
                (o.getTier() <= tierMaxComboBox.getValue());

        this.filteredList.setPredicate(brand.and(type).and(purpose).and(tier));
    }

    @FXML
    void resetFilters(ActionEvent event) {

    }

    @FXML
    void tierMaxComboBoxUpdate(ActionEvent event) {
        this.tierController.maxComboBoxUpdated();
    }

    @FXML
    void tierMinComboBoxUpdate(ActionEvent event) {
        this.tierController.minComboBoxUpdated();
    }

    private List<String> getDistinctPurpose() {
        return this.productList.stream().map(Cooler::isForWorkstation).distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctCoolerTypes() {
        return this.productList.stream().map(Cooler::getCoolerType).distinct().collect(Collectors.toList());
    }

    private List<Integer> getDistinctTiers() {
        return this.productList.stream().map(Cooler::getTier).sorted().distinct().collect(Collectors.toList());
    }
}
