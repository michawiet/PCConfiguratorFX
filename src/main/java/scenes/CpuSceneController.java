package scenes;

import components.Cpu;
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
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CpuSceneController extends ComponentScene<Cpu> {

    @FXML
    private TreeView<String> socketTreeView;

    @FXML
    private ComboBox<Integer> coreCountMinComboBox;

    @FXML
    private ComboBox<Integer> coreCountMaxComboBox;

    @FXML
    private TreeView<String> smtTreeView;

    @FXML
    private TreeView<String> igpuTreeView;

    @FXML
    private ComboBox<Integer> tdpMinComboBox;

    @FXML
    private ComboBox<Integer> tdpMaxComboBox;

    @FXML
    private TextField stPerformanceLowerTextField;

    @FXML
    private TextField stPerformanceUpperTextField;

    @FXML
    private TextField mtPerformanceLowerTextField;

    @FXML
    private TextField mtPerformanceUpperTextField;

    @FXML
    private TextField coreClockLowerTextField;

    @FXML
    private TextField coreClockUpperTextField;

    @FXML
    private TextField boostClockLowerTextField;

    @FXML
    private TextField boostClockUpperTextField;

    private ComboBoxRangeValueController tdpController;
    private ComboBoxRangeValueController coresController;

    @FXML
    public void initialize() {
        //initialize the lists
        try {
            this.productList = JsonDataGetter.getInstance().getCpuList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.filteredList = new FilteredList<>(FXCollections.observableList(this.productList));
        SortedList<Cpu> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        //initialize the tableview
        tableView.getColumns().addAll(Cpu.getColumns());
        tableView.setItems(sortedList);

        //initialize the TreeView Filters
        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(brandRoot.getRoot());

        CheckBoxRoot socketRoot = new CheckBoxRoot(this.getDistinctSockets());
        this.socketTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.socketTreeView.setRoot(socketRoot.getRoot());

        CheckBoxRoot smtRoot = new CheckBoxRoot(this.getDistinctSmt());
        this.smtTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.smtTreeView.setRoot(smtRoot.getRoot());

        CheckBoxRoot igpuRoot = new CheckBoxRoot(this.getDistinctIgpu());
        this.igpuTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.igpuTreeView.setRoot(igpuRoot.getRoot());

        //initialize the TextFields filters
        this.coresController = new ComboBoxRangeValueController(this.getDistinctCoreCount(), coreCountMinComboBox, coreCountMaxComboBox);
        this.tdpController = new ComboBoxRangeValueController(this.getDistinctTdp(), tdpMinComboBox, tdpMaxComboBox);

        this.initPriceTextFields();

        var stats = getStPerformanceStatistics();

        this.stPerformanceLowerTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.stPerformanceLowerTextField.setText(stats.getMin() + "");

        this.stPerformanceUpperTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.stPerformanceUpperTextField.setText(stats.getMax() + "");

        stats = getMtPerformanceStatistics();

        this.mtPerformanceLowerTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.mtPerformanceLowerTextField.setText(stats.getMin() + "");

        this.mtPerformanceUpperTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.mtPerformanceUpperTextField.setText(stats.getMax() + "");

        var fStats = getCoreClockStatistics();

        this.coreClockLowerTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.coreClockLowerTextField.setText(String.format("%.2f", fStats.getMin()));

        this.coreClockUpperTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.coreClockUpperTextField.setText(String.format("%.2f", fStats.getMax()));

        fStats = getBoostClockStatistics();

        this.boostClockLowerTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.boostClockLowerTextField.setText(String.format("%.2f", fStats.getMin()));

        this.boostClockUpperTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.boostClockUpperTextField.setText(String.format("%.2f", fStats.getMax()));

        this.addTableViewListener();
    }

    @FXML
    void applyFilters(ActionEvent event) {
        //Update the predicates and apply to the filtered/observable list
        Predicate<Cpu> brand = (cpu) -> getSelectedValues(brandTreeView).contains(cpu.getBrand());
        Predicate<Cpu> socket = (cpu) -> getSelectedValues(socketTreeView).contains(cpu.getSocket());
        Predicate<Cpu> smt = (cpu) -> getSelectedValues(smtTreeView).contains(cpu.getSmt());
        Predicate<Cpu> igpu = (cpu) -> getSelectedValues(igpuTreeView).contains(cpu.getIgpu());

        //Predicate<Cpu> price
        Predicate<Cpu> coreCount = (cpu) -> (cpu.getCores() >= coreCountMinComboBox.getValue()) &&
                (cpu.getCores() <= coreCountMaxComboBox.getValue());
        Predicate<Cpu> tdp = (cpu) -> (cpu.getTdp() >= tdpMinComboBox.getValue()) &&
                (cpu.getTdp() <= tdpMaxComboBox.getValue());

        Predicate<Cpu> price = (cpu) -> (cpu.getPrice() >= getDoubleFromRegionalString(priceLowerTextField.getText())
                && cpu.getPrice() <= getDoubleFromRegionalString(priceUpperTextField.getText()));

        Predicate<Cpu> st = (cpu) -> (cpu.getStPerformance() >= Integer.valueOf(stPerformanceLowerTextField.getText())
                && cpu.getStPerformance() <= Integer.valueOf(stPerformanceUpperTextField.getText()));
        Predicate<Cpu> mt = (cpu) -> (cpu.getMtPerformance() >= Integer.valueOf(mtPerformanceLowerTextField.getText())
                && cpu.getMtPerformance() <= Integer.valueOf(mtPerformanceUpperTextField.getText()));
        Predicate<Cpu> core = (cpu) -> (cpu.getCoreClock() >= getFloatFromRegionalString(coreClockLowerTextField.getText())
                && cpu.getCoreClock() <= getFloatFromRegionalString(coreClockUpperTextField.getText()));
        Predicate<Cpu> boost = (cpu) -> (cpu.getBoostClock() >= getFloatFromRegionalString(boostClockLowerTextField.getText())
                && cpu.getBoostClock() <= getFloatFromRegionalString(boostClockUpperTextField.getText()));

        this.filteredList.setPredicate(brand
                .and(socket)
                .and(smt)
                .and(igpu)
                .and(coreCount)
                .and(tdp)
                .and(price)
                .and(st)
                .and(mt)
                .and(core)
                .and(boost));
    }

    @FXML
    void coreCountMaxComboBoxUpdated(ActionEvent event) {
        this.coresController.maxComboBoxUpdated();
    }

    @FXML
    void coreCountMinComboBoxUpdated(ActionEvent event) {
        this.coresController.minComboBoxUpdated();
    }

    @FXML
    void tdpMaxComboBoxUpdated(ActionEvent event) {
        this.tdpController.maxComboBoxUpdated();
    }

    @FXML
    void tdpMinComboBoxUpdated(ActionEvent event) {
        this.tdpController.minComboBoxUpdated();
    }

    private List<String> getDistinctSockets() {
        return new ArrayList(this.productList.stream().map(Cpu::getSocket).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctIgpu() {
        return this.productList.stream().map(Cpu::getIgpu).distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctSmt() {
        return this.productList.stream().map(Cpu::getSmt).distinct().collect(Collectors.toList());
    }

    private List<Integer> getDistinctCoreCount() {
        return this.productList.stream().map(Cpu::getCores).sorted().distinct().collect(Collectors.toList());
    }

    private List<Integer> getDistinctTdp() {
        return this.productList.stream().map(Cpu::getTdp).sorted().distinct().collect(Collectors.toList());
    }

    private IntSummaryStatistics getCoresStatistics() {
        return this.productList.stream().map(Cpu::getCores).mapToInt(Integer::intValue).summaryStatistics();
    }

    private IntSummaryStatistics getTdpStatistics() {
        return this.productList.stream().map(Cpu::getTdp).mapToInt(Integer::intValue).summaryStatistics();
    }

    private IntSummaryStatistics getStPerformanceStatistics() {
        return this.productList.stream().map(Cpu::getStPerformance).mapToInt(Integer::intValue).summaryStatistics();
    }

    private IntSummaryStatistics getMtPerformanceStatistics() {
        return this.productList.stream().map(Cpu::getMtPerformance).mapToInt(Integer::intValue).summaryStatistics();
    }

    private DoubleSummaryStatistics getCoreClockStatistics() {
        return this.productList.stream().map(Cpu::getCoreClock).mapToDouble(Float::doubleValue).summaryStatistics();
    }

    private DoubleSummaryStatistics getBoostClockStatistics() {
        return this.productList.stream().map(Cpu::getBoostClock).mapToDouble(Float::doubleValue).summaryStatistics();
    }
}