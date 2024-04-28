package com.volodin.orderclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Класс, представляющий заказ
 */
public class Order {
    @JsonProperty("user_id")
    private final String userId;
    @JsonProperty("ordered_at")
    private final LocalDateTime orderedAt;
    @JsonProperty("total")
    private BigDecimal total;
    @JsonProperty("status")
    private OrderStatus orderStatus;

    /**
     * Конструктор для создания объекта Order с указанием id пользователя и суммы заказа.
     *
     * @param userId идентификатор пользователя
     * @param total  общая сумма заказа
     */
    public Order(String userId, BigDecimal total) {
        this.orderedAt = LocalDateTime.now();
        this.orderStatus = OrderStatus.CREATED;
        this.userId = userId;
        this.total = total;
    }

    /**
     * Пустой конструктор для сериализации/десериализации объекта Order из/в JSON.
     */
    public Order() {
        this.userId = null;
        this.orderedAt = null;
    }

    /**
     * @return идентификатор пользователя
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @return время оформления заказа
     */
    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    /**
     * @return общую сумму заказа
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @return текущий статус заказа
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId='" + userId + '\'' +
                ", orderedAt=" + orderedAt +
                ", total=" + total +
                ", orderStatus=" + orderStatus +
                '}';
    }


}
