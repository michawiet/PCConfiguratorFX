package components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class Gpu extends Product {
    private String chipset;
    private int memoryGb;
    private int coreClockMhz;
    private int boostClockMhz;
    private int lengthMillimeters;
    private int tdpW;
    private int recommendedPsuWatts;
    private int performance;

    public static List<TableColumn> getColumns() {
        List<TableColumn> columns = Product.getBasicColumns();
        //class specific columns
        columns.add(new TableColumn<Gpu, String>("Chipset"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("chipset"));
        columns.add(new TableColumn<Gpu, Integer>("Memory"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("memoryGb"));
        columns.get(columns.size() - 1).setCellFactory((column) -> getIntegerTableCell("GB"));
        //Merged column with the title "Clock"
        columns.add(new TableColumn<Gpu, Integer>("Core"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("coreClockMhz"));
        columns.get(columns.size() - 1).setCellFactory((column) -> getIntegerTableCell("MHz"));
        columns.add(new TableColumn<Gpu, Integer>("Boost"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("boostClockMhz"));
        columns.get(columns.size() - 1).setCellFactory((column) -> getIntegerTableCell("MHz"));
        columns.add(new TableColumn<Gpu, Integer>("Length"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("lengthMillimeters"));
        columns.add(new TableColumn<Gpu, Integer>("TDP"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("tdpW"));
        columns.get(columns.size() - 1).setCellFactory((column) -> getIntegerTableCell("W"));
        columns.add(new TableColumn<Gpu, Integer>("Recomm. PSU"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("recommendedPsuWatts"));
        columns.get(columns.size() - 1).setCellFactory((column) -> getIntegerTableCell("W"));
        columns.add(new TableColumn<Gpu, Integer>("Performance"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("performance"));
        //last columns
        columns.add(getPriceColumn());

        return columns;
    }

    @Override
    public ProductType getProductType() {
        return ProductType.Gpu;
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

    @Override
    public String toString() {
        return brand
                + " "
                + chipset
                + " "
                + memoryGb
                + " GB "
                + name;
    }
}
