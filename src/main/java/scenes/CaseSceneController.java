package scenes;

import components.Case;
import components.Motherboard;
import helpers.CheckBoxRoot;
import helpers.JsonDataGetter;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CaseSceneController extends ComponentScene<Case> {

    @FXML
    private TreeView<String> powerSupplyStandardTreeView;

    @FXML
    private TreeView<String> maxMotherboardSizeTreeView;

    @FXML
    private TreeView<String> typeTreeView;

    @FXML
    private TreeView<String> sidePanelWindowTreeView;

    @FXML
    void applyFilters(ActionEvent event) {
        Predicate<Case> brand = (o) -> getSelectedValues(brandTreeView).contains(o.getBrand());
        Predicate<Case> standard = (o) -> getSelectedValues(powerSupplyStandardTreeView).contains(o.getPowerSupplyStandard());
        Predicate<Case> mbSize = (o) -> getSelectedValues(maxMotherboardSizeTreeView).contains(o.getMaxMotherboardSize());
        Predicate<Case> type = (o) -> getSelectedValues(typeTreeView).contains(o.getType());
        Predicate<Case> window = (o) -> getSelectedValues(sidePanelWindowTreeView).contains(o.getSidePanelWindow());

        Predicate<Case> price = (o) -> (o.getPrice() >= getDoubleFromRegionalString(priceLowerTextField.getText())
                && o.getPrice() <= getDoubleFromRegionalString(priceUpperTextField.getText()));

        this.filteredList.setPredicate(brand.and(type).and(standard).and(mbSize).and(window).and(price));
    }

    @Override
    public void initialize() {
        //initialize the lists
        try {
            this.productList = JsonDataGetter.getInstance().getCaseList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.filteredList = new FilteredList<>(FXCollections.observableList(this.productList));
        SortedList<Case> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        //initialize the tableview
        tableView.getColumns().addAll(Case.getColumns());
        tableView.setItems(sortedList);
        //initialize the TreeView Filters
        CheckBoxRoot brandRoot = new CheckBoxRoot(this.getDistinctBrands());
        this.brandTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.brandTreeView.setRoot(brandRoot.getRoot());

        CheckBoxRoot standardRoot = new CheckBoxRoot(this.getDistinctStandards());
        this.powerSupplyStandardTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.powerSupplyStandardTreeView.setRoot(standardRoot.getRoot());

        CheckBoxRoot maxMotherboardSizeRoot = new CheckBoxRoot(this.getDistinctMaxMotherboardSizes());
        this.maxMotherboardSizeTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.maxMotherboardSizeTreeView.setRoot(maxMotherboardSizeRoot.getRoot());

        CheckBoxRoot typeRoot = new CheckBoxRoot(this.getDistinctTypes());
        this.typeTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.typeTreeView.setRoot(typeRoot.getRoot());

        CheckBoxRoot sidePanelWindowRoot = new CheckBoxRoot(this.getDistinctSidePanelWindows());
        this.sidePanelWindowTreeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        this.sidePanelWindowTreeView.setRoot(sidePanelWindowRoot.getRoot());
        //initialize the TextFields filters
        initPriceTextFields();

        this.addTableViewListener();
    }

    private List<String> getDistinctSidePanelWindows() {
        return this.productList.stream().map(Case::getSidePanelWindow).distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctTypes() {
        return this.productList.stream().map(Case::getType).distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctMaxMotherboardSizes() {
        return this.productList.stream().map(Case::getMaxMotherboardSize).distinct().collect(Collectors.toList());
    }

    private List<String> getDistinctStandards() {
        return this.productList.stream().map(Case::getPowerSupplyStandard).distinct().collect(Collectors.toList());
    }
}
