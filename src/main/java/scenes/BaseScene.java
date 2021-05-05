package scenes;

import components.Product;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseScene<T extends Product> {

    //TODO: move here the methods and fields commonly used in scenes
    protected List<T> productList;
    protected ObservableList<T> observableList;
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
    protected TreeView<String> nameTreeView;

    @FXML
    protected TextField priceLowerTextField;

    @FXML
    protected TextField priceUpperTextField;

    @FXML
    protected TableView tableView;

    @FXML
    public abstract void initialize();

    protected List<String> getDistinctBrands() {
        return new ArrayList(this.productList.stream().map(T::getBrand).distinct().collect(Collectors.toList()));
    }

    protected List<String> getDistinctNames() {
        return new ArrayList(this.productList.stream().map(T::getName).distinct().collect(Collectors.toList()));
    }

    protected DoubleSummaryStatistics getPriceStatistics() {
        return this.productList.stream().map(T::getPrice).mapToDouble(Double::doubleValue).summaryStatistics();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
