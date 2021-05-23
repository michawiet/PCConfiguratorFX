package components;

public enum ProductType {
    Unknown("UNDEFINED"),
    Case("Case"),
    Cooler("CPU Cooler"),
    Cpu("Processor (CPU)"),
    Gpu("Graphics Card (GPU)"),
    Motherboard("Motherboard"),
    Psu("Power Supply"),
    Ram ("Memory (RAM)"),
    Storage("Storage");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
