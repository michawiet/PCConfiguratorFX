package components;

import helpers.WorkloadType;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class Motherboard extends Product {
    private float tier;
    private String chipset;
    private String socket;
    private String formFactor;
    private int memorySlots;
    private int memoryMaxGb;

    public static List<TableColumn> getColumns() {
        List<TableColumn> columns = Product.getBasicColumns();
        //class specific columns
        columns.add(new TableColumn<Motherboard, Float>("Tier"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("tier"));
        columns.add(new TableColumn<Motherboard, String>("Chipset"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("chipset"));
        columns.add(new TableColumn<Motherboard, String>("Socket"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("socket"));
        columns.add(new TableColumn<Motherboard, String>("Form factor"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("formFactor"));
        columns.add(new TableColumn<Motherboard, Integer>("Memory slots"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("memorySlots"));
        columns.add(new TableColumn<Motherboard, Integer>("Max memory"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("memoryMaxGb"));
        columns.get(columns.size() - 1).setCellFactory((column) -> getIntegerTableCell("GB"));
        //last columns
        columns.add(getPriceColumn());

        return columns;
    }

    @Override
    public ProductType getProductType() {
        return ProductType.Motherboard;
    }

    @Override
    public void setPerformanceValues(ObservableList<XYChart.Data<Number, String>> performanceValues) {
        for(var value : performanceValues) {
            float tier = 9.f / getTier();
            switch (WorkloadType.toEnum(value.getYValue())) {
                case Gaming:
                    tier *= 0.5f;
                    break;
                case Office:
                    tier *= 1.1f;
                    break;
                case PhotoEditing:
                    tier *= 0.85f;
                    break;
                case VideoEditing:
                    tier *= 0.7f;
                    break;
                case Rendering3D:
                    tier *= 0.6f;
                    break;
            }
            value.setXValue(tier * 10.f);
        }
    }

    public float getTier() {
        return tier;
    }

    public void setTier(float tier) {
        this.tier = tier;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public int getMemorySlots() {
        return memorySlots;
    }

    public void setMemorySlots(int memorySlots) {
        this.memorySlots = memorySlots;
    }

    public int getMemoryMaxGb() {
        return memoryMaxGb;
    }

    public void setMemoryMaxGb(int memoryMaxGb) {
        this.memoryMaxGb = memoryMaxGb;
    }

    @Override
    public String toString() {
        return brand
                + " "
                + name
                + " "
                + formFactor
                + " "
                + socket
                + " Motherboard"
                ;
    }
}
