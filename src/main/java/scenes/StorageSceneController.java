package scenes;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.Cpu;
import components.Storage;
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
    private ComboBox<Integer> tierMinComboBox;

    @FXML
    private ComboBox<Integer> tierMaxComboBox;

    @FXML
    private TreeView<String> typeTreeView;

    @FXML
    private TreeView<String> formFactorTreeView;

    @FXML
    private TreeView<String> interfaceTreeView;

    ComboBoxMinMaxValueController tierController;

    @FXML
    void addAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainSceneController.fxml"));
        Main.getPrimaryScene().setRoot(root);
    }

    @FXML
    void applyFilters(ActionEvent event) {
        //Update the predicates and apply to the filtered/observable list
        Predicate<Storage> brand = (o) -> getSelectedValues(brandTreeView).contains(o.getBrand());
        Predicate<Storage> type = (o) -> getSelectedValues(typeTreeView).contains(o.getType());
        Predicate<Storage> formFactor = (o) -> getSelectedValues(formFactorTreeView).contains(o.getFormFactor());
        Predicate<Storage> connectionInterface = (o) -> getSelectedValues(interfaceTreeView).contains(o.getConnectionInterface());

        Predicate<Storage> tier = (o) -> (o.getTier() >= tierMinComboBox.getValue()) &&
                (o.getTier() <= tierMaxComboBox.getValue());

        this.filteredList.setPredicate(brand.and(type).and(formFactor).and(connectionInterface).and(tier));
    }

    @FXML
    void resetFilters(ActionEvent event) {

    }

    @FXML
    void tierMaxComboBoxUpdate(ActionEvent event) {
        tierController.maxComboBoxUpdated();
    }

    @FXML
    void tierMinComboBoxUpdate(ActionEvent event) {
        tierController.minComboBoxUpdated();
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
        this.filteredList = new FilteredList<>(FXCollections.observableList(this.productList));
        SortedList<Storage> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        //initialize the tableview
        tableView.getColumns().addAll(Storage.getColumns());
        tableView.setItems(sortedList);
        //initialize the TreeView Filters
        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(brandRoot.getRoot());

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
        tierController = new ComboBoxMinMaxValueController(this.getDistinctTier(), tierMinComboBox, tierMaxComboBox);
    }

    private List<Integer> getDistinctTier() {
        return this.productList.stream().map(Storage::getTier).sorted().distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctInterfaces() {
        return this.productList.stream().map(Storage::getConnectionInterface).distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctFormFactors() {
        return this.productList.stream().map(Storage::getFormFactor).distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctTypes() {
        return this.productList.stream().map(Storage::getType).distinct().collect(Collectors.toList());
    }
}
