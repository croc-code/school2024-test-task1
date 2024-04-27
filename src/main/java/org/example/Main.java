package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Order> orders = readOrders("input.json");
        Map<String, Double> monthlyTotal = calculateMonthlyTotal(orders);
        
        List<String> topSpendingMonths = findTopSpendingMonths(monthlyTotal);
        System.out.println("Top Spending Months:");
        for (String month : topSpendingMonths) {
            System.out.println(month);
        }
    }


    // Метод для чтения JSON-файла и возврата списка заказов
    private static List<Order> readOrders(String filename) {
        List<Order> orders = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Преобразование JSON-файла в список объектов Order
            orders = mapper.readValue(new File(filename),
                    mapper.getTypeFactory().constructCollectionType(List.class, Order.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // Метод для вычисления общей суммы заказов для каждого месяца

    private static Map<String, Double> calculateMonthlyTotal(List<Order> orders) {
            Map<String, Double> monthlyTotal = new HashMap<>();
            String[] months = new DateFormatSymbols(Locale.ENGLISH).getMonths();

            for (Order order : orders) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(order.getOrdered_at());
                int month = cal.get(Calendar.MONTH);
                double total = order.getTotal();

                String monthName = months[month];
                monthlyTotal.put(monthName, monthlyTotal.getOrDefault(monthName, 0.0) + total);
            }
            return monthlyTotal;
    }


    //Метод для нахождения месяца с наибольшими тратами
    private static List<String> findTopSpendingMonths(Map<String, Double> monthlyTotal) {
        List<String> topMonths = new ArrayList<>();
        double maxTotal = Collections.max(monthlyTotal.values());

        for (Map.Entry<String, Double> entry : monthlyTotal.entrySet()) {
            if (entry.getValue() == maxTotal) {
                topMonths.add(entry.getKey());
            }
        }

        return topMonths;
    }
}
