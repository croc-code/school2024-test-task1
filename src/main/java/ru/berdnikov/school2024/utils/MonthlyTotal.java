package ru.berdnikov.school2024.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyTotal {
    private String month;
    private BigDecimal total;
}
