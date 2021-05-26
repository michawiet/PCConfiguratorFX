package scenes;

import components.Motherboard;
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
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MotherboardSceneController extends ComponentScene<Motherboard> {

    @FXML
    private TreeView<String> chipsetTreeView;

    @FXML
    private TreeView<String> socketTreeView;

    @FXML
    private TreeView<String> formFactorTreeView;

    @FXML
    private TextField tierMinTextField;

    @FXML
    private TextField tierMaxTextField;

    @FXML
    private ComboBox<Integer> slotsMinComboBox;

    @FXML
    private ComboBox<Integer> slotsMaxComboBox;

    @FXML
    private ComboBox<Integer> memoryMinComboBox;

    @FXML
    private ComboBox<Integer> memoryMaxComboBox;

    ComboBoxRangeValueController slotsController;
    ComboBoxRangeValueController memoryMaxController;
    ComboBoxRangeValueController tierController;

    @FXML
    void applyFilters(ActionEvent event) {
        Predicate<Motherboard> brand = (o) -> getSelectedValues(brandTreeView).contains(o.getBrand());
        Predicate<Motherboard> chipset = (o) -> getSelectedValues(chipsetTreeView).contains(o.getChipset());
        Predicate<Motherboard> socket = (o) -> getSelectedValues(socketTreeView).contains(o.getSocket());
        Predicate<Motherboard> formFactor = (o) -> getSelectedValues(formFactorTreeView).contains(o.getFormFactor());
        Predicate<Motherboard> memory = (o) -> (o.getMemoryMaxGb() >= memoryMinComboBox.getValue()) &&
                (o.getMemoryMaxGb() <= memoryMaxComboBox.getValue());
        Predicate<Motherboard> slots = (o) -> (o.getMemorySlots() >= slotsMinComboBox.getValue()) &&
                (o.getMemorySlots() <= slotsMaxComboBox.getValue());

        Predicate<Motherboard> price = (o) -> (o.getPrice() >= getDoubleFromRegionalString(priceLowerTextField.getText())
                && o.getPrice() <= getDoubleFromRegionalString(priceUpperTextField.getText()));

        Predicate<Motherboard> tier = (o) -> (o.getTier() >= getDoubleFromRegionalString(tierMinTextField.getText())
                && o.getTier() <= getDoubleFromRegionalString(tierMaxTextField.getText()));

        this.filteredList.setPredicate(brand.and(chipset).and(socket).and(formFactor).and(memory).and(slots).and(price).and(tier));
    }

    @FXML
    void memoryMaxComboBoxUpdate(ActionEvent event) {
        memoryMaxController.maxComboBoxUpdated();
    }

    @FXML
    void memoryMinComboBoxUpdate(ActionEvent event) {
        memoryMaxController.minComboBoxUpdated();
    }

    @FXML
    void slotsMaxComboBoxUpdate(ActionEvent event) {
        slotsController.maxComboBoxUpdated();
    }

    @FXML
    void slotsMinComboBoxUpdate(ActionEvent event) {
        slotsController.minComboBoxUpdated();
    }

    @Override
    public void initialize() {
        //initialize the lists
        try {
            this.productList = JsonDataGetter.getInstance().getMotherboardList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.filteredList = new FilteredList<>(FXCollections.observableList(this.productList));
        SortedList<Motherboard> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        //initialize the tableview
        tableView.getColumns().addAll(Motherboard.getColumns());
        tableView.setItems(sortedList);
        //initialize the TreeView Filters
        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(brandRoot.getRoot());

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
        slotsController = new ComboBoxRangeValueController(getDistinctSlots(), slotsMinComboBox, slotsMaxComboBox);
        memoryMaxController = new ComboBoxRangeValueController(getDistinctMemory(), memoryMinComboBox, memoryMaxComboBox);

        initPriceTextFields();

        var stats = getTierStats();
        tierMinTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        tierMinTextField.setText(String.format("%.2f", stats.getMin()));

        tierMaxTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        tierMaxTextField.setText(String.format("%.2f", stats.getMax()));

        this.addTableViewListener();
    }

    private List<Integer> getDistinctMemory() {
        return this.productList.stream().map(Motherboard::getMemoryMaxGb).sorted().distinct().collect(Collectors.toList());
    }

    private List<Integer> getDistinctSlots() {
        return this.productList.stream().map(Motherboard::getMemorySlots).sorted().distinct().collect(Collectors.toList());
    }

    private DoubleSummaryStatistics getTierStats() {
        return this.productList.stream().map(Motherboard::getTier).mapToDouble(Float::doubleValue).summaryStatistics();
    }

    private List<String> getDistinctFormFactors() {
        return this.productList.stream().map(Motherboard::getFormFactor).distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctSockets() {
        return this.productList.stream().map(Motherboard::getSocket).distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctChipsets() {
        return this.productList.stream().map(Motherboard::getChipset).distinct().collect(Collectors.toList());
    }


}
