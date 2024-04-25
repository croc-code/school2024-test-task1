package com.fridrock;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderAnalytics {
  private static final String NEEDED_STATUS = "COMPLETED";
  private static final String[] monthsNames = new String[]{"«january»", "«february»", "«march»", "«april»",
      "«may»", "«june»", "«july»", "«august»", "«september»", "«october»", "«november»", "«december»"};

  public static void perform(final String fileName) {
    try {
      System.out.println(getResultJSON(getMonthTotalSorted(getOrdersFromFile(fileName))));
    } catch (IOException e) {
      System.out.println("Error reading JSON file:" + e.getMessage());
    }
  }

  private static String getResultJSON(final List<Map.Entry<Integer, Double>> monthTotalSorted) {
    //Getting max sum of orders
    double maxTotal = monthTotalSorted.get(monthTotalSorted.size() - 1).getValue();
    List<String> maxMonthsNames = extractMonthNames(monthTotalSorted, maxTotal);
    String maxMonthsJSON = String.join(", ", maxMonthsNames);
    return String.format("{«months»: [%s]}", maxMonthsJSON);
  }

  private static List<String> extractMonthNames(
      final List<Map.Entry<Integer, Double>> monthTotalSorted, final double maxTotal) {
    /*transform <monthNumber, totalSum> key pair values to list of strings,
    that match months that have max total sum*/
    return monthTotalSorted.stream()
        .filter(entry -> entry.getValue() == maxTotal)
        .sorted(Map.Entry.comparingByKey())
        .map(entry -> monthsNames[entry.getKey() - 1])
        .collect(Collectors.toList());
  }

  private static List<Map.Entry<Integer, Double>> getMonthTotalSorted(final List<Order> orders) {
    List<Map.Entry<Integer, Double>> list = new LinkedList<>(fillMonthTotalMap(orders).entrySet());
    list.sort(Map.Entry.comparingByValue());
    return list;
  }

  private static Map<Integer, Double> fillMonthTotalMap(final List<Order> orders) {
    Map<Integer, Double> monthTotal = new HashMap<>();
    orders.forEach((o) -> {
      Double newTotal = monthTotal.getOrDefault(o.getMonth(), 0.0) + o.getTotal();
      monthTotal.put(o.getMonth(), newTotal);
    });
    return monthTotal;
  }

  private static List<Order> getOrdersFromFile(final String fileName) throws IOException {
    List<Order> orders = new ObjectMapper().readValue(new File(fileName), new TypeReference<>() {
    });
    return orders.stream().filter(order -> order.getStatus().equals(NEEDED_STATUS)).collect(Collectors.toList());
  }
}
