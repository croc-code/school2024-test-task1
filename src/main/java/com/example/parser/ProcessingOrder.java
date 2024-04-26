package com.example.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class ProcessingOrder {
    private static final String status = "COMPLETED";
    private static final List<String> allMonths = Arrays.asList("«january»", "«february»", "«march»", "«april»",
            "«may»", "«june»", "«july»", "«august»", "«september»", "«october»", "«november»", "«december»");

    //Получение списка заказов со статусом COMPLETED из файла input.json
    public static List<Order> parseJson(String path) throws IOException {
        List<Order> listOfOrders = new ObjectMapper().readValue(new File(path), new TypeReference<>(){});
        return listOfOrders.stream().filter(o -> o.getStatus().equals(status)).collect(Collectors.toList());
    }

    //Получение месяцев с максимальной суммой покупок
    public static List<String> calculateMostSpendingMonths(List<Order> orders) throws ParseException {
        double maxTotal = orders.stream().mapToDouble(Order::getTotal).max().orElse(0.0);
        List<Order> ordersWithMaxTotal = orders.stream().filter(o -> o.getTotal() == maxTotal).collect(Collectors.toList());

        List<String> monthsWithMaxTotal = new ArrayList<>();
        for (Order order : ordersWithMaxTotal) {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date date = formatter.parse(String.valueOf(order.getOrdered_at()));
            LocalDate orderDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String month = orderDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase();
            monthsWithMaxTotal.add("«" + month + "»");
        }

        return allMonths.stream()
                .filter(monthsWithMaxTotal::contains)
                .collect(Collectors.toList());
    }

    public static String returnJson(List<String> uniqueMonths) {
        StringBuilder jsonResult = new StringBuilder();
        jsonResult.append("{");
        jsonResult.append("«months»: [");
        for (int i = 0; i < uniqueMonths.size(); i++) {
            jsonResult.append(uniqueMonths.get(i));
            if (i < uniqueMonths.size() - 1) {
                jsonResult.append(", ");
            }
        }
        jsonResult.append("]}");
        return jsonResult.toString();
    }

    public static void startApplication(String file) {
        try {
            List<Order> orders = parseJson(file);
            System.out.println(returnJson(calculateMostSpendingMonths(orders)));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

}
