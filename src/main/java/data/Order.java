package data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import utils.LocalDateTimeDeserializer;
import java.time.LocalDateTime;

@Data
public class Order {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("ordered_at")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime orderedAt;
    private Status status;
    private double total;
}
