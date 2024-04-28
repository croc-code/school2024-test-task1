package org.example;

import org.json.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("format.json")));
            JSONArray jsonArray = new JSONArray(jsonString);
            List<Order> orders = Order.parseOrders(jsonArray);
            Report maxMonthReport = new Report(orders);
            System.out.println(maxMonthReport.generateMaxTotalMonthReport());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (JSONException e) {
            throw new RuntimeException();
        }
    }
}