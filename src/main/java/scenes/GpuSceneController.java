package scenes;

import components.Gpu;
import helpers.*;
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

public class GpuSceneController extends ComponentScene<Gpu> {

    @FXML
    private TreeView<String> chipsetTreeView;

    @FXML
    ComboBox<Integer> memoryMinComboBox;

    @FXML
    ComboBox<Integer> memoryMaxComboBox;

    @FXML
    private TextField coreClockLowerTextField;

    @FXML
    private TextField coreClockUpperTextField;

    @FXML
    private TextField boostClockLowerTextField;

    @FXML
    private TextField boostClockUpperTextField;

    @FXML
    private TextField lengthLowerTextField;

    @FXML
    private TextField lengthUpperTextField;

    @FXML
    private ComboBox<Integer> tdpMinComboBox;

    @FXML
    private ComboBox<Integer> tdpMaxComboBox;

    @FXML
    private ComboBox<Integer> psuMinComboBox;

    @FXML
    private ComboBox<Integer> psuMaxComboBox;

    @FXML
    private TextField performanceLowerTextField;

    @FXML
    private TextField performanceUpperTextField;

    private ComboBoxRangeValueController memoryController;
    private ComboBoxRangeValueController tdpController;
    private ComboBoxRangeValueController psuController;

    @FXML
    void applyFilters(ActionEvent event) {
        Predicate<Gpu> brand = (o) -> getSelectedValues(brandTreeView).contains(o.getBrand());
        Predicate<Gpu> chipset = (o) -> getSelectedValues(chipsetTreeView).contains(o.getChipset());
        Predicate<Gpu> memory = (o) -> (o.getMemoryGb() >= memoryMinComboBox.getValue()) &&
                (o.getMemoryGb() <= memoryMaxComboBox.getValue());
        Predicate<Gpu> tdp = (o) -> (o.getTdpW() >= tdpMinComboBox.getValue()) &&
                (o.getTdpW() <= tdpMaxComboBox.getValue());
        Predicate<Gpu> psu = (o) -> (o.getRecommendedPsuWatts() >= psuMinComboBox.getValue()) &&
                (o.getRecommendedPsuWatts() <= psuMaxComboBox.getValue());

        Predicate<Gpu> price = (o) -> (o.getPrice() >= getDoubleFromRegionalString(priceLowerTextField.getText())
                && o.getPrice() <= getDoubleFromRegionalString(priceUpperTextField.getText()));

        Predicate<Gpu> core = (o) -> (o.getCoreClockMhz() >= getDoubleFromRegionalString(coreClockLowerTextField.getText())) &&
                (o.getCoreClockMhz() <= getDoubleFromRegionalString(coreClockUpperTextField.getText()));

        Predicate<Gpu> boost = (o) -> (o.getBoostClockMhz() >= getDoubleFromRegionalString(boostClockLowerTextField.getText())) &&
                (o.getBoostClockMhz() <= getDoubleFromRegionalString(boostClockUpperTextField.getText()));

        Predicate<Gpu> length = (o) -> (o.getLengthMillimeters() >= getDoubleFromRegionalString(lengthLowerTextField.getText())) &&
                (o.getLengthMillimeters() <= getDoubleFromRegionalString(lengthUpperTextField.getText()));

        Predicate<Gpu> performance = (o) -> (o.getPerformance() >= getDoubleFromRegionalString(performanceLowerTextField.getText())) &&
                (o.getPerformance() <= getDoubleFromRegionalString(performanceUpperTextField.getText()));

        this.filteredList.setPredicate(brand.and(chipset).and(tdp).and(memory).and(price).and(psu).and(core).and(boost).and(length).and(performance));
    }

    @FXML
    void memoryMaxComboBoxUpdate(ActionEvent event) {
        memoryController.maxComboBoxUpdated();
    }

    @FXML
    void memoryMinComboBoxUpdate(ActionEvent event) {
        memoryController.minComboBoxUpdated();
    }

    @FXML
    void tdpMaxComboBoxUpdate(ActionEvent event) {
        tdpController.maxComboBoxUpdated();
    }

    @FXML
    void tdpMinComboBoxUpdate(ActionEvent event) {
        tdpController.minComboBoxUpdated();
    }

    @FXML
    void psuMinComboBoxUpdate(ActionEvent event) {
        psuController.minComboBoxUpdated();
    }

    @FXML
    void psuMaxComboBoxUpdate(ActionEvent event) {
        psuController.maxComboBoxUpdated();
    }

    @Override
    public void initialize() {
        //initialize the lists
        try {
            this.productList = JsonDataGetter.getInstance().getGpuList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.filteredList = new FilteredList<>(FXCollections.observableList(this.productList));
        SortedList<Gpu> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        //initialize the tableview
        tableView.getColumns().addAll(Gpu.getColumns());
        tableView.setItems(sortedList);
        //initialize the TreeView Filters
        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(brandRoot.getRoot());

        CheckBoxRoot chipsetRoot = new CheckBoxRoot(this.getDistinctChipsets());
        this.chipsetTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.chipsetTreeView.setRoot(chipsetRoot.getRoot());

        //initialize the TextFields filters
        memoryController = new ComboBoxRangeValueController(getDistinctMemoryCapacity(), memoryMinComboBox, memoryMaxComboBox);
        tdpController = new ComboBoxRangeValueController(getDistinctTdp(), tdpMinComboBox, tdpMaxComboBox);
        psuController = new ComboBoxRangeValueController(getDistinctPsu(), psuMinComboBox, psuMaxComboBox);

        initPriceTextFields();

        var stats = getCoreClockStats();

        this.coreClockLowerTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.coreClockLowerTextField.setText(String.format("%d", stats.getMin()));

        this.coreClockUpperTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.coreClockUpperTextField.setText(String.format("%d", stats.getMax()));

        stats = getBoostClockStats();

        this.boostClockLowerTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.boostClockLowerTextField.setText(String.format("%d", stats.getMin()));

        this.boostClockUpperTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.boostClockUpperTextField.setText(String.format("%d", stats.getMax()));

        stats = getLengthStats();

        this.lengthLowerTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.lengthLowerTextField.setText(String.format("%d", stats.getMin()));

        this.lengthUpperTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.lengthUpperTextField.setText(String.format("%d", stats.getMax()));

        stats = getPerformanceStats();

        this.performanceLowerTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.performanceLowerTextField.setText(String.format("%d", stats.getMin()));

        this.performanceUpperTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.performanceUpperTextField.setText(String.format("%d", stats.getMax()));

        this.addTableViewListener();
    }

    private IntSummaryStatistics getCoreClockStats() {
        return this.productList.stream().map(Gpu::getCoreClockMhz).mapToInt(Integer::intValue).summaryStatistics();
    }

    private IntSummaryStatistics getBoostClockStats() {
        return this.productList.stream().map(Gpu::getBoostClockMhz).mapToInt(Integer::intValue).summaryStatistics();
    }

    private IntSummaryStatistics getLengthStats() {
        return this.productList.stream().map(Gpu::getLengthMillimeters).mapToInt(Integer::intValue).summaryStatistics();
    }

    private List<Integer> getDistinctPsu() {
        return this.productList.stream().map(Gpu::getRecommendedPsuWatts).sorted().distinct().collect(Collectors.toList());
    }

    private IntSummaryStatistics getPerformanceStats() {
        return this.productList.stream().map(Gpu::getPerformance).mapToInt(Integer::intValue).summaryStatistics();
    }

    private List<Integer> getDistinctTdp() {
        return this.productList.stream().map(Gpu::getTdpW).sorted().distinct().collect(Collectors.toList());
    }

    private List<Integer> getDistinctMemoryCapacity() {
        return this.productList.stream().map(Gpu::getMemoryGb).sorted().distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctChipsets() {
        return this.productList.stream().map(Gpu::getChipset).distinct().collect(Collectors.toList());
    }
}
