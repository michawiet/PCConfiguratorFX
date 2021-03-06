package components;

import com.fasterxml.jackson.annotation.JsonIgnore;
import helpers.WorkloadType;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class Psu extends Product {
    private float tier;
    private String formFactor;
    private String efficiencyRating;
    private Integer wattage;
    private String modular;

    public static List<TableColumn> getColumns() {
        List<TableColumn> columns = Product.getBasicColumns();
        //class specific columns
        columns.add(new TableColumn<Psu, Float>("Tier"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("tier"));
        columns.add(new TableColumn<Psu, String>("Form factor"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("formFactor"));
        columns.add(new TableColumn<Psu, String>("Eff. Rating"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("efficiencyRating"));
        columns.add(new TableColumn<Psu, Integer>("Wattage"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("wattage"));
        columns.get(columns.size() - 1).setCellFactory((column) -> getIntegerTableCell("W"));
        columns.add(new TableColumn<Psu, String>("Modularity"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("modular"));
        //last columns
        columns.add(getPriceColumn());

        return columns;
    }

    @Override
    public ProductType getProductType() {
        return ProductType.Psu;
    }

    @Override
    public void setPerformanceValues(ObservableList<XYChart.Data<Number, String>> performanceValues) {
        for(var value : performanceValues) {
            float tier = 9.f / getTier();
            switch (WorkloadType.toEnum(value.getYValue())) {
                case Gaming:
                    tier *= 0.7f;
                    break;
                case Office:
                    tier *= 1.1f;
                    break;
                case PhotoEditing:
                    tier *= 0.8f;
                    break;
                case VideoEditing:
                    tier *= 0.65f;
                    break;
                case Rendering3D:
                    tier *= 0.6f;
                    break;
            }
            value.setXValue(tier * 3);
        }
    }

    public float getTier() {
        return tier;
    }

    public void setTier(float tier) {
        this.tier = tier;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public String getEfficiencyRating() {
        return efficiencyRating;
    }

    public void setEfficiencyRating(String efficiencyRating) {
        this.efficiencyRating = efficiencyRating;
    }

    public Integer getWattage() {
        return wattage;
    }

    public void setWattage(Integer wattage) {
        this.wattage = wattage;
    }

    public String getModular() {
        return modular;
    }

    @JsonIgnore
    private String getModularityFormatted() {
        if(modular.equals("No"))
            return "Non modular";
        if(modular.equals("Semi"))
            return "Semi modular";
        return "Fully modular";
    }

    public void setModular(String modular) {
        this.modular = modular;
    }

    @Override
    public String toString() {
        return brand
                + " "
                + name
                + " "
                + wattage
                + " W "
                + efficiencyRating
                + " "
                + getModularityFormatted()
                + " "
                + formFactor
                + " Power Supply";
    }
}
