package org.example;

import org.json.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String userId;
    private LocalDate orderedAt;
    private OrderStatuses status;
    private double total;

    public Order(String userId, LocalDate orderedAt, OrderStatuses status, double total) {
        this.userId = userId;
        this.orderedAt = orderedAt;
        this.status = status;
        this.total = total;
    }
    public String getUserId() {
        return userId;
    }
    public OrderStatuses getStatus(){
        return status;
    }
    public LocalDate getOrderedAt() {
        return orderedAt;
    }
    public double getTotal() {
        return total;
    }

    public static List<Order> parseOrders(JSONArray jsonArray) {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String userId = jsonObject.getString("user_id");
                LocalDate orderedAt = LocalDate.parse(jsonObject.getString("ordered_at").substring(0, 10));
                OrderStatuses status = OrderStatuses.valueOf(jsonObject.getString("status"));
                double total = jsonObject.getDouble("total");
                orders.add(new Order(userId, orderedAt, status, total));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }
}