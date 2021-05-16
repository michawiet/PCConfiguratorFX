package components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Psu extends Product {
    private float tier;
    private String formFactor;
    private String efficiencyRating;
    private Integer wattage;
    private String modular;

    //TODO: public static explanation
    public static ArrayList<TableColumn> getColumns() {
        ArrayList<TableColumn> columns = new ArrayList<>();

        //image column
        columns.add(new TableColumn<Psu, ImageView>(""));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("imageView"));
        columns.get(columns.size() - 1).setResizable(false);
        columns.get(columns.size() - 1).setMinWidth(70);
        columns.get(columns.size() - 1).setMaxWidth(70);
        columns.get(columns.size() - 1).setSortable(false);
        //first two columns
        columns.add(new TableColumn<Psu, String>("Brand"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("brand"));
        columns.add(new TableColumn<Psu, String>("Name"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("name"));
        //class specific columns
        columns.add(new TableColumn<Psu, Float>("Tier"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("tier"));
        columns.add(new TableColumn<Psu, String>("Form factor"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("formFactor"));
        columns.add(new TableColumn<Psu, String>("Eff. Rating"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("efficiencyRating"));
        columns.add(new TableColumn<Psu, Integer>("Wattage"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("wattage"));
        columns.add(new TableColumn<Psu, String>("Modularity"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("modular"));
        //last columns
        columns.add(new TableColumn<Psu, Double>("Price"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("price"));

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
