package scenes;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.Gpu;
import helpers.CheckBoxRoot;
import helpers.DatabaseData;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GpuSceneController extends BaseScene<Gpu> {

    @FXML
    private TreeView<String> chipsetTreeView;

    @FXML
    private TextField memoryLowerTextField;

    @FXML
    private TextField memoryUpperTextField;

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
    private TextField tdpLowerTextField;

    @FXML
    private TextField tdpUpperTextField;

    @FXML
    private TextField recommendedPsuWattsLowerTextField;

    @FXML
    private TextField recommendedPsuWattsUpperTextField;

    @FXML
    private TextField performanceLowerTextField;

    @FXML
    private TextField performanceUpperTextField;

    @FXML
    void AddCpu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainSceneController.fxml"));
        Main.getPrimaryScene().setRoot(root);
    }

    @FXML
    void ApplyFilters(ActionEvent event) {

    }

    @FXML
    void ResetFilters(ActionEvent event) {

    }

    @Override
    public void initialize() {
        //initialize the lists
        DatabaseData data = new DatabaseData();
        try {
            this.productList = data.getGpuList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.observableList = FXCollections.observableList(this.productList);
        this.filteredList = new FilteredList<>(observableList, cpu -> true);
        //initialize the tableview
        tableView.getColumns().addAll(Gpu.getColumns());
        tableView.getItems().addAll(filteredList);
        //initialize the TreeView Filters
        CheckBoxRoot nameRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(nameRoot.getRoot());

        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctNames());
        this.nameTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.nameTreeView.setRoot(brandRoot.getRoot());

        CheckBoxRoot chipsetRoot = new CheckBoxRoot(this.getDistinctChipsets());
        this.chipsetTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.chipsetTreeView.setRoot(chipsetRoot.getRoot());

        //initialize the TextFields filters
    }

    private List<String> getDistinctChipsets() {
        return new ArrayList(this.productList.stream().map(Gpu::getChipset).distinct().collect(Collectors.toList()));
    }
}
