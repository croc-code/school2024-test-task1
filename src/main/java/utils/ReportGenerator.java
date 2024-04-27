package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.Order;
import data.Status;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public class ReportGenerator {
    public static String generate(List<Order> orders) {
        List<String> maxMonths = new ArrayList<>();
        Map<String, Double> earningsForEachMonth = new HashMap<>();
        for (int i = 0; i < orders.size(); i ++) {
            if (orders.get(i).getStatus() == Status.COMPLETED) {
                String month = orders.get(i).getOrderedAt().getMonth().name();
                if (earningsForEachMonth.get(month) == null) {
                    earningsForEachMonth.put(month, orders.get(i).getTotal());
                } else {
                    earningsForEachMonth.put(month, earningsForEachMonth.get(month) + orders.get(i).getTotal());
                }
            }
        }

        double maxValue = Collections.max(earningsForEachMonth.values());
        for (Map.Entry<String, Double> entry : earningsForEachMonth.entrySet()) {
            if (entry.getValue().equals(maxValue)) {
                maxMonths.add(entry.getKey());
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter output = new StringWriter();
        try {
            objectMapper.writeValue(output, new Report(maxMonths));
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}