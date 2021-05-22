package components;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public abstract class Product {

    @JsonIgnore
    protected static List<TableColumn> getBasicColumns() {
        List<TableColumn> columns = new ArrayList<>();

        //image column
        columns.add(new javafx.scene.control.TableColumn<Product, ImageView>(""));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("imageView"));
        columns.get(columns.size() - 1).setResizable(false);
        columns.get(columns.size() - 1).setMinWidth(70);
        columns.get(columns.size() - 1).setMaxWidth(70);
        columns.get(columns.size() - 1).setSortable(false);
        columns.get(columns.size() - 1).setCellFactory((column) -> new TableCell<Cpu, ImageView>() {
            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);
                this.setGraphic(item);
                if(item != null) {
                    Tooltip tooltip = new Tooltip();
                    tooltip.setGraphic(new ImageView(item.getImage()));
                    setTooltip(tooltip);
                }
            }
        });
        //first two columns
        columns.add(new javafx.scene.control.TableColumn<Product, String>("Brand"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("brand"));
        columns.add(new javafx.scene.control.TableColumn<Product, String>("Name"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("name"));

        return columns;
    }

    @JsonIgnore
    protected static TableColumn getPriceColumn() {
        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setCellFactory((column) -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if(item != null) {
                    this.setText(String.format("%.2f PLN", item));
                }
            }
        });
        return priceColumn;
    }

    protected static <S> TableCell<S, Integer> getIntegerTableCell(String toAddAfterValue) {
        return new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if(item != null) {
                    this.setText(String.format("%d %s", item, toAddAfterValue));
                }
            }
        };
    }

    protected static <S> TableCell<S, Float> getGHzFrequencyTableCell() {
        return new TableCell<>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if(item != null) {
                    this.setText(String.format("%.1f GHz", item));
                }
            }
        };
    }

    // Returns "Yes" for true and "No" for false. Can be non static, since it is only used by the classes that inherit Product
    @JsonIgnore
    protected static String getBooleanStringValue(boolean value) {
        return (value ? "Yes" : "No");
    }

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

    @JsonIgnore
    public abstract ProductType getProductType();

    @JsonIgnore
    public ImageView getImageView() {
        if(this.imageView == null) {
            var image = new Image(getClass().getResource(this.image).toString());
            this.imageView = new ImageView(image);
            this.imageView.setSmooth(true);
            this.imageView.setPreserveRatio(true);
            this.imageView.setFitWidth(64);
            this.imageView.setFitHeight(64);
        }

        return this.imageView;
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

    @JsonIgnore
    public String getComponentName() {
        return this.brand + " " + this.name;
    }
}
