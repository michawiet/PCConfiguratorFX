package components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Cooler extends Product {
    private int tier;
    private float noiseLevelDb;
    private boolean isAirCooler;
    private boolean forWorkstation;

    public static ArrayList<TableColumn> getColumns() {
        ArrayList<TableColumn> columns = new ArrayList<>();

        //image column
        columns.add(new TableColumn<Cooler, ImageView>(""));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("imageView"));
        columns.get(columns.size() - 1).setResizable(false);
        columns.get(columns.size() - 1).setMinWidth(70);
        columns.get(columns.size() - 1).setMaxWidth(70);
        columns.get(columns.size() - 1).setSortable(false);
        //first two columns
        columns.add(new TableColumn<Cooler, String>("Brand"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("brand"));
        columns.add(new TableColumn<Cooler, String>("Name"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("name"));
        //class specific columns
        columns.add(new TableColumn<Cooler, Integer>("Tier"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("tier"));
        columns.add(new TableColumn<Cooler, Float>("Noise Level (dB)"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("noiseLevelDb"));
        columns.add(new TableColumn<Cooler, String>("Cooler Type"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("coolerType"));
        columns.add(new TableColumn<Cooler, String>("For workstations"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("forWorkstation"));
        //last columns
        columns.add(new TableColumn<Cooler, Double>("Price"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("price"));

        return columns;
    }

    public Cooler() {

    }

    @Override
    public ProductType getProductType() {
        return ProductType.Cooler;
    }

    public Cooler(String _id, String image, String brand, String name, Double price, int tier, float noiseLevelDb, boolean isAirCooler, boolean forWorkstation) {
        super(_id, image, brand, name, price);
        this.tier = tier;
        this.noiseLevelDb = noiseLevelDb;
        this.isAirCooler = isAirCooler;
        this.forWorkstation = forWorkstation;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public float getNoiseLevelDb() {
        return noiseLevelDb;
    }

    public void setNoiseLevelDb(float noiseLevelDb) {
        this.noiseLevelDb = noiseLevelDb;
    }

    public String getCoolerType() {
        return (this.isAirCooler ? "Air" : "Liquid");
    }

    public void setIsAirCooler(boolean airCooler) {
        isAirCooler = airCooler;
    }

    public String isForWorkstation() {
        return this.forWorkstation ? "Workstation" : "Standard";
    }

    public void setForWorkstation(boolean forWorkstation) {
        this.forWorkstation = forWorkstation;
    }
}
