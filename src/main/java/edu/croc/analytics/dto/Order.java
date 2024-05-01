package edu.croc.analytics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Order (
        @JsonProperty("user_id")
        String userId,

        @JsonProperty("ordered_at")
        LocalDateTime orderedAt,

        @JsonProperty("status")
        Status status,

        @JsonProperty("total")
        BigDecimal total) {

    public enum Status {
        COMPLETED,
        CANCELED,
        CREATED,
        DELIVERY
    }
}
