package scenes;

import components.Ram;
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
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.IOException;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RamSceneController extends ComponentScene<Ram> {

    @FXML
    private ComboBox<Integer> speedMinComboBox;

    @FXML
    private ComboBox<Integer> speedMaxComboBox;

    @FXML
    private ComboBox<Integer> modulesCountMinComboBox;

    @FXML
    private ComboBox<Integer> modulesCountMaxComboBox;

    @FXML
    private ComboBox<Integer> modulesCapacityMinComboBox;

    @FXML
    private ComboBox<Integer> modulesCapacityMaxComboBox;


    @FXML
    private TextField firstWordLatencyLowerTextField;

    @FXML
    private TextField firstWordLatencyUpperTextField;

    @FXML
    private ComboBox<Integer> casMinComboBox;

    @FXML
    private ComboBox<Integer> casMaxComboBox;

    ComboBoxRangeValueController speedController;
    ComboBoxRangeValueController modulesCountController;
    ComboBoxRangeValueController modulesCapacityController;
    ComboBoxRangeValueController casLatencyController;

    @FXML
    void applyFilters(ActionEvent event) {
        Predicate<Ram> brand = (o) -> getSelectedValues(brandTreeView).contains(o.getBrand());
        Predicate<Ram> speed = (o) -> (o.getSpeed() >= speedMinComboBox.getValue()) &&
                (o.getSpeed() <= speedMaxComboBox.getValue());
        Predicate<Ram> count = (o) -> (o.getModulesCount() >= modulesCountMinComboBox.getValue()) &&
                (o.getModulesCount() <= modulesCapacityMaxComboBox.getValue());
        Predicate<Ram> capacity = (o) -> (o.getModuleCapacityGb() >= modulesCapacityMinComboBox.getValue()) &&
                (o.getModuleCapacityGb() <= modulesCapacityMaxComboBox.getValue());
        Predicate<Ram> cas = (o) -> (o.getCasLatency() >= casMinComboBox.getValue()) &&
                (o.getCasLatency() <= casMaxComboBox.getValue());

        Predicate<Ram> price = (o) -> (o.getPrice() >= getDoubleFromRegionalString(priceLowerTextField.getText())
                && o.getPrice() <= getDoubleFromRegionalString(priceUpperTextField.getText()));

        Predicate<Ram> fw = (o) -> (o.getFirstWordLatencyNs() >= getDoubleFromRegionalString(firstWordLatencyLowerTextField.getText())
                && o.getFirstWordLatencyNs() <= getDoubleFromRegionalString(firstWordLatencyUpperTextField.getText()));

        this.filteredList.setPredicate(brand.and(speed).and(count).and(capacity).and(cas).and(price).and(fw));
    }

    @FXML
    void casMaxComboBoxUpdate(ActionEvent event) {
        casLatencyController.maxComboBoxUpdated();
    }

    @FXML
    void casMinComboBoxUpdate(ActionEvent event) {
        casLatencyController.minComboBoxUpdated();
    }

    @FXML
    void modulesCapacityMaxComboBoxUpdate(ActionEvent event) {
        modulesCapacityController.maxComboBoxUpdated();
    }

    @FXML
    void modulesCapacityMinComboBoxUpdate(ActionEvent event) {
        modulesCapacityController.minComboBoxUpdated();
    }

    @FXML
    void modulesCountMaxComboBoxUpdate(ActionEvent event) {
        modulesCountController.maxComboBoxUpdated();
    }

    @FXML
    void modulesCountMnComboBoxUpdate(ActionEvent event) {
        modulesCountController.minComboBoxUpdated();
    }

    @FXML
    void speedMaxComboBoxUpdate(ActionEvent event) {
        speedController.maxComboBoxUpdated();
    }

    @FXML
    void speedMinComboBoxUpdate(ActionEvent event) {
        speedController.minComboBoxUpdated();
    }

    @Override
    public void initialize() {
        //initialize the lists
        try {
            this.productList = JsonDataGetter.getInstance().getRamList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.filteredList = new FilteredList<>(FXCollections.observableList(this.productList));
        SortedList<Ram> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        //initialize the tableview
        tableView.getColumns().addAll(Ram.getColumns());
        tableView.setItems(sortedList);
        //initialize the TreeView Filters
        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(brandRoot.getRoot());
        //initialize combobox filters
        speedController = new ComboBoxRangeValueController(getDistinctSpeed(), speedMinComboBox, speedMaxComboBox);
        modulesCountController = new ComboBoxRangeValueController(getDistinctModulesCount(), modulesCountMinComboBox, modulesCountMaxComboBox);
        modulesCapacityController = new ComboBoxRangeValueController(getDistinctModuleCapacity(), modulesCapacityMinComboBox, modulesCapacityMaxComboBox);
        casLatencyController = new ComboBoxRangeValueController(getDistinctCasLatency(), casMinComboBox, casMaxComboBox);
        //initialize the TextFields filters
        initPriceTextFields();

        var stats = getFwLatency();
        this.firstWordLatencyLowerTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.firstWordLatencyLowerTextField.setText(String.format("%.2f", stats.getMin()));
        this.firstWordLatencyUpperTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.firstWordLatencyUpperTextField.setText(String.format("%.2f", stats.getMax()));

        this.addTableViewListener();
    }

    private DoubleSummaryStatistics getFwLatency() {
        return this.productList.stream().map(Ram::getFirstWordLatencyNs).mapToDouble(Float::doubleValue).summaryStatistics();
    }

    private List<Integer> getDistinctSpeed() {
        return this.productList.stream().map(Ram::getSpeed).sorted().distinct().collect(Collectors.toList());
    }

    private List<Integer> getDistinctModulesCount() {
        return this.productList.stream().map(Ram::getModulesCount).sorted().distinct().collect(Collectors.toList());
    }

    private List<Integer> getDistinctModuleCapacity() {
        return this.productList.stream().map(Ram::getModuleCapacityGb).sorted().distinct().collect(Collectors.toList());
    }

    private List<Integer> getDistinctCasLatency() {
        return this.productList.stream().map(Ram::getCasLatency).sorted().distinct().collect(Collectors.toList());
    }
}
