package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Map.Entry;

public class Main {
    public static void main(String[] args) {

        /* Считываем данные из файла */
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("input.json")) {
            /* Преобразуем JSON в список заказов */
            Type orderListType = new TypeToken<List<Order>>(){}.getType();
            List<Order> orders = gson.fromJson(reader, orderListType);


            if (orders != null) {
                /* Генерируем отчет */
                String report = generateReport(orders);
                System.out.println(report);


            } else {
                System.out.println("Не удалось прочитать данные из файла.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Генерация отчета
     * @param orders список заказов
     * @return отчет в формате JSON
     */
    private static String generateReport(List<Order> orders) {
        /* Создаем мапу, где ключ - месяц, значение - сумма заказов за данный месяц */
        Map<String, Double> monthSumMap = new HashMap<>();
        orders.stream()
                /* Фильтруем заказы со статусом "COMPLETED" */
                .filter(order -> "COMPLETED".equals(order.getStatus()))
                /* Сортируем заказы по месяцу */
                .sorted(Comparator.comparing(order -> order.getOrdered_at().getMonth()))
                .forEach(x -> {
                    /* Получаем месяц из даты заказа */
                    String month = x.getOrdered_at().getMonth().toString().toLowerCase();
                    /* Получаем текущую сумму для данного месяца или 0, если месяц еще не встречался */
                    double currentSum = monthSumMap.getOrDefault(month, 0.0);
                    /* Обновляем сумму для данного месяца, добавляя стоимость текущего заказа */
                    currentSum += x.getTotal();
                    /* Сохраняем обновленную сумму в мапу */
                    monthSumMap.put(month, currentSum);
                });
        /* Находим максимальную сумму заказов */
        double maxSum = monthSumMap.values().stream().max(Double::compareTo).orElse(0.0);

        /* Получаем список месяцев с максимальной суммой заказов */
        List<String> monthsWithMaxSum = monthSumMap.entrySet().stream()
                /* Фильтруем месяцы с максимальной суммой */
                .filter(entry -> entry.getValue().equals(maxSum))
                /* Получаем список месяцев */
                .map(Entry::getKey)
                /* Преобразуем в список */
                .collect(Collectors.toList());

        /* Создаем мапу для отчета */
        Map<String, List<String>> reportMap = new HashMap<>();
        reportMap.put("months", monthsWithMaxSum);
        Gson gson = new Gson();
        return gson.toJson(reportMap);
    }
}