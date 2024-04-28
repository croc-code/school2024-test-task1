package com.wladischlau.app.service;

import com.wladischlau.app.dto.order.OrderDTO;
import com.wladischlau.app.dto.order.OrderStatus;
import com.wladischlau.app.exception.NoOrdersFoundException;
import com.wladischlau.app.util.FileUtils;
import com.wladischlau.app.util.JsonUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
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
     * Вычисляет общую сумму трат по месяцам.
     * <p>
     * В реализации не используется {@link Collection#parallelStream()}, так как
     * в данном случае параллельные стримы эффективны начиная только с 1_000_000
     * заказов в списке.
     * </p>
     *
     * @param orders список заказов.
     * @return {@link Map}, где ключи - названия месяцев в нижнем регистре,
     * значения - общая сумма трат за каждый месяц.
     */
    private static Map<String, BigDecimal> getMonthlyTotals(
            List<OrderDTO> orders
    ) {
        return orders
                .stream()
                .filter(order -> order.status() == OrderStatus.COMPLETED)
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
     * Отвечает за получения месяцев с максимальной общей суммой трат.
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
                .sorted(Comparator.comparing(m -> Month.valueOf(m.toUpperCase())))
                .toList();
    }
}
