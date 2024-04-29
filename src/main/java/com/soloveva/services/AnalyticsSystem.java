package com.soloveva.services;

import com.soloveva.models.Order;
import com.soloveva.models.OrderStatus;
import com.soloveva.readers.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.Month;
import java.util.*;

public class AnalyticsSystem {
    public String getMostProfitMonths(String filename) throws IOException, ParseException {
        List<Order> orders = JsonReader.readJsonAndSaveData(filename);
        List<String> months = calculateMostProfitMonths(orders);

        return generateJson(months);
    }

    private List<String> calculateMostProfitMonths(List<Order> orders){
        List<Order> completedOrders = orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.COMPLETED)).toList();

        Map<Month, Double> profitsByMonths = new HashMap<>();

        for(Order order : completedOrders){
            Month month = order.getOrderTime().getMonth();
            double totalPrice = order.getTotalPrice();

            profitsByMonths.compute(month, (k, v) -> (v != null ? v : 0.0) + totalPrice);
        }

        Double maxProfit = profitsByMonths.values().stream().max(Double::compare).orElse(null);

        if(maxProfit == null){
            return null;
        } else {
            return profitsByMonths.entrySet().stream()
                    .filter(entry -> Objects.equals(entry.getValue(), maxProfit))
                    .map(Map.Entry::getKey).sorted(Comparator.comparingInt(Month::getValue))
                    .map(month -> month.name().toLowerCase()).toList();
        }
    }

    private String generateJson(List<String> months){
        JSONObject jsonObject = new JSONObject();
        JSONArray monthArray = new JSONArray();
        monthArray.addAll(months);
        jsonObject.put("months", monthArray);
        return jsonObject.toJSONString();
    }
}
