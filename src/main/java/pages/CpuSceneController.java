package pages;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.Cpu;
import components.DatabaseData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class CpuSceneController {

    //Private class objects
    private List<Cpu> cpuList;
    private ObservableList<Cpu> observableList;
    private FilteredList<Cpu> filteredList;

    @FXML
    private Button addButton;

    @FXML
    private Button resetFiltersButton;

    @FXML
    private Button applyFiltersButton;

    @FXML
    private TreeView<String> brandTreeView;

    @FXML
    private TreeView<String> nameTreeView;

    @FXML
    private TextField priceLowerTextField;

    @FXML
    private TextField priceUpperTextField;

    @FXML
    private TreeView<String> socketTreeView;

    @FXML
    private TextField coreCountLowerSpinner;

    @FXML
    private TextField coreCountUpperSpinner;

    @FXML
    private TreeView<String> smtTreeView;

    @FXML
    private TreeView<String> igpuTreeView;

    @FXML
    private TextField tdpLowerTextField;

    @FXML
    private TextField tdpUpperTextField;

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

    @FXML
    private TableView tableView;

    @FXML
    public void initialize() {
        //initialize the lists
        DatabaseData data = new DatabaseData();
        try {
            this.cpuList = data.getCpuList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.observableList = FXCollections.observableList(this.cpuList);
        this.filteredList = new FilteredList<>(observableList, cpu -> true);
        //initialize the tableview
        tableView.getColumns().addAll(Cpu.getColumns());
        tableView.getItems().addAll(filteredList);
        //initialize the TreeView Filters
        CheckBoxRoot nameRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(nameRoot.getRoot());

        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctNames());
        this.nameTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.nameTreeView.setRoot(brandRoot.getRoot());

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
    }

    @FXML
    void AddCpu(ActionEvent event) {
        //try to change
    }

    @FXML
    void ApplyFilters(ActionEvent event) {
        //Update the predicates and apply to the filtered/observable list
    }

    @FXML
    void ResetFilters(ActionEvent event) {
        //reset predicates
    }

    //can be put in abstract page since it is all product
    private List<String> getDistinctBrands() {
        //return getDistinctValues(this.cpuList.stream().map(Cpu::getBrand).collect(Collectors.toList()));
        return new ArrayList(this.cpuList.stream().map(Cpu::getBrand).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctNames() {
        return new ArrayList(this.cpuList.stream().map(Cpu::getName).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctSockets() {
        return new ArrayList(this.cpuList.stream().map(Cpu::getSocket).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctIgpu() {
        return new ArrayList(this.cpuList.stream().map(Cpu::getIgpu).distinct().collect(Collectors.toList()));
    }

    private List<String> getDistinctSmt() {
        return new ArrayList(this.cpuList.stream().map(Cpu::getSmt).distinct().collect(Collectors.toList()));
    }

    private DoubleSummaryStatistics getPriceStatistics() {
        return this.cpuList.stream().map(Cpu::getPrice).mapToDouble(Double::doubleValue).summaryStatistics();
    }

    private IntSummaryStatistics getCoresStatistics() {
        return this.cpuList.stream().map(Cpu::getCores).mapToInt(Integer::intValue).summaryStatistics();
    }

    private IntSummaryStatistics getTdpStatistics() {
        return this.cpuList.stream().map(Cpu::getTdp).mapToInt(Integer::intValue).summaryStatistics();
    }

    private IntSummaryStatistics getStPerformanceStatistics() {
        return this.cpuList.stream().map(Cpu::getStPerformance).mapToInt(Integer::intValue).summaryStatistics();
    }

    private IntSummaryStatistics getMtPerformanceStatistics() {
        return this.cpuList.stream().map(Cpu::getMtPerformance).mapToInt(Integer::intValue).summaryStatistics();
    }

    private DoubleSummaryStatistics getCoreClockStatistics() {
        return this.cpuList.stream().map(Cpu::getCoreClock).mapToDouble(Float::doubleValue).summaryStatistics();
    }

    private DoubleSummaryStatistics getBoostClockStatistics() {
        return this.cpuList.stream().map(Cpu::getBoostClock).mapToDouble(Float::doubleValue).summaryStatistics();
    }
}