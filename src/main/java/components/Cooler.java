package components;

import helpers.WorkloadType;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableCell;
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
        columns.get(columns.size() - 1).setCellFactory((column) -> new TableCell<Cooler, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    this.setText(String.format("%.1f dB", item));
                }
            }
        });
        columns.add(new TableColumn<Cooler, String>("Cooler Type"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("coolerType"));
        columns.add(new TableColumn<Cooler, String>("For workstations"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("forWorkstation"));
        //last columns
        columns.add(getPriceColumn());

        return columns;
    }

    @Override
    public ProductType getProductType() {
        return ProductType.Cooler;
    }

    @Override
    public void setPerformanceValues(ObservableList<XYChart.Data<Number, String>> performanceValues) {
        for(var value : performanceValues) {
            float tier = (9.f / getTier()) * noiseLevelDb;
            switch (WorkloadType.toEnum(value.getYValue())) {
                case Gaming:
                    tier /= 8;
                    break;
                case Office:
                    tier /= 2;
                    break;
                case PhotoEditing:
                    tier /= 4;
                    break;
                case VideoEditing:
                    tier /= 7;
                    break;
                case Rendering3D:
                    tier /= 10;
                    break;
            }
            value.setXValue(tier);
        }
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

    @Override
    public String toString() {
        return brand
                + " "
                + name
                + " "
                + (isAirCooler ? "Air" : "Liquid")
                + " CPU Cooler"
                ;
    }
}
