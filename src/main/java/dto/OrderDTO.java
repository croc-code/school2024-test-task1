package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderDTO(
        @JsonProperty("user_id") UUID userId,
        @JsonProperty("ordered_at") LocalDateTime orderedAt,
        @JsonProperty("status") OrderStatus status,
        @JsonProperty("total") BigDecimal total
) {
}
