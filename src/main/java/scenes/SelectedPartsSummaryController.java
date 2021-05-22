package scenes;

import components.Product;
import helpers.SceneHubSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectedPartsSummaryController {

    @FXML
    private ImageView cpuImageView;

    @FXML
    private ImageView coolerImageView;

    @FXML
    private ImageView motherboardImageView;

    @FXML
    private ImageView ramImageView;

    @FXML
    private ImageView storageImageView;

    @FXML
    private ImageView gpuImageView;

    @FXML
    private ImageView caseImageView;

    @FXML
    private ImageView psuImageView;

    @FXML
    private Label cpuNameLabel;

    @FXML
    private Label coolerNameLabel;

    @FXML
    private Label motherboardNameLabel;

    @FXML
    private Label ramNameLabel;

    @FXML
    private Label storageNameLabel;

    @FXML
    private Label gpuNameLabel;

    @FXML
    private Label caseNameLabel;

    @FXML
    private Label psuNameLabel;

    @FXML
    private Label cpuPriceLabel;

    @FXML
    private Label coolerPriceLabel;

    @FXML
    private Label motherboardPriceLabel;

    @FXML
    private Label ramPriceLabel;

    @FXML
    private Label storagePriceLabel;

    @FXML
    private Label gpuPriceLabel;

    @FXML
    private Label casePriceLabel;

    @FXML
    private Label psuPriceLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private PieChart costPieChart;

    private ObservableList<PieChart.Data> costPieChartData;

    private ObservableList<Product> selectedProducts;

    public static String getTitle() {
        return "Part Selection Summary";
    }

    @FXML
    public void initialize() {
        this.costPieChartData = FXCollections.observableList(new ArrayList<>());
        this.costPieChartData.setAll(costPieChartData);
        this.costPieChart.setData(costPieChartData);

        selectedProducts = FXCollections.observableList(new ArrayList<>());
        selectedProducts.addListener((ListChangeListener<Product>) c -> {
            c.next();
            if(c.wasAdded()) {
                for(var it : c.getAddedSubList()) {
                    this.costPieChartData.add(new PieChart.Data(it.getProductType().toString(), it.getPrice()));
                }
            }
            if(c.wasRemoved()) {
                for(var it : c.getRemoved()) {
                    this.costPieChartData.removeIf(r -> r.getName() == it.getProductType().toString());
                }
            }
        });
    }

    @FXML
    private void loadCaseSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("CaseSceneController.fxml"));
        SceneHubSingleton.getInstance().switchToScene(root)
                .setTitle("Case selection");
    }

    @FXML
    private void loadCoolerSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("CoolerSceneController.fxml"));
        SceneHubSingleton.getInstance().switchToScene(root)
                .setTitle("CPU Cooler selection");
    }

    @FXML
    private void loadCpuSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("CpuSceneController.fxml"));
        SceneHubSingleton.getInstance().switchToScene(root)
                .setTitle("Processor (CPU) selection");
    }

    @FXML
    private void loadGpuSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GpuSceneController.fxml"));
        SceneHubSingleton.getInstance().switchToScene(root)
                .setTitle("Video Card (GPU) selection");
    }

    @FXML
    private void loadMotherboardSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MotherboardSceneController.fxml"));
        SceneHubSingleton.getInstance().switchToScene(root)
                .setTitle("Motherboard selection");
    }

    @FXML
    private void loadPsuSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("PsuSceneController.fxml"));
        SceneHubSingleton.getInstance().switchToScene(root)
                .setTitle("Power Supply (PSU) selection");
    }

    @FXML
    private void loadRamSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("RamSceneController.fxml"));
        SceneHubSingleton.getInstance().switchToScene(root)
                .setTitle("Memory (RAM) selection");
    }

    @FXML
    private void loadStorageSelectionScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("StorageSceneController.fxml"));
        SceneHubSingleton.getInstance().switchToScene(root)
                .setTitle("Storage (SSD/HDD) selection");
    }

    public void addSelectedProduct(Product product) {
        //TODO: add the product to some container, which can be used for db storage
        this.selectedProducts.removeIf(p -> p.getProductType() == product.getProductType());
        this.selectedProducts.add(product);

        ImageView imageView;
        Label nameLabel;
        Label priceLabel;

        switch(product.getProductType()) {
            case Case -> {
                imageView = caseImageView;
                nameLabel = caseNameLabel;
                priceLabel = casePriceLabel;
            }
            case Cooler -> {
                imageView = coolerImageView;
                nameLabel = coolerNameLabel;
                priceLabel = coolerPriceLabel;
            }
            case Cpu -> {
                imageView = cpuImageView;
                nameLabel = cpuNameLabel;
                priceLabel = cpuPriceLabel;
            }
            case Gpu -> {
                imageView = gpuImageView;
                nameLabel = gpuNameLabel;
                priceLabel = gpuPriceLabel;
            }
            case Motherboard -> {
                imageView = motherboardImageView;
                nameLabel = motherboardNameLabel;
                priceLabel = motherboardPriceLabel;
            }
            case Psu -> {
                imageView = psuImageView;
                nameLabel = psuNameLabel;
                priceLabel = psuPriceLabel;
            }
            case Ram -> {
                imageView = ramImageView;
                nameLabel = ramNameLabel;
                priceLabel = ramPriceLabel;
            }
            case Storage -> {
                imageView = storageImageView;
                nameLabel = storageNameLabel;
                priceLabel = storagePriceLabel;
            }
            default -> throw new IllegalStateException("Unexpected value: " + product.getProductType());
        }

        var tooltip = new Tooltip();
        tooltip.setGraphic(new ImageView(product.getImage()));
        Tooltip.install(imageView, tooltip);
        imageView.setImage(product.getImageView().getImage());
        nameLabel.setText(product.getBrand() + " " + product.getName());
        nameLabel.setDisable(false);
        priceLabel.setText(product.getPrice() + " PLN");
        priceLabel.setDisable(false);
    }
}
