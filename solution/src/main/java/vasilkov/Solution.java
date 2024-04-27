package vasilkov;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Класс, представляющий решение задачи по обработке заказов.
 */
public class Solution {
    private final List<OrderModels.Order> orderArray; // Массив заказов
    private final OrderModels.Status status; // Статус заказа для фильтрации

    /**
     * Конструктор класса Solution.
     * @param orderArray Массив заказов.
     * @param status Статус заказа для фильтрации.
     */
    public Solution(List<OrderModels.Order> orderArray, OrderModels.Status status) {
        this.orderArray = orderArray;
        this.status = status;
    }

    /**
     * Метод для решения задачи по обработке заказов.
     * @return Результат обработки заказов.
     * @throws NoSuchElementException Исключение, если не найден максимальный месячный оборот.
     * @throws NullPointerException Исключение, если найдены пустые значения в данных о заказах.
     */
    public OrderModels.Result solve() throws NoSuchElementException, NullPointerException{

        Map<String, Double> monthlyTotal = filterOrdersAndSumByMonthTotal();

        Double monthlyMaxTotal = getMonthlyMaxValue(monthlyTotal);

        List<String> monthsWithMaxTotal = findMonthsEqualToMaxTotal(monthlyTotal, monthlyMaxTotal);

        return new OrderModels.Result(monthsWithMaxTotal);
    }

    /**
     * Получение максимального значения оборота за месяц.
     * @param monthlyTotal Месячные обороты.
     * @return Максимальное значение оборота за месяц.
     * @throws NoSuchElementException Исключение, если не найден максимальный месячный оборот.
     */
    private Double getMonthlyMaxValue(Map<String, Double> monthlyTotal) throws NoSuchElementException {
        return Collections.max(monthlyTotal.values());
    }

    /**
     * Поиск месяцев с максимальным оборотом.
     * @param monthlyTotal Месячный оборот.
     * @param maxValue Максимальное значение оборота за месяц.
     * @return Список месяцев с максимальным оборотом.
     */
    private List<String> findMonthsEqualToMaxTotal(Map<String, Double> monthlyTotal, Double maxValue) {
        return monthlyTotal.entrySet().stream()
                .filter(entry -> entry.getValue().equals(maxValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Фильтрация заказов по статусу и суммирование общей суммы по месяцам.
     * @return Месячный оборот.
     */
    private Map<String, Double> filterOrdersAndSumByMonthTotal() {
        return orderArray.stream()
                .filter(it->it.status().equals(status))
                .collect(Collectors.groupingBy(
                                order -> order.ordered_at().getMonth().toString().toLowerCase(),
                                Collectors.summingDouble(OrderModels.Order::total)
                        )
                );
    }
}
