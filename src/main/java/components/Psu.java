package components;

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
        columns.get(columns.size() - 1).setCellFactory((column) -> getWattageTableCell());
        columns.add(new TableColumn<Psu, String>("Modularity"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("modular"));
        //last columns
        columns.add(getPriceColumn());

        return columns;
    }

    public Psu() {

    }

    @Override
    public ProductType getProductType() {
        return ProductType.Psu;
    }

    public Psu(String _id, String image, String brand, String name, Double price, float tier, String formFactor, String efficiencyRating, Integer wattage, String modular) {
        super(_id, image, brand, name, price);
        this.tier = tier;
        this.formFactor = formFactor;
        this.efficiencyRating = efficiencyRating;
        this.wattage = wattage;
        this.modular = modular;
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

    public void setModular(String modular) {
        this.modular = modular;
    }
}
