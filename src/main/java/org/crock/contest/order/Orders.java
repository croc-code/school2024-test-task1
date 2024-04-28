package org.crock.contest.order;

import org.crock.contest.data.YearAndMonth;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

/**
 * Утилитарный класс Order, который делает проверку в методе {@link  #isValidOrder(Order)}
 */
public class Orders {
    public static String isValidOrder(Order order) {
        StringBuilder errors = new StringBuilder();
        if (order.userId() == null || order.userId().trim().isEmpty()) {
            errors.append("userId can't be blank\n");
        }
        if (order.orderedAt() == null || order.orderedAt().isAfter(LocalDateTime.now())) {
            errors.append("Illegal state of date of order\n");
        }
        if (order.status() == null) {
            errors.append("Illegal state of status\n");
        }
        if (order.total() == null) {
            errors.append("total not found\n");
        }
        if (order.total() != null && order.total().compareTo(BigDecimal.ZERO) < 0) {
            errors.append("Incorrect total sum: ")
                    .append(order.total())
                    .append(" can't be negative\n");
        }
        return errors.toString();
    }

    /**
     * @param sumForAllMonths - Сумма total  в соответсвии с каждым месяцем и годом
     * @return - отсортированные месяца с максимальным total в соответсвии с годом
     */
    private static List<List<String>> findMonthsWithMaxTotal(Map<YearAndMonth, BigDecimal> sumForAllMonths) {
        // Map  хранит максимум total для каждого года.
        Map<Integer, BigDecimal> maxTotalForEveryYear = new HashMap<>();

        // Поиск максимальной суммы для каждого года
        for (Map.Entry<YearAndMonth, BigDecimal> entry : sumForAllMonths.entrySet()) {
            int year = entry.getKey().year();
            BigDecimal currentTotal = entry.getValue();

            maxTotalForEveryYear.put(year, maxTotalForEveryYear.getOrDefault(year, BigDecimal.ZERO)
                    .max(currentTotal));
        }

        // Построение списка месяцев с наибольшей суммой для каждого года
        Map<Integer, List<String>> maxMonthsForYear = new HashMap<>();
        for (Map.Entry<YearAndMonth, BigDecimal> entry : sumForAllMonths.entrySet()) {

            int year = entry.getKey().year();
            int month = entry.getKey().month();
            String monthName = Month.of(month).toString().toLowerCase();
            BigDecimal monthTotal = entry.getValue();

            if (monthTotal.equals(maxTotalForEveryYear.get(year))) {
                List<String> months = new ArrayList<>();
                months.add(monthName);
                maxMonthsForYear.putIfAbsent(year, months);
            }
        }

        // Сортировка месяцев в каждом списке
        maxMonthsForYear.forEach((year, months) ->
                months.sort(Comparator.comparingInt(m -> Month.valueOf(m.toUpperCase()).getValue())));

        return new ArrayList<>(maxMonthsForYear.values());
    }

    /**
     * @param orders - все заказы
     * @return -  возращает все месяца в порядке следования в году, а также в соответствии  с годом
     * (так как у нас года могут быть разные, то было принято решение находить
     * для каждого года его месяца с максмальным total)
     */
    public static List<List<String>> getMonthsWithMaxTotal(List<Order> orders) {
        Map<YearAndMonth, BigDecimal> spendingByMonth = new HashMap<>();

        // Расчет общей суммы трат за каждый месяц  в каждом году. Получим ключ значения соответсвия каждого года
        // и его месяца с суммарным total
        for (Order order : orders) {
            if (order.status().equals(Status.COMPLETED)) {
                YearAndMonth date = new YearAndMonth(
                        order.orderedAt().getYear(),
                        order.orderedAt().getMonthValue()
                );
                BigDecimal currentSpendingInMonth = spendingByMonth.getOrDefault(date, BigDecimal.ZERO);
                spendingByMonth.put(date, currentSpendingInMonth.add(order.total()));
            }
        }

        return findMonthsWithMaxTotal(spendingByMonth);
    }
}
