package components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class Storage extends Product {
    private int capacityGb;
    private int tier;
    private String type;
    private String formFactor;
    private String connectionInterface;

    public static ArrayList<TableColumn> getColumns() {
        ArrayList<TableColumn> columns = new ArrayList<>();
        //first two columns
        columns.add(new TableColumn<Storage, String>("Brand"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("brand"));
        columns.add(new TableColumn<Storage, String>("Name"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("name"));
        //class specific columns
        columns.add(new TableColumn<Storage, Integer>("Capacity"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("capacityGb"));
        columns.add(new TableColumn<Storage, Integer>("Tier"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("tier"));
        columns.add(new TableColumn<Storage, String>("Type"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("type"));
        columns.add(new TableColumn<Storage, String>("Form factor"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("formFactor"));
        columns.add(new TableColumn<Storage, String>("Interface"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("connectionInterface"));
        //last columns
        columns.add(new TableColumn<Storage, Double>("Price"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("price"));

        return columns;
    }

    public Storage() {

    }

    public Storage(String _id, String image, String brand, String name, Double price, int capacityGb, int tier, String type, String formFactor, String connectionInterface) {
        super(_id, image, brand, name, price);
        this.capacityGb = capacityGb;
        this.tier = tier;
        this.type = type;
        this.formFactor = formFactor;
        this.connectionInterface = connectionInterface;
    }

    public int getCapacityGb() {
        return capacityGb;
    }

    public void setCapacityGb(int capacityGb) {
        this.capacityGb = capacityGb;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public String getConnectionInterface() {
        return connectionInterface;
    }

    public void setConnectionInterface(String connectionInterface) {
        this.connectionInterface = connectionInterface;
    }
}
