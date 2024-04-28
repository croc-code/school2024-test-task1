package org.crock.contest.order;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @param userId    - id заказчика
 * @param orderedAt - дата заказа
 * @param status    - статус заказа, его состояния хранятся в {@link Status}
 * @param total     -  сумма заказа
 */
public record Order(@SerializedName("user_id") String userId,
                    @SerializedName("ordered_at") LocalDateTime orderedAt,
                    @SerializedName("status") Status status,
                    @SerializedName("total") BigDecimal total) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(userId, order.userId) && Objects.equals(orderedAt, order.orderedAt) && status == order.status && Objects.equals(total, order.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, orderedAt, status, total);
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId='" + userId + '\'' +
                ", orderedAt=" + orderedAt +
                ", status=" + status +
                ", total=" + total +
                '}';
    }
}
