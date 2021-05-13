package scenes;

import components.Product;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseScene<T extends Product> {

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
