package components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Ram extends Product {
    private int speed;
    private int modulesCount;
    private int moduleCapacityGb;
    private float firstWordLatencyNs;
    private int casLatency;

    public static ArrayList<TableColumn> getColumns() {
        ArrayList<TableColumn> columns = new ArrayList<>();

        //image column
        columns.add(new TableColumn<Ram, ImageView>(""));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("imageView"));
        columns.get(columns.size() - 1).setResizable(false);
        columns.get(columns.size() - 1).setMinWidth(70);
        columns.get(columns.size() - 1).setMaxWidth(70);
        columns.get(columns.size() - 1).setSortable(false);
        //first two columns
        columns.add(new TableColumn<Ram, String>("Brand"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("brand"));
        columns.add(new TableColumn<Ram, String>("Name"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("name"));
        //class specific columns
        columns.add(new TableColumn<Ram, Integer>("MT/s"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("speed"));
        //Merge "Count" and "Capacity" under name modules
        columns.add(new TableColumn<Ram, Integer>("Modules count"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("modulesCount"));
        columns.add(new TableColumn<Ram, Integer>("Module capacity"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("moduleCapacityGb"));
        columns.add(new TableColumn<Ram, Float>("First word latency"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("firstWordLatencyNs"));
        columns.add(new TableColumn<Ram, Integer>("CAS latency"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("casLatency"));
        //last columns
        columns.add(new TableColumn<Ram, Double>("Price"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("price"));

        return columns;
    }

    public Ram() {

    }

    @Override
    public ProductType getProductType() {
        return ProductType.Ram;
    }

    public Ram(String _id, String image, String brand, String name, Double price, int speed, int modulesCount, int moduleCapacityGb, float firstWordLatencyNs, int casLatency) {
        super(_id, image, brand, name, price);
        this.speed = speed;
        this.modulesCount = modulesCount;
        this.moduleCapacityGb = moduleCapacityGb;
        this.firstWordLatencyNs = firstWordLatencyNs;
        this.casLatency = casLatency;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getModulesCount() {
        return modulesCount;
    }

    public void setModulesCount(int modulesCount) {
        this.modulesCount = modulesCount;
    }

    public int getModuleCapacityGb() {
        return moduleCapacityGb;
    }

    public void setModuleCapacityGb(int moduleCapacityGb) {
        this.moduleCapacityGb = moduleCapacityGb;
    }

    public float getFirstWordLatencyNs() {
        return firstWordLatencyNs;
    }

    public void setFirstWordLatencyNs(float firstWordLatencyNs) {
        this.firstWordLatencyNs = firstWordLatencyNs;
    }

    public int getCasLatency() {
        return casLatency;
    }

    public void setCasLatency(int casLatency) {
        this.casLatency = casLatency;
    }
}
