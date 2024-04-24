package org.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            // Чтение данных из JSON файла и запись в переменную
            String content = new String(Files.readAllBytes(Paths.get("input.json")));
            JSONArray jsonArray = new JSONArray(content);

            // Подсчет общей суммы для каждого месяца
            Map<String, Double> monthTotal = new HashMap<>();
            getData(jsonArray, monthTotal);

            // Вывод ответа
            System.out.println(createReport(monthTotal));

        } catch (NoSuchFileException | AccessDeniedException |
                 InvalidPathException e) {
            // Обработка ошибок, связанных с отсутствием файла, доступом и неверным путем
            System.out.printf("Error: %s", e.getMessage());
        } catch (IOException e) {
            // Обработка ошибок ввода-вывода
            System.out.printf("Error input-output: %s", e.getMessage());
        } catch (JSONException e) {
            // Обработка ошибок при разборе JSON
            System.out.printf("Error parsing JSON: %s", e.getMessage());
        }
    }

    // Метод для извлечения данных из JSON массива и подсчета общей суммы для каждого месяца
    private static void getData(JSONArray jsonArray, Map<String, Double> monthTotal) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        for (int i = 0; i < jsonArray.length(); i++) {

            // Получение JSON объекта из массива
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String status = jsonObject.getString("status");
            if (status.equals("COMPLETED")) {
                String date = jsonObject.getString("ordered_at");
                LocalDate formattedDate = LocalDate.parse(date, formatter);
                String month = formattedDate.getMonth().toString().toLowerCase();
                Double total = jsonObject.getDouble("total");

                // Обновление общей суммы для данного месяца в мапе
                monthTotal.put(month, monthTotal.getOrDefault(month, 0.0) + total);
            }
        }
    }
    // Метод для создания отчета с максимальными расходами по месяцам
    private static StringBuilder createReport(Map<String, Double> monthTotal) {
        double maxSpending = Double.MIN_VALUE;
        // Поиск максимального расхода за месяц
        for (Double value : monthTotal.values()) {
            maxSpending = Math.max(maxSpending, value);
        }
        // Формирование ответа в формате, согласно условию задания
        StringBuilder stringBuilder = new StringBuilder("{\"months:\": [");
        boolean isFirst = true;
        for (Map.Entry<String, Double> entry : monthTotal.entrySet()) {
            if (entry.getValue() == maxSpending) {
                if (!isFirst) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append("\"").append(entry.getKey()).append("\"");
                isFirst = false;
            }
        }
        stringBuilder.append("]}");
        return stringBuilder;
    }
}