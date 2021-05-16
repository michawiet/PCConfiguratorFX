package helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.*;
import components.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

//TODO: add SLF4J or disable the warning
public class DatabaseData {
    private static DatabaseData instance;

    private MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    private MongoDatabase database = mongoClient.getDatabase("PC_Configurator_FX");

    public static DatabaseData getInstance() {
        if(instance == null) {
            instance = new DatabaseData();
        }
        return instance;
    }

    private DatabaseData() {
    }

    private List<String> getJsonData(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        List<String> list = new ArrayList<>();

        FindIterable<Document> fi = collection.find();
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while (cursor.hasNext()) {
                list.add(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        return list;
    }

    //returns list
    public List<Cpu> getCpuList() throws JsonProcessingException {
        List<String> jsonList = this.getJsonData("cpu");
        List<Cpu> resultList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for(String el : jsonList) {
            resultList.add(objectMapper.readValue(el, Cpu.class));
        }

        return resultList;
    }

    //returns list
    public List<Cooler> getCoolerList() throws JsonProcessingException {
        List<String> jsonList = this.getJsonData("cooler");
        List<Cooler> resultList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for(String el : jsonList) {
            resultList.add(objectMapper.readValue(el, Cooler.class));
        }

        return resultList;
    }

    //returns list
    public List<Case> getCaseList() throws JsonProcessingException {
        List<String> jsonList = this.getJsonData("case");
        List<Case> resultList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for(String el : jsonList) {
            resultList.add(objectMapper.readValue(el, Case.class));
        }

        return resultList;
    }

    //returns list
    public List<Gpu> getGpuList() throws JsonProcessingException {
        List<String> jsonList = this.getJsonData("gpu");
        List<Gpu> resultList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for(String el : jsonList) {
            resultList.add(objectMapper.readValue(el, Gpu.class));
        }

        return resultList;
    }

    //returns list
    public List<Motherboard> getMotherboardList() throws JsonProcessingException {
        List<String> jsonList = this.getJsonData("motherboard");
        List<Motherboard> resultList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for(String el : jsonList) {
            resultList.add(objectMapper.readValue(el, Motherboard.class));
        }

        return resultList;
    }

    //returns list
    public List<Psu> getPsuList() throws JsonProcessingException {
        List<String> jsonList = this.getJsonData("psu");
        List<Psu> resultList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for(String el : jsonList) {
            resultList.add(objectMapper.readValue(el, Psu.class));
        }

        return resultList;
    }

    //returns list
    public List<Ram> getRamList() throws JsonProcessingException {
        List<String> jsonList = this.getJsonData("ram");
        List<Ram> resultList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for(String el : jsonList) {
            resultList.add(objectMapper.readValue(el, Ram.class));
        }

        return resultList;
    }

    //returns list
    public List<Storage> getStorageList() throws JsonProcessingException {
        List<String> jsonList = this.getJsonData("storage");
        List<Storage> resultList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for(String el : jsonList) {
            resultList.add(objectMapper.readValue(el, Storage.class));
        }

        return resultList;
    }
}
