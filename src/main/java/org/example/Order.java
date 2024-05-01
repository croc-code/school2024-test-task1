package org.example;

import java.time.LocalDateTime;

public class Order {
    private String user_id;
    private String ordered_at;
    private String status;
    private double total;

    public Order() {
    }

    // Геттеры и сеттеры
    public String getUser_id() {
        return user_id;
    }


    public LocalDateTime getOrdered_at() {
        return LocalDateTime.parse(ordered_at);

    }

    public String getStatus() {
        return status;
    }


    public double getTotal() {
        return total;
    }

}