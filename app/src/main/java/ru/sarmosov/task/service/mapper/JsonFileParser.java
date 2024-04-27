package ru.sarmosov.task.service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sarmosov.task.entity.Purchase;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class JsonFileParser {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String JSON_PATH;

    public JsonFileParser(String JSON_PATH) {
        this.JSON_PATH = JSON_PATH;
    }


    public List<Purchase> getListFromJson() {
        try (FileInputStream fis = new FileInputStream(JSON_PATH)) {
            return Arrays.asList(mapper.readValue(fis, Purchase[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
