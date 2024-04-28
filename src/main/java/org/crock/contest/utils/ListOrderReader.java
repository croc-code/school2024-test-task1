package org.crock.contest.utils;

import org.crock.contest.converter.ConverterFromJson;
import org.crock.contest.order.Order;

import java.io.*;
import java.util.List;

/**
 * Класс для считывания массива Order
 */
public class ListOrderReader implements Reader<List<Order>> {

    private final ConverterFromJson<List<Order>> converter;

    public ListOrderReader(ConverterFromJson<List<Order>> converter) {
        this.converter = converter;
    }

    @Override
    public List<Order> readObjectFromJsonFile(String path) {
        StringBuilder json = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String jsonLine;
            while ((jsonLine = in.readLine()) != null) {
                json.append(jsonLine);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл " + path + " не найден.");
        } catch (IOException e) {
            throw new RuntimeException("Не удалось открыть файл " + path);
        }
        return converter.json2Object(json.toString());
    }
}