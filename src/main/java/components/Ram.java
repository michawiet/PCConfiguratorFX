package components;

import helpers.WorkloadType;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class Ram extends Product {
    private int speed;
    private int modulesCount;
    private int moduleCapacityGb;
    private float firstWordLatencyNs;
    private int casLatency;

    public static List<TableColumn> getColumns() {
        List<TableColumn> columns = Product.getBasicColumns();
        //class specific columns
        columns.add(new TableColumn<Ram, Integer>("Speed"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("speed"));
        columns.get(columns.size() - 1).setCellFactory((column) -> getIntegerTableCell("MHz"));
        //Merge "Count" and "Capacity" under name modules
        columns.add(new TableColumn<Ram, Integer>("Modules count"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("modulesCount"));
        columns.add(new TableColumn<Ram, Integer>("Module capacity"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("moduleCapacityGb"));
        columns.get(columns.size() - 1).setCellFactory((column) -> getIntegerTableCell("GB"));
        columns.add(new TableColumn<Ram, Integer>("Total capacity"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("totalCapacity"));
        columns.get(columns.size() - 1).setCellFactory((column) -> getIntegerTableCell("GB"));
        columns.add(new TableColumn<Ram, Float>("First word latency"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("firstWordLatencyNs"));
        columns.get(columns.size() - 1).setCellFactory((column) -> new TableCell<Ram, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if(item != null) {
                    if(item != item.longValue())
                        this.setText(item + " ns");
                    else
                        this.setText(item.longValue() + " ns");

                }
            }
        });
        columns.add(new TableColumn<Ram, Integer>("CAS latency"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("casLatency"));
        columns.get(columns.size() - 1).setCellFactory((column) -> new TableCell<Ram, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if(item != null) {
                    this.setText("CL" + item);

                }
            }
        });
        //last columns
        columns.add(getPriceColumn());

        return columns;
    }

    @Override
    public ProductType getProductType() {
        return ProductType.Ram;
    }

    @Override
    public void setPerformanceValues(ObservableList<XYChart.Data<Number, String>> performanceValues) {
        for(var value : performanceValues) {
            float performance = getSpeed() * (modulesCount > 1 ? 1.f : 0.6f);
            switch (WorkloadType.toEnum(value.getYValue())) {
                case Gaming:
                    performance *= Math.log(getTotalCapacity()) * 0.7f / getFirstWordLatencyNs() * 10.f;
                    break;
                case Office:
                    performance *= Math.log10(getTotalCapacity()) * 2.5f;
                    break;
                case PhotoEditing:
                    performance *= Math.log(getTotalCapacity()) * 0.7f;
                    break;
                case VideoEditing:
                    performance *= Math.log10(getTotalCapacity()) * 0.4f;
                    break;
                case Rendering3D:
                    performance *= Math.log(getTotalCapacity()) * 0.55f;
                    break;
            }
            value.setXValue(performance / 100.f);
        }
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

    public int getTotalCapacity() {
        return this.moduleCapacityGb * this.modulesCount;
    }

    @Override
    public String toString() {
        return brand
                + " "
                + name
                + " ("
                + modulesCount
                + " x "
                + moduleCapacityGb
                + " GB) "
                + speed
                + " MHz CL"
                + casLatency
                + " Memory"
                ;
    }
}
