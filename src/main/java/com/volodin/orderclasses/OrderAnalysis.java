package com.volodin.orderclasses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для анализа заказов и создания отчета.
 */
public class OrderAnalysis {
    private OrderAnalysis(){} // Приватный конструктор для предотвращения создания экземпляра класса

    /**
     * Вычисляет общую сумму заказов для каждого месяца.
     * @param orders список заказов для анализа
     * @return Map, где ключ - месяц, а значение - общая сумма заказов за месяц
     */
    private static Map<Month, BigDecimal> calculateTotalForMonths(List<Order> orders) {
        Map<Month, BigDecimal> totalByMonthMap = new HashMap<>();
        orders.forEach(order -> {
            if (order.getOrderStatus().equals(OrderStatus.COMPLETED)) { // учитываем только завершенные заказы
                Month month = order.getOrderedAt().getMonth();
                //Если месяц ещё не встречался, то его значение 0 +сумма заказа; в противном случае - имеющееся + сумма заказа
                totalByMonthMap.put(month, (totalByMonthMap.getOrDefault(month, BigDecimal.valueOf(0)).add(order.getTotal())));
            }
        });
        return totalByMonthMap;
    }

    /**
     * Находит месяцы с наибольшей общей суммой заказов
     * @param totalByMonthMap Map,где ключ - месяц, значение - общая сумма
     * @return список месяцев с максимальной суммой заказов
     */
    private static List<Month> findMaxTotalMonths(Map<Month, BigDecimal> totalByMonthMap) {
        List<Month> monthsWithMaxTotal = new ArrayList<>();
        Optional<Map.Entry<Month, BigDecimal>> totalMaxOfMonth = totalByMonthMap.entrySet().stream()
                .max(Map.Entry.comparingByValue());
        if (totalMaxOfMonth.isPresent()) {
            BigDecimal maxEntry = totalMaxOfMonth.get().getValue();
            totalByMonthMap.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(maxEntry))//фильтруем по макс.полученному значению
                    .forEach(x -> monthsWithMaxTotal.add(x.getKey()));
        }
        return monthsWithMaxTotal;
    }

    /**
     * Создает отчет в формате JSON, содержащий сортированные по порядку
     * месяцы с максимальной общей суммой заказов.
     * @param orders список заказов для анализа
     * @return отчет в формате JSON
     * @throws JsonProcessingException если возникает ошибка при преобразовании объекта в JSON
     */
    public static String createReport(List<Order> orders) throws JsonProcessingException {
        List<Month> maxTotalMonths = findMaxTotalMonths(calculateTotalForMonths(orders));
        List<String> sortedStringMonths = maxTotalMonths.stream()
                .sorted(Comparator.comparingInt(Month::getValue))
                .map(month -> month.toString().toLowerCase())
                .collect(Collectors.toList());
        String strJsonFormat = new ObjectMapper().writeValueAsString(sortedStringMonths);
        return String.format("{\"months\": %s}",strJsonFormat);
    }

}
