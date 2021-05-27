package scenes;

import components.Storage;
import helpers.CheckBoxRoot;
import helpers.ComboBoxRangeValueController;
import helpers.DecimalTextFormatter;
import helpers.JsonDataGetter;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.IOException;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StorageSceneController extends ComponentScene<Storage> {

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

    ComboBoxRangeValueController tierController;

    @FXML
    private void applyFilters(ActionEvent event) {
        //Update the predicates and apply to the filtered/observable list
        Predicate<Storage> brand = (o) -> getSelectedValues(brandTreeView).contains(o.getBrand());
        Predicate<Storage> type = (o) -> getSelectedValues(typeTreeView).contains(o.getType());
        Predicate<Storage> formFactor = (o) -> getSelectedValues(formFactorTreeView).contains(o.getFormFactor());
        Predicate<Storage> connectionInterface = (o) -> getSelectedValues(interfaceTreeView).contains(o.getConnectionInterface());

        Predicate<Storage> tier = (o) -> (o.getTier() >= tierMinComboBox.getValue()) &&
                (o.getTier() <= tierMaxComboBox.getValue());

        Predicate<Storage> price = (o) -> (o.getPrice() >= getDoubleFromRegionalString(priceLowerTextField.getText())
                && o.getPrice() <= getDoubleFromRegionalString(priceUpperTextField.getText()));

        this.filteredList.setPredicate(brand.and(type).and(formFactor).and(connectionInterface).and(tier).and(price));
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
        try {
            this.productList = JsonDataGetter.getInstance().getStorageList();
        } catch (IOException e) {
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
        tierController = new ComboBoxRangeValueController(this.getDistinctTier(), tierMinComboBox, tierMaxComboBox);

        var stats = getCapacityStats();

        this.capacityLowerTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.capacityLowerTextField.setText(String.format("%d", stats.getMin()));

        this.capacityUpperTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.capacityUpperTextField.setText(String.format("%d", stats.getMax()));

        initPriceTextFields();

        this.addTableViewListener();
    }

    private IntSummaryStatistics getCapacityStats() {
        return this.productList.stream().map(Storage::getCapacityGb).mapToInt(Integer::intValue).summaryStatistics();
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
