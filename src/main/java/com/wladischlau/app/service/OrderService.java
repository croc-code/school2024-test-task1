package com.wladischlau.app.service;

import com.wladischlau.app.dto.order.OrderDTO;
import com.wladischlau.app.dto.order.OrderStatus;
import com.wladischlau.app.exception.NoOrdersFoundException;
import com.wladischlau.app.util.FileUtils;
import com.wladischlau.app.util.JsonUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс, предоставляющий сервисные методы
 * для работы с {@link OrderDTO заказами}.
 */
public class OrderService {

    /**
     * Обрабатывает список заказов, вычисляя месяцы с наибольшими тратами.
     *
     * @param orders список заказов.
     * @return список месяцев с наибольшими тратами.
     * @throws NoOrdersFoundException если список заказов пуст.
     */
    public static List<String> processOrderList(
            List<OrderDTO> orders
    ) throws NoOrdersFoundException {
        var totalByMonthMap = OrderService.getMonthlyTotals(orders);

        var maxTotalByMonth = totalByMonthMap
                .values()
                .stream()
                .max(BigDecimal::compareTo);

        return OrderService.getMonthsWithMaxTotals(
                totalByMonthMap,
                maxTotalByMonth.orElseThrow(
                        () -> new NoOrdersFoundException(
                                "Can't calculate the max total by month."
                        )
                )
        );
    }

    /**
     * Считывает список {@link OrderDTO заказов} из JSON файла.
     *
     * @param path путь к файлу.
     * @return список {@link OrderDTO заказов}, считанных из файла.
     * @throws IOException если возникает ошибка при чтении файла.
     */
    public static List<OrderDTO> readOrdersFromJson(
            String path
    ) throws IOException {
        return JsonUtils.parseJsonArray(
                FileUtils.readJsonFile(path),
                OrderDTO.class
        );
    }

    /**
     * Проверяет, оплачен ли {@link OrderDTO заказ}.
     *
     * @param order {@link OrderDTO заказ} для проверки.
     * @return {@code true}, если заказ оплачен, {@code false} в противном случае.
     */
    private static boolean isOrderPaid(OrderDTO order) {
        return order.status() == OrderStatus.DELIVERY ||
               order.status() == OrderStatus.COMPLETED;
    }

    private static Map<String, BigDecimal> getMonthlyTotals(
            List<OrderDTO> orders
    ) {
        return orders
                .stream() // Parallel is effective only from 1_000_000 elements
                .filter(OrderService::isOrderPaid)
                .collect(
                        Collectors.groupingBy(
                                order -> order
                                        .orderedAt()
                                        .getMonth()
                                        .toString()
                                        .toLowerCase(),
                                Collectors.reducing(
                                        BigDecimal.ZERO,
                                        OrderDTO::total,
                                        BigDecimal::add
                                )
                        )
                );
    }

    /**
     * Получает месяцы с максимальной общей суммой трат.
     *
     * @param monthlyTotals отображение с общими суммами трат по месяцам.
     * @param maxTotal      максимальная общая сумма трат.
     * @return список месяцев с максимальной общей суммой трат.
     */
    private static List<String> getMonthsWithMaxTotals(
            Map<String, BigDecimal> monthlyTotals,
            BigDecimal maxTotal
    ) {
        return monthlyTotals
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().compareTo(maxTotal) == 0)
                .map(Map.Entry::getKey)
                .toList();
    }
}
