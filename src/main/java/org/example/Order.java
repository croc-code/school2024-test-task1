package org.example;

import java.time.LocalDateTime;

public class Order {
    private String user_id; // Идентификатор пользователя
    private String ordered_at; // Дата заказа
    private String status; // Статус заказа
    private double total; // Сумма заказа

    public Order() {
    }

    public String getUser_id() {
        return user_id;
    }

    public LocalDateTime getOrdered_at() {
        // Парсим дату в формате строки в LocalDateTime
        return LocalDateTime.parse(ordered_at);
    }
    public String getStatus() {
        return status;
    }

    public double getTotal() {
        return total;
    }

}