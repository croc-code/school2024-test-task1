package com.xx1ee;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
    private static final String[] monthNames = {"january", "february", "march", "april", "may", "june", "july", "august",
            "september", "october", "november", "december"};

    private ReportGenerator() {
    }

    /**
     monthTotal: ключ - номер месяца, значение - общая сумма трат в данном месяце
     maxTotal- максимальная сумма трат
     maxMonths - список месяцев, общая сумма трат которых равна maxTotal
     sortedMonths - maxMonths, отсортированные в порядке их следования в течение года,
     преобразованные в названия на английском языке в нижнем регистре
     **/
    public static List<String> generateMonthsList(List<Purchase> data) {
        Map<Integer, BigDecimal> monthTotal = getMonthTotal(data);
        double maxTotal = monthTotal.values().stream()
                .mapToDouble(BigDecimal::doubleValue)
                .max()
                .orElse(0);

        List<Integer> maxMonths = monthTotal.entrySet().stream()
                .filter(entry -> entry.getValue().doubleValue() == maxTotal)
                .map(Map.Entry::getKey)
                .toList();

        List<String> sortedMonths = maxMonths.stream()
                .sorted()
                .map(month -> monthNames[month - 1])
                .toList();
        return sortedMonths;
    }

    /**
        monthTotal: ключ - номер месяца, значение - общая сумма трат в данном месяце
        фильтруем заказы по COMPLETED статусу, подсчитываем общую сумму трат для каждого месяца
     **/
    private static Map<Integer, BigDecimal> getMonthTotal(List<Purchase> data) {
        Map<Integer, BigDecimal> monthTotal = new HashMap<>();
        data.stream()
                .filter(purchase -> purchase.getStatus() == Status.COMPLETED)
                .forEach(purchase -> {
                    LocalDateTime orderedAt = purchase.getOrdered_at();
                    Month month = orderedAt.getMonth();

                    BigDecimal total = purchase.getTotal();

                    if (monthTotal.containsKey(month.getValue())) {
                        total = total.add(monthTotal.get(month.getValue()));
                    }
                    monthTotal.put(month.getValue(), total);
                });
        return monthTotal;
    }
}
