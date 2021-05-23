package components;

import helpers.WorkloadType;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class Storage extends Product {
    private int capacityGb;
    private int tier;
    private String type;
    private String formFactor;
    private String connectionInterface;

    public static List<TableColumn> getColumns() {
        List<TableColumn> columns = Product.getBasicColumns();
        //class specific columns
        columns.add(new TableColumn<Storage, Integer>("Capacity"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("capacityGb"));
        columns.get(columns.size() - 1).setCellFactory((column) -> new TableCell<Storage, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    this.setText(formatAsMemoryCapacity(item));
                }
            }
        });
        columns.add(new TableColumn<Storage, Integer>("Tier"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("tier"));
        columns.add(new TableColumn<Storage, String>("Type"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("type"));
        columns.add(new TableColumn<Storage, String>("Form factor"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("formFactor"));
        columns.add(new TableColumn<Storage, String>("Interface"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("connectionInterface"));
        //last columns
        columns.add(getPriceColumn());

        return columns;
    }

    public static String formatAsMemoryCapacity(int capacity) {
        if(capacity < 1000) {
            return (capacity + " GB");
        }

        float newVal = capacity / 1000.f;
        if(newVal == (long)newVal) {
            return (long)newVal + " TB";
        }

        return newVal + " TB";
    }

    @Override
    public ProductType getProductType() {
        return ProductType.Storage;
    }

    @Override
    public void setPerformanceValues(ObservableList<XYChart.Data<Number, String>> performanceValues) {
        for(var value : performanceValues) {
            float tier = (float) (9.f / getTier() * (price / 10.f));
            switch (WorkloadType.toEnum(value.getYValue())) {
                case Gaming:
                    tier *= 8;
                    break;
                case Office:
                    tier *= 2;
                    break;
                case PhotoEditing:
                    tier *= 4;
                    break;
                case VideoEditing:
                    tier *= 7;
                    break;
                case Rendering3D:
                    tier *= 10;
                    break;
            }
            value.setXValue(1 / tier * 1000);
        }
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

    @Override
    public String toString() {
        return brand
                + " "
                + name
                + " "
                + formatAsMemoryCapacity(capacityGb)
                + " "
                + formFactor
                + " "
                + type;
    }
}
