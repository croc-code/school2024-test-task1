package org.example;

import org.example.model.Order;
import org.example.model.Report;

import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        List<Order> orders = JsonProcessor.getOrdersFromJson("src/main/java/resources/input.json");
        Report report = new OrderAnalyser().generateReport(orders);
        System.out.println(JsonProcessor.reportToJson(report));
    }
}
