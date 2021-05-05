package components;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Product {
    @JsonIgnore
    protected ImageView imageView;

    @JsonProperty
    private String _id;

    @JsonProperty
    protected String image;

    @JsonProperty
    protected String brand;

    @JsonProperty
    protected String name;

    @JsonProperty
    protected Double price;

    public Product(String _id, String image, String brand, String name, Double price) {
        this._id = _id;
        this.image = image;
        this.brand = brand;
        this.name = name;
        this.price = price;
    }

    public Product() {

    }

    // Returns "Yes" for true and "No" for false. Can be non static, since it is only used by the classes that inherit Product
    @JsonIgnore
    protected static String getBooleanStringValue(boolean value) {
        return (value ? "Yes" : "No");
    }

    @JsonProperty
    public String getImage() {
        return image;
    }

    @JsonProperty
    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty
    public String get_id() {
        return _id;
    }

    @JsonProperty
    public String getBrand() {
        return brand;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public Double getPrice() {
        return price;
    }

    @JsonProperty
    public void set_id(String _id) {
        this._id = _id;
    }

    @JsonProperty
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public void setPrice(Double price) {
        this.price = price;
    }
}
