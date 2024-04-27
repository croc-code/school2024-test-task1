package com.volodin.jsonclasses;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.volodin.orderclasses.Order;

import java.io.*;
import java.util.List;

/**
 * Класс, осуществляющий работу с JSON файлами
 */
public class JsonWorker {
    private final String pathOfJson;

    /**
     * Конструктор класса JsonWorker.
     * @param pathOfJson путь к JSON файлу
     */
    public JsonWorker(String pathOfJson) {
        this.pathOfJson = pathOfJson;
    }

    /**
     * Получает список заказов из JSON файла.
     * @return список заказов, считанный из файла
     * @throws IOException при возникновении ошибки ввода-вывода при чтении файла
     */
    public List<Order> getOrdersFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // модуль для поддержки date/time API
        return objectMapper.readValue(new File(pathOfJson), new TypeReference<List<Order>>() {
        });

    }
    /**
     * Создает файл отчета и записывает в него переданный отчет в формате JSON.
     * @param report отчет, который нужно записать в файл
     */
    public void createReportFile(String report) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("result.json"))) {
            writer.write(report);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
