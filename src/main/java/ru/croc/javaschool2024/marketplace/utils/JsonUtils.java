package ru.croc.javaschool2024.marketplace.utils;

import ru.croc.javaschool2024.marketplace.models.MonthReport;
import ru.croc.javaschool2024.marketplace.models.Order;
import ru.croc.javaschool2024.marketplace.models.OrderStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonUtils {

    /**
     * Метод отвечает за логику поиска месяца с максимальной суммой покупок.
     *
     * @param orders - Считанный Json в виде List
     * @return месяцы, в которых сумма покупок в месяц соответствует месяцу с максимальной суммой покупок
     */
    public static String findMonthWithMaxWastes(List<Order> orders) throws IOException {
        Map<Month, BigDecimal> totalAmountByMonth = getTotalAmountByMonth(orders);

        BigDecimal maxTotal = getMaxMonthTotal(totalAmountByMonth);

        List<String> maxMonths = totalAmountByMonth.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(maxTotal) == 0)
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase())
                .collect(Collectors.toList());

        return MonthReport.toJson(maxMonths);
    }


    /**
     * Метод отвечает за поиск месяцев с максимальным числом покупок
     *
     * @param orders - Считанный Json в виде List
     * @return Map с параметрами <Месяц, Сумма числа покупок>
     */
    private static Map<Month, BigDecimal> getTotalAmountByMonth(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                .collect(Collectors.groupingBy(
                        order -> order.getOrderAt().getMonth(),
                        Collectors.reducing(BigDecimal.ZERO, Order::getPrice, BigDecimal::add)
                ));
    }

    /**
     * @param totalAmountByMonth - Map с параметрами <Месяц, Сумма числа покупок>
     * @return число наибольших трат в месяце
     */
    private static BigDecimal getMaxMonthTotal(Map<Month, BigDecimal> totalAmountByMonth) {
        return totalAmountByMonth.values().stream()
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }
}