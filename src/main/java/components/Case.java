package components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Case extends Product {
    private String powerSupplyStandard;
    private String maxMotherboardSize;
    private String type;
    private String sidePanelWindow;

    public static ArrayList<TableColumn> getColumns() {
        ArrayList<TableColumn> columns = new ArrayList<>();

        //image column
        columns.add(new TableColumn<Case, ImageView>(""));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("imageView"));
        columns.get(columns.size() - 1).setResizable(false);
        columns.get(columns.size() - 1).setMinWidth(70);
        columns.get(columns.size() - 1).setMaxWidth(70);
        columns.get(columns.size() - 1).setSortable(false);
        //first two columns
        columns.add(new TableColumn<Case, String>("Brand"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("brand"));
        columns.add(new TableColumn<Case, String>("Name"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("name"));
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
        columns.add(new TableColumn<Case, Double>("Price"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("price"));

        return columns;
    }

    public Case() {
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
