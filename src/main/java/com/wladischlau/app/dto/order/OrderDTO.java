package com.wladischlau.app.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, представляющий собой объект заказа.
 * <p/>
 * <p>
 * Каждый объект содержит следующую информацию:<br>
 * - {@link OrderDTO#userId}: идентификатор пользователя, совершившего заказ.<br>
 * - {@link OrderDTO#orderedAt}: дата и время оформления заказа.<br>
 * - {@link OrderDTO#status}: статус заказа ({@link OrderStatus}).<br>
 * - {@link OrderDTO#total}: общая сумма заказа.
 * </p>
 */
public record OrderDTO(
        @JsonProperty("user_id") UUID userId,
        @JsonProperty("ordered_at") LocalDateTime orderedAt,
        @JsonProperty("status") OrderStatus status,
        @JsonProperty("total") BigDecimal total
) {
}
