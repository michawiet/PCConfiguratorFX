package components;

import com.fasterxml.jackson.annotation.JsonProperty;
import helpers.WorkloadType;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class Cpu extends Product {

    //creates a arrayList of columns compatible with this class
    public static List<TableColumn> getColumns() {
        List<TableColumn> columns = Product.getBasicColumns();
        //class specific columns
        columns.add(new TableColumn<Cpu, String>("Socket"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("socket"));
        columns.add(new TableColumn<Cpu, Integer>("Cores"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("cores"));
        columns.add(new TableColumn<Cpu, String>("SMT"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("smt"));
        columns.add(new TableColumn<Cpu, String>("iGPU"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("igpu"));
        columns.add(new TableColumn<Cpu, Integer>("TDP"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("tdp"));
        columns.get(columns.size() - 1).setCellFactory((column) -> getIntegerTableCell("W"));
        //Merged column with the title "Performance"
        columns.add(new TableColumn<Cpu, Integer>("ST"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("stPerformance"));
        columns.add(new TableColumn<Cpu, Integer>("MT"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("mtPerformance"));
        //Merged column with the title "Clock"
        columns.add(new TableColumn<Cpu, Float>("Core"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("coreClock"));
        columns.get(columns.size() - 1).setCellFactory((column) -> getGHzFrequencyTableCell());
        columns.add(new TableColumn<Cpu, Float>("Boost"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("boostClock"));
        columns.get(columns.size() - 1).setCellFactory(new PropertyValueFactory<>("boostClock"));
        columns.get(columns.size() - 1).setCellFactory((column) -> getGHzFrequencyTableCell());
        //last columns
        columns.add(getPriceColumn());

        return columns;
    }

    @JsonProperty
    private String socket;

    @JsonProperty
    private int cores;

    @JsonProperty
    private boolean smt;

    @JsonProperty
    private boolean igpu;

    @JsonProperty
    private int tdp;

    @JsonProperty
    private int stPerformance;

    @JsonProperty
    private int mtPerformance;

    @JsonProperty
    private float coreClock;

    @JsonProperty
    private float boostClock;
    
    @Override
    public ProductType getProductType() {
        return ProductType.Cpu;
    }

    @Override
    public void setPerformanceValues(ObservableList<XYChart.Data<Number, String>> performanceValues) {
        for(var value : performanceValues) {
            float st = stPerformance;
            float mt = mtPerformance;
            switch (WorkloadType.toEnum(value.getYValue())) {
                case Gaming:
                    st *= 0.6f;
                    mt *= 0.7f;
                    break;
                case Office:
                    st *= 1.2f;
                    break;
                case PhotoEditing:
                    st *= 0.6f;
                    mt *= 0.9f;
                    break;
                case VideoEditing:
                    st *= 0.9f;
                    mt *= 0.4f;
                    break;
                case Rendering3D:
                    mt *= 0.4f;
                    break;
            }
            value.setXValue((st + mt) / 40.f);
        }
    }

    public String getSocket() {
        return socket;
    }

    public Integer getCores() {
        return cores;
    }

    public String getSmt() {
        return getBooleanStringValue(this.smt);
    }

    public String getIgpu() {
        return this.getBooleanStringValue(this.igpu);
    }

    public Integer getTdp() {
        return tdp;
    }

    public Integer getStPerformance() {
        return stPerformance;
    }

    public Integer getMtPerformance() {
        return mtPerformance;
    }

    public Float getCoreClock() {
        return coreClock;
    }

    public Float getBoostClock() {
        return boostClock;
    }

    @JsonProperty
    public void setSocket(String socket) {
        this.socket = socket;
    }

    @JsonProperty
    public void setCores(int cores) {
        this.cores = cores;
    }

    @JsonProperty
    public void setSmt(boolean smt) {
        this.smt = smt;
    }

    @JsonProperty
    public void setIgpu(boolean igpu) {
        this.igpu = igpu;
    }

    @JsonProperty
    public void setTdp(int tdp) {
        this.tdp = tdp;
    }

    @JsonProperty
    public void setStPerformance(int stPerformance) {
        this.stPerformance = stPerformance;
    }

    @JsonProperty
    public void setMtPerformance(int mtPerformance) {
        this.mtPerformance = mtPerformance;
    }

    @JsonProperty
    public void setCoreClock(float coreClock) {
        this.coreClock = coreClock;
    }

    @JsonProperty
    public void setBoostClock(float boostClock) {
        this.boostClock = boostClock;
    }

    @Override
    public String toString() {
        return brand
                + " "
                + name
                + " "
                + cores
                + "-Core Processor"
                ;
    }
}
