package scenes;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.Ram;
import helpers.CheckBoxRoot;
import helpers.DatabaseData;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.IOException;

public class RamSceneController extends BaseScene<Ram> {

    @FXML
    private TextField speedLowerTextField;

    @FXML
    private TextField speedUpperTextField;

    @FXML
    private TextField modulesCountLowerTextField;

    @FXML
    private TextField modulesCountUpperTextField;

    @FXML
    private TextField modulesCapacityLowerTextField;

    @FXML
    private TextField modulesCapacityUpperTextField;

    @FXML
    private TextField firstWordLatencyLowerTextField;

    @FXML
    private TextField firstWordLatencyUpperTextField;

    @FXML
    private TextField casLatencyLowerTextField;

    @FXML
    private TextField casLatencyUpperTextField;

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
            this.productList = data.getRamList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.observableList = FXCollections.observableList(this.productList);
        this.filteredList = new FilteredList<>(observableList, cpu -> true);
        //initialize the tableview
        tableView.getColumns().addAll(Ram.getColumns());
        tableView.getItems().addAll(filteredList);
        //initialize the TreeView Filters
        CheckBoxRoot nameRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(nameRoot.getRoot());

        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctNames());
        this.nameTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.nameTreeView.setRoot(brandRoot.getRoot());
        //initialize the TextFields filters
    }
}
