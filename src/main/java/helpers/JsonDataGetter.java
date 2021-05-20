package helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import components.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonDataGetter {
    private static JsonDataGetter instance;

    public static JsonDataGetter getInstance() {
        if(instance == null) {
            instance = new JsonDataGetter();
        }
        return instance;
    }

    private ObjectMapper mapper;

    private JsonDataGetter() {
        this.mapper = new ObjectMapper();
    }

    public List<Cpu> getCpuList() throws IOException {
        return Arrays.asList(this.mapper.readValue(getClass().getResource("/json/cpu.json"), Cpu[].class));
    }

    public List<Cooler> getCoolerList() throws IOException {
        return Arrays.asList(this.mapper.readValue(getClass().getResource("/json/cooler.json"), Cooler[].class));
    }

    public List<Case> getCaseList() throws IOException {
        return Arrays.asList(this.mapper.readValue(getClass().getResource("/json/case.json"), Case[].class));
    }

    public List<Gpu> getGpuList() throws IOException {
        return Arrays.asList(this.mapper.readValue(getClass().getResource("/json/gpu.json"), Gpu[].class));
    }

    public List<Motherboard> getMotherboardList() throws IOException {
        return Arrays.asList(this.mapper.readValue(getClass().getResource("/json/motherboard.json"), Motherboard[].class));
    }

    public List<Psu> getPsuList() throws IOException {
        return Arrays.asList(this.mapper.readValue(getClass().getResource("/json/psu.json"), Psu[].class));
    }

    public List<Ram> getRamList() throws IOException {
        return Arrays.asList(this.mapper.readValue(getClass().getResource("/json/ram.json"), Ram[].class));
    }

    public List<Storage> getStorageList() throws IOException {
        return Arrays.asList(this.mapper.readValue(getClass().getResource("/json/storage.json"), Storage[].class));
    }
}
