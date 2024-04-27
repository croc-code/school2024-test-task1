package ru.sarmosov.task.service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JsonFileWriter {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String JSON_PATH;

    public JsonFileWriter(String JSON_PATH) {
        this.JSON_PATH = JSON_PATH;
    }

    public void writeList(List<Month> list) {
        try (FileWriter fw = new FileWriter(JSON_PATH)) {
            Map<String, List<Month>> map = new TreeMap<>();
            map.put("months", list);
            String jsonResult = mapper.writeValueAsString(map);
            jsonResult = jsonResult.toLowerCase();
            fw.write(jsonResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
