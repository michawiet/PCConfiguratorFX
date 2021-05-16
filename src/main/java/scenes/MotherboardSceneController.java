package scenes;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.Motherboard;
import helpers.CheckBoxRoot;
import helpers.ComboBoxRangeValueController;
import helpers.DatabaseData;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

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
    private ComboBox<Integer> tierMinComboBox;

    @FXML
    private ComboBox<Integer> tierMaxComboBox;

    @FXML
    private ComboBox<Integer> slotsMinComboBox;

    @FXML
    private ComboBox<Integer> slotsMaxComboBox;

    @FXML
    private ComboBox<Integer> memoryMinComboBox;

    @FXML
    private ComboBox<Integer> memoryMaxComboBox;

    ComboBoxRangeValueController tierController;
    ComboBoxRangeValueController slotsController;
    ComboBoxRangeValueController memoryMaxController;

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

        this.filteredList.setPredicate(brand.and(chipset).and(socket).and(formFactor).and(memory).and(slots));
    }

    @FXML
    void resetFilters(ActionEvent event) {

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

    @FXML
    void tierMaxComboBoxUpdate(ActionEvent event) {

    }

    @FXML
    void tierMinComboBoxUpdate(ActionEvent event) {

    }

    @Override
    public void initialize() {
        //initialize the lists
        try {
            this.productList = DatabaseData.getInstance().getMotherboardList();
        } catch (JsonProcessingException e) {
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
        //tierController = new ComboBoxRangeValueController(getDistinctTier(), tierMinComboBox, tierMaxComboBox);
        slotsController = new ComboBoxRangeValueController(getDistinctSlots(), slotsMinComboBox, slotsMaxComboBox);
        memoryMaxController = new ComboBoxRangeValueController(getDistinctMemory(), memoryMinComboBox, memoryMaxComboBox);

        this.addTableViewListener();
    }

    private List<Integer> getDistinctMemory() {
        return this.productList.stream().map(Motherboard::getMemoryMaxGb).sorted().distinct().collect(Collectors.toList());
    }

    private List<Integer> getDistinctSlots() {
        return this.productList.stream().map(Motherboard::getMemorySlots).sorted().distinct().collect(Collectors.toList());
    }

    private List<Float> getDistinctTier() {
        return this.productList.stream().map(Motherboard::getTier).sorted().distinct().collect(Collectors.toList());
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
