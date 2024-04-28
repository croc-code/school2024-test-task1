package ru.croc.javaschool2024.marketplace.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.croc.javaschool2024.marketplace.models.Order;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     *  Метод отвечающий за считывание из JSON-файла
     * @param filePath - путь к JSON файлу
     * @return Лист заказов
     */
    public static List<Order> readJsonFile(String filePath) throws IOException {
        return objectMapper.readValue(
                new File(filePath),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Order.class)
        );
    }

}