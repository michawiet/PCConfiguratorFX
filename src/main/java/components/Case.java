package components;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class Case extends Product {
    private String powerSupplyStandard;
    private String maxMotherboardSize;
    private String type;
    private String sidePanelWindow;

    public static List<TableColumn> getColumns() {
        List<TableColumn> columns = Product.getBasicColumns();
        //class specific columns
        columns.add(new TableColumn<Case, String>("PSU standard"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("powerSupplyStandard"));
        columns.add(new TableColumn<Case, String>("Max motherboard size"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("maxMotherboardSize"));
        columns.add(new TableColumn<Case, String>("Type"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("type"));
        columns.add(new TableColumn<Case, String>("Side panel window"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("sidePanelWindow"));
        //last columns
        columns.add(getPriceColumn());

        return columns;
    }

    @Override
    public ProductType getProductType() {
        return ProductType.Case;
    }

    @Override
    public void setPerformanceValues(ObservableList<XYChart.Data<Number, String>> performanceValues) {
        //none
    }

    public String getPowerSupplyStandard() {
        return powerSupplyStandard;
    }

    public void setPowerSupplyStandard(String powerSupplyStandard) {
        this.powerSupplyStandard = powerSupplyStandard;
    }

    public String getMaxMotherboardSize() {
        return maxMotherboardSize;
    }

    public void setMaxMotherboardSize(String maxMotherboardSize) {
        this.maxMotherboardSize = maxMotherboardSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSidePanelWindow() {
        return sidePanelWindow;
    }

    public void setSidePanelWindow(String sidePanelWindow) {
        this.sidePanelWindow = sidePanelWindow;
    }

    @Override
    public String toString() {
        return brand
                + " "
                + name
                + " "
                + type
                + " Case";
    }
}
