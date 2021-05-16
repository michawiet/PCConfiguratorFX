package scenes;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.Gpu;
import helpers.CheckBoxRoot;
import helpers.ComboBoxRangeValueController;
import helpers.DatabaseData;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

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
    private TextField lengthPerformanceLowerTextField;

    @FXML
    private TextField lengthPerformanceUpperTextField;

    @FXML
    private ComboBox<Integer> tdpMinComboBox;

    @FXML
    private ComboBox<Integer> tdpMaxComboBox;

    @FXML
    private TextField recommendedPsuWattsLowerTextField;

    @FXML
    private TextField recommendedPsuWattsUpperTextField;

    @FXML
    private TextField performanceLowerTextField;

    @FXML
    private TextField performanceUpperTextField;

    private ComboBoxRangeValueController memoryController;
    private ComboBoxRangeValueController tdpController;

    @FXML
    void applyFilters(ActionEvent event) {
        Predicate<Gpu> brand = (o) -> getSelectedValues(brandTreeView).contains(o.getBrand());
        Predicate<Gpu> chipset = (o) -> getSelectedValues(chipsetTreeView).contains(o.getChipset());
        Predicate<Gpu> memory = (o) -> (o.getMemoryGb() >= memoryMinComboBox.getValue()) &&
                (o.getMemoryGb() <= memoryMaxComboBox.getValue());
        Predicate<Gpu> tdp = (o) -> (o.getTdpW() >= tdpMinComboBox.getValue()) &&
                (o.getTdpW() <= tdpMaxComboBox.getValue());

        this.filteredList.setPredicate(brand.and(chipset).and(tdp).and(memory));
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
    void resetFilters(ActionEvent event) {

    }

    @FXML
    void tdpMaxComboBoxUpdate(ActionEvent event) {
        tdpController.maxComboBoxUpdated();
    }

    @FXML
    void tdpMinComboBoxUpdate(ActionEvent event) {
        tdpController.minComboBoxUpdated();
    }

    @Override
    public void initialize() {
        //initialize the lists
        try {
            this.productList = DatabaseData.getInstance().getGpuList();
        } catch (JsonProcessingException e) {
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

        this.addTableViewListener();
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
