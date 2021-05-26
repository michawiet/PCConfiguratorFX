package scenes;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.Motherboard;
import components.Psu;
import components.Ram;
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
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PsuSceneController extends ComponentScene<Psu> {

    @FXML
    private TextField tierLowerTextField;

    @FXML
    private TextField tierUpperTextField;

    @FXML
    private TreeView<String> formFactorTreeView;

    @FXML
    private TreeView<String> efficiencyRatingTreeView;

    @FXML
    private ComboBox<Integer> wattageMinComboBox;

    @FXML
    private ComboBox<Integer> wattageMaxComboBox;

    @FXML
    private TreeView<String> modularityTreeView;

    private ComboBoxRangeValueController wattageController;

    @FXML
    void applyFilters(ActionEvent event) {
        Predicate<Psu> brand = (o) -> getSelectedValues(brandTreeView).contains(o.getBrand());
        Predicate<Psu> formFactor = (o) -> getSelectedValues(formFactorTreeView).contains(o.getFormFactor());
        Predicate<Psu> efficiency = (o) -> getSelectedValues(efficiencyRatingTreeView).contains(o.getEfficiencyRating());
        Predicate<Psu> modularity = (o) -> getSelectedValues(modularityTreeView).contains(o.getModular());
        Predicate<Psu> wattage = (o) -> (o.getWattage() >= wattageMinComboBox.getValue()) &&
                (o.getWattage() <= wattageMaxComboBox.getValue());

        Predicate<Psu> price = (o) -> (o.getPrice() >= getDoubleFromRegionalString(priceLowerTextField.getText())
                && o.getPrice() <= getDoubleFromRegionalString(priceUpperTextField.getText()));

        Predicate<Psu> tier = (o) -> (o.getTier() >= getDoubleFromRegionalString(tierLowerTextField.getText()).floatValue()
                && o.getTier() <= getDoubleFromRegionalString(tierUpperTextField.getText()).floatValue());

        this.filteredList.setPredicate(brand.and(formFactor).and(efficiency).and(modularity).and(wattage).and(price).and(tier));
    }

    @FXML
    void wattageMaxComboBoxUpdate(ActionEvent event) {
        wattageController.maxComboBoxUpdated();
    }

    @FXML
    void wattageMinComboBoxUpdate(ActionEvent event) {
        wattageController.minComboBoxUpdated();
    }

    @Override
    public void initialize() {
        //initialize the lists
        try {
            this.productList = JsonDataGetter.getInstance().getPsuList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.filteredList = new FilteredList<>(FXCollections.observableList(this.productList));
        SortedList<Psu> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        //initialize the tableview
        tableView.getColumns().addAll(Psu.getColumns());
        tableView.setItems(sortedList);
        //initialize the TreeView Filters
        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(brandRoot.getRoot());

        CheckBoxRoot formFactorRoot = new CheckBoxRoot(this.getDistinctFormFactors());
        this.formFactorTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.formFactorTreeView.setRoot(formFactorRoot.getRoot());

        CheckBoxRoot modularityRoot = new CheckBoxRoot(this.getDistinctModularity());
        this.modularityTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.modularityTreeView.setRoot(modularityRoot.getRoot());

        CheckBoxRoot efficiencyRatingRoot = new CheckBoxRoot(this.getDistinctEfficiencyRating());
        this.efficiencyRatingTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.efficiencyRatingTreeView.setRoot(efficiencyRatingRoot.getRoot());

        //initialize the TextFields filters
        wattageController = new ComboBoxRangeValueController(getDistinctWattage(), wattageMinComboBox, wattageMaxComboBox);

        initPriceTextFields();

        var stats = getTierStats();
        this.tierLowerTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.tierLowerTextField.setText(String.format("%.2f", stats.getMin()));
        this.tierUpperTextField.setTextFormatter(new DecimalTextFormatter(0, 2));
        this.tierUpperTextField.setText(String.format("%.2f", stats.getMax()));

        this.addTableViewListener();
    }

    private DoubleSummaryStatistics getTierStats() {
        return this.productList.stream().map(Psu::getTier).mapToDouble(Float::doubleValue).summaryStatistics();
    }

    private List<Integer> getDistinctWattage() {
        return this.productList.stream().map(Psu::getWattage).sorted().distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctEfficiencyRating() {
        return this.productList.stream().map(Psu::getEfficiencyRating).distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctModularity() {
        return this.productList.stream().map(Psu::getModular).distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctFormFactors() {
        return this.productList.stream().map(Psu::getFormFactor).distinct().collect(Collectors.toList());
    }
}
