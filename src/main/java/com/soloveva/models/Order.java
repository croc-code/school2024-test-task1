package com.soloveva.models;

import java.time.LocalDateTime;

public class Order {
    private String userID;
    private LocalDateTime orderTime;
    private OrderStatus status;
    private double totalPrice;

    public Order(String userID, LocalDateTime orderTime, OrderStatus status, double totalPrice){
        this.userID = userID;
        this.orderTime = orderTime;
        this.status = status;
        this.totalPrice = totalPrice;
    }
    public String getUserID() {
        return userID;
    }
    public LocalDateTime getOrderTime() {
        return orderTime;
    }
    public OrderStatus getStatus(){
        return status;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
}
