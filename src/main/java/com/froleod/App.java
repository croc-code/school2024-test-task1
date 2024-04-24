package com.froleod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Главный класс приложения для генерации отчета о месяцах с наибольшими тратами пользователей.
 */
public class App {

    // Путь к файлу с данными о заказах
    private static final String FILEPATH = "src/main/java/com/froleod/resources/format.json";
    // Статус завершенного заказа
    private static final String COMPLETED = "COMPLETED";


    /**
     * Метод для чтения данных о заказах из JSON файла и преобразования их в список объектов Order.
     * @return список объектов Order, представляющих данные о заказах
     * @throws Exception если произошла ошибка при чтении файла
     */
    public static List<Order> getOrdersFromFile() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(new File(FILEPATH), new TypeReference<>(){});
    }

    /**
     * Метод для генерации отчета о месяцах с наибольшими тратами пользователей.
     *
     * @param orders список объектов Order, представляющих данные о заказах
     * @return строка в формате JSON, содержащая список месяцев с наибольшими тратами
     */
    public static String generateReport(List<Order> orders) {
        // Группировка заказов по месяцам и подсчет суммы трат для каждого месяца
        Map<Month, Double> monthlySpending = orders.stream()
                .filter(order -> order.getStatus().equals(COMPLETED))
                .collect(Collectors.groupingBy(
                        order -> order.getOrdered_at().getMonth(),
                        Collectors.summingDouble(Order::getTotal)
                ));

        // Нахождение максимальной суммы трат
        double maxSpending = monthlySpending.values().stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0.0);

        // Формирования списка месяцев с максимальной суммой трат
        List<String> maxSpendingMonths = monthlySpending.entrySet().stream()
                .filter(entry -> entry.getValue() == maxSpending)
                .map(entry -> "\"" + entry.getKey().name().toLowerCase() + "\"")
                .toList();

        return "{\"months\": " + maxSpendingMonths + "}";
    }

    /**
     * Главный метод, который запускает процесс генерации отчета.
     */
    public static void main(String[] args) throws Exception {
        try {
            List<Order> orders = getOrdersFromFile();
            String report = generateReport(orders);
            System.out.println(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
