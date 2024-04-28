package ru.croc.javaschool2024.marketplace.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Модель данных для заказа
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @JsonProperty("user_id")
    String userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    @JsonProperty("ordered_at")
    LocalDateTime orderAt;

    @JsonProperty("status")
    OrderStatus status;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty("total")
    BigDecimal price;

}
