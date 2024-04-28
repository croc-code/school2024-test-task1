package org.example;

import org.json.JSONObject;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report {
    List<Order> orders;
    public Report( List<Order> orders){
        this.orders = orders;
    }

    public String generateMaxTotalMonthReport() {
        Map<String, Double> totalForMonths = new HashMap<>();
        for (Order order : orders) {
            if (order.getStatus() == OrderStatuses.COMPLETED) {
                Month month = order.getOrderedAt().getMonth();
                double totalForMonth = totalForMonths.getOrDefault(month.name().toLowerCase(), 0.0);
                totalForMonths.put(month.name().toLowerCase(), totalForMonth + order.getTotal());
            }
        }
        double maxTotal = Double.MIN_VALUE;
        for (double total : totalForMonths.values()) {
            maxTotal = Math.max(maxTotal, total);
        }

        List<String> maxTotalForMonths = new ArrayList<>();
        for (Months month: Months.values()){
            String monthString = month.toString().toLowerCase();
            if (totalForMonths.containsKey(monthString) && totalForMonths.get(monthString) == maxTotal){
                maxTotalForMonths.add(monthString);
            }
        }

        JSONObject reportJson = new JSONObject();
        reportJson.put("months", maxTotalForMonths);
        return reportJson.toString();
    }
}