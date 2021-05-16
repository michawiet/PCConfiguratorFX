package scenes;

import components.Product;
import helpers.SceneHubSingleton;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ComponentScene<T extends Product> {

    //TODO: move here the methods and fields commonly used in scenes
    protected List<T> productList;
    protected FilteredList<T> filteredList;

    Scene scene;

    //FXML
    @FXML
    protected Button addButton;

    @FXML
    protected Button resetFiltersButton;

    @FXML
    protected Button applyFiltersButton;

    @FXML
    protected TreeView<String> brandTreeView;

    @FXML
    protected TextField priceLowerTextField;

    @FXML
    protected TextField priceUpperTextField;

    @FXML
    protected TableView tableView;

    @FXML
    public abstract void initialize();

    protected void addTableViewListener() {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            addButton.setDisable(newSelection != null ? false : true);
        });
    }

    @FXML
    protected void addAction(ActionEvent event) {
        Product product = (Product) tableView.getSelectionModel().getSelectedItem();
        SceneHubSingleton.getInstance().addSelectedProductAndSwitch(product);
    }

    protected List<String> getDistinctBrands() {
        return this.productList.stream().map(T::getBrand).distinct().collect(Collectors.toList());
    }

    protected DoubleSummaryStatistics getPriceStatistics() {
        return this.productList.stream().map(T::getPrice).mapToDouble(Double::doubleValue).summaryStatistics();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    protected List<String> getSelectedValues(TreeView<String> root) {
        var parent = root.getRoot().getChildren();
        List<String> list = new ArrayList<>();
        for(var item : parent) {
            if(((CheckBoxTreeItem<String>)item).isSelected()) {
                list.add(item.getValue());
            }
        }

        return list;
    }
}