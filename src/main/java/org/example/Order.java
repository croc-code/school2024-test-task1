package org.example;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
    @JsonProperty("user_id")
    private String userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date ordered_at;
    private String status;
    private double total;

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "Order{" +
                "userId='" + userId + '\'' +
                ", ordered_at=" + dateFormat.format(ordered_at) +
                ", status='" + status + '\'' +
                ", total=" + total +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getOrdered_at() {
        return ordered_at;
    }

    public void setOrdered_at(Date ordered_at) {
        this.ordered_at = ordered_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
