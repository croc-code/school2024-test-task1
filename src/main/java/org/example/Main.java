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
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("input.json")) {
            Type orderListType = new TypeToken<List<Order>>(){}.getType();
            List<Order> orders = gson.fromJson(reader, orderListType);

            if (orders != null) {
                String report = generateReport(orders);
                System.out.println(report);


            } else {
                System.out.println("Не удалось прочитать данные из файла.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateReport(List<Order> orders) {
        Map<String, Double> monthSumMap = new HashMap<>();
        orders.stream()
                .filter(order -> "COMPLETED".equals(order.getStatus()))
                .sorted(Comparator.comparing(order -> order.getOrdered_at().getMonth()))
                .forEach(x -> {
                    String month = x.getOrdered_at().getMonth().toString().toLowerCase();
                    double currentSum = monthSumMap.getOrDefault(month, 0.0);
                    currentSum += x.getTotal();

                    monthSumMap.put(month, currentSum);
                });
        double maxSum = monthSumMap.values().stream().max(Double::compareTo).orElse(0.0);

        List<String> monthsWithMaxSum = monthSumMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(maxSum))
                .map(Entry::getKey)
                .collect(Collectors.toList());

        Map<String, List<String>> reportMap = new HashMap<>();
        reportMap.put("months", monthsWithMaxSum);

        Gson gson = new Gson();
        return gson.toJson(reportMap);
    }
}