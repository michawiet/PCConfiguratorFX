package components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Motherboard extends Product {
    private float tier;
    private String chipset;
    private String socket;
    private String formFactor;
    private int memorySlots;
    private int memoryMaxGb;

    public static ArrayList<TableColumn> getColumns() {
        ArrayList<TableColumn> columns = new ArrayList<>();

        //image column
        columns.add(new TableColumn<Motherboard, ImageView>(""));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("imageView"));
        columns.get(columns.size() - 1).setResizable(false);
        columns.get(columns.size() - 1).setMinWidth(70);
        columns.get(columns.size() - 1).setMaxWidth(70);
        columns.get(columns.size() - 1).setSortable(false);
        //first two columns
        columns.add(new TableColumn<Motherboard, String>("Brand"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("brand"));
        columns.add(new TableColumn<Motherboard, String>("Name"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("name"));
        //class specific columns
        columns.add(new TableColumn<Motherboard, Float>("Tier"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("tier"));
        columns.add(new TableColumn<Motherboard, String>("Chipset"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("chipset"));
        columns.add(new TableColumn<Motherboard, String>("Socket"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("socket"));
        columns.add(new TableColumn<Motherboard, String>("Form factor"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("formFactor"));
        columns.add(new TableColumn<Motherboard, Integer>("Memory slots"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("memorySlots"));
        columns.add(new TableColumn<Motherboard, Integer>("Max memory"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("memoryMaxGb"));
        //last columns
        columns.add(new TableColumn<Motherboard, Double>("Price"));
        columns.get(columns.size() - 1).setCellValueFactory(new PropertyValueFactory<>("price"));

        return columns;
    }

    public Motherboard() {

    }

    public Motherboard(String _id, String image, String brand, String name, Double price, float tier, String chipset, String socket, String formFactor, int memorySlots, int memoryMaxGb) {
        super(_id, image, brand, name, price);
        this.tier = tier;
        this.chipset = chipset;
        this.socket = socket;
        this.formFactor = formFactor;
        this.memorySlots = memorySlots;
        this.memoryMaxGb = memoryMaxGb;
    }

    public float getTier() {
        return tier;
    }

    public void setTier(float tier) {
        this.tier = tier;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public int getMemorySlots() {
        return memorySlots;
    }

    public void setMemorySlots(int memorySlots) {
        this.memorySlots = memorySlots;
    }

    public int getMemoryMaxGb() {
        return memoryMaxGb;
    }

    public void setMemoryMaxGb(int memoryMaxGb) {
        this.memoryMaxGb = memoryMaxGb;
    }
}
