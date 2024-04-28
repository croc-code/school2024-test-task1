package ru.croc.javaschool2024.marketplace.utils;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonReport {

    /**
     * Метод отвечающий за вывод информации в json
     *
     * @param jsonMonthInfo - информация для вывода
     */
    public static String generateReport(String jsonMonthInfo) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(jsonMonthInfo, Object.class);
        return mapper.writeValueAsString(json);
    }
}