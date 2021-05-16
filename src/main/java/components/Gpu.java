package components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Gpu extends Product {
    private String chipset;
    private int memoryGb;
    private int coreClockMhz;
    private int boostClockMhz;
    private int lengthMillimeters;
    private int tdpW;
    private int recommendedPsuWatts;
    private int performance;

    public static ArrayList<TableColumn> getColumns() {
        ArrayList<TableColumn> columns = new ArrayList<>();

        //image column
        columns.add(new TableColumn<Gpu, ImageView>(""));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("imageView"));
        columns.get(columns.size() - 1).setResizable(false);
        columns.get(columns.size() - 1).setMinWidth(70);
        columns.get(columns.size() - 1).setMaxWidth(70);
        columns.get(columns.size() - 1).setSortable(false);
        //first two columns
        columns.add(new TableColumn<Gpu, String>("Brand"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("brand"));
        columns.add(new TableColumn<Gpu, String>("Name"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("name"));
        //class specific columns
        columns.add(new TableColumn<Gpu, String>("Chipset"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("chipset"));
        columns.add(new TableColumn<Gpu, Integer>("Memory"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("memoryGb"));
        //Merged column with the title "Clock"
        columns.add(new TableColumn<Gpu, Integer>("Core"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("coreClockMhz"));
        columns.add(new TableColumn<Gpu, Integer>("Boost"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("boostClockMhz"));
        columns.add(new TableColumn<Gpu, Integer>("Length"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("lengthMillimeters"));
        columns.add(new TableColumn<Gpu, Integer>("TDP"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("tdpW"));
        columns.add(new TableColumn<Gpu, Integer>("Recommended PSU"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("recommendedPsuWatts"));
        columns.add(new TableColumn<Gpu, Integer>("Performance"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("performance"));
        //last columns
        columns.add(new TableColumn<Gpu, Double>("Price"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("price"));

        return columns;
    }

    public Gpu() {

    }

    @Override
    public ProductType getProductType() {
        return ProductType.Gpu;
    }

    public Gpu(String _id, String image, String brand, String name, Double price, String chipset, int memoryGb, int coreClockMhz, int boostClockMhz, int lengthMillimeters, int tdpW, int recommendedPsuWatts, int performance) {
        super(_id, image, brand, name, price);
        this.chipset = chipset;
        this.memoryGb = memoryGb;
        this.coreClockMhz = coreClockMhz;
        this.boostClockMhz = boostClockMhz;
        this.lengthMillimeters = lengthMillimeters;
        this.tdpW = tdpW;
        this.recommendedPsuWatts = recommendedPsuWatts;
        this.performance = performance;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public int getMemoryGb() {
        return memoryGb;
    }

    public void setMemoryGb(int memoryGb) {
        this.memoryGb = memoryGb;
    }

    public int getCoreClockMhz() {
        return coreClockMhz;
    }

    public void setCoreClockMhz(int coreClockMhz) {
        this.coreClockMhz = coreClockMhz;
    }

    public int getBoostClockMhz() {
        return boostClockMhz;
    }

    public void setBoostClockMhz(int boostClockMhz) {
        this.boostClockMhz = boostClockMhz;
    }

    public int getLengthMillimeters() {
        return lengthMillimeters;
    }

    public void setLengthMillimeters(int lengthMillimeters) {
        this.lengthMillimeters = lengthMillimeters;
    }

    public int getTdpW() {
        return tdpW;
    }

    public void setTdpW(int tdpW) {
        this.tdpW = tdpW;
    }

    public int getRecommendedPsuWatts() {
        return recommendedPsuWatts;
    }

    public void setRecommendedPsuWatts(int recommendedPsuWatts) {
        this.recommendedPsuWatts = recommendedPsuWatts;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }
}
