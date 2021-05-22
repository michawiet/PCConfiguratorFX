package components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class Cooler extends Product {
    private int tier;
    private float noiseLevelDb;
    private boolean isAirCooler;
    private boolean forWorkstation;

    public static List<TableColumn> getColumns() {
        List<TableColumn> columns = Product.getBasicColumns();
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
        columns.add(getPriceColumn());

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
