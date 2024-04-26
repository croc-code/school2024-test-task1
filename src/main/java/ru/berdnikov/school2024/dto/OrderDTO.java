package ru.berdnikov.school2024.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.berdnikov.school2024.utils.DateTime;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String user_id;

    @JsonDeserialize(using = DateTime.class)
    private DateTime ordered_at;

    private String status;
    private BigDecimal total;
}
