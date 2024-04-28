package application;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.Order;
import data.Status;
import java.io.IOException;
import java.io.StringWriter;
import java.time.Month;
import java.util.*;

public class ReportGenerator {
    public static String generate(List<Order> orders) throws IOException {
        Map<Integer, Double> earningsForEachMonth = countEarningsForEachMonth(orders); // Формируем мапу из списка всех заказов, ключом в которой
        // является порядковый номер месяца, а значением - сумма потраченная в этот месяц
        List<String> maxMonths = findMonthsWithMaxEarnings(earningsForEachMonth); // Формируем список с месяцами, в которые совершались наибольшие затраты

        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter output = new StringWriter();
        objectMapper.writeValue(output, new Report(maxMonths));
        return output.toString();
    }

    private static Map<Integer, Double> countEarningsForEachMonth(List<Order> orders) {
        Map<Integer, Double> earningsForEachMonth = new TreeMap<>(); // Создаем TreeMap, так как тогда коллекция будет по умолчанию
        // отсортированна по ключу в порядке возрастания (в нашем случае это означает, что месяца будут располагаться в порядке их следования в течение года)
        for (int i = 0; i < orders.size(); i ++) { // проходимся по списку заказов
            if (orders.get(i).getStatus() == Status.COMPLETED) {
                int month = orders.get(i).getOrderedAt().getMonthValue();
                if (earningsForEachMonth.get(month) == null) {
                    earningsForEachMonth.put(month, orders.get(i).getTotal()); // если в мапе еще нет ни одного
                    // элемента с таким ключом, то кладем его туда, в качестве значения указываем сумму заказа
                } else {
                    earningsForEachMonth.put(month, earningsForEachMonth.get(month) + orders.get(i).getTotal()); // если в мапе уже есть элемент с таким  ключом,
                    // то прибавляем к значению в мапе сумму текущего заказа
                }
            }
        }
        return earningsForEachMonth;
    }

    private static List<String> findMonthsWithMaxEarnings(Map<Integer, Double> earningsForEachMonth) {
        List<String> maxMonths = new ArrayList<>();
        if (earningsForEachMonth.isEmpty()) {return maxMonths;} // если не проверить, то при вызове Collections.max() может вылететь ошибка
        double maxValue = Collections.max(earningsForEachMonth.values());
        for (Map.Entry<Integer, Double> entry : earningsForEachMonth.entrySet()) {
            if (entry.getValue().equals(maxValue)) {
                maxMonths.add(Month.of(entry.getKey()).toString().toLowerCase());
            }
        }
        return maxMonths;
    }
}