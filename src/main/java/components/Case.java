package components;

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

    public Case() {

    }

    @Override
    public ProductType getProductType() {
        return ProductType.Case;
    }

    public Case(String _id, String image, String brand, String name, Double price, String powerSupplyStandard, String maxMotherboardSize, String type, String sidePanelWindow) {
        super(_id, image, brand, name, price);
        this.powerSupplyStandard = powerSupplyStandard;
        this.maxMotherboardSize = maxMotherboardSize;
        this.type = type;
        this.sidePanelWindow = sidePanelWindow;
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
}
