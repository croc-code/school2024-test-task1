package ru.berdnikov.school2024.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.berdnikov.school2024.dto.OrderDTO;
import ru.berdnikov.school2024.utils.OrderStatus;
import ru.berdnikov.school2024.service.OrderService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Month;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final ObjectMapper objectMapper;

    @Value("classpath:input.json")
    private Resource resourceFile;

    @Autowired
    public OrderServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getMaxTotalMonthReport() {
        try {
            Map<Integer, Map<Integer, BigDecimal>> yearsWithMonth = getYearsWithMonth();
            Map<Integer, Map<Integer, BigDecimal>> lastYearWithMonth = getLastYearWithPreviousMonths(yearsWithMonth);
            List<String> maxTotalMonths = getMaxTotalMonths(lastYearWithMonth);
            Map<String, List<String>> report = generateReport(maxTotalMonths);
            return objectMapper.writeValueAsString(report);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Генерируем Map с ключем в виде года, значения в виде Map, где ключ - месяц, а значения - сумма трат
    //Пример: {2016={3=215.50}, 2023={1=1917.00, 3=13990.00, 12=64259.00}}
    public Map<Integer, Map<Integer, BigDecimal>> getYearsWithMonth() {
        try {
            OrderDTO[] orderDTOs = objectMapper.readValue(resourceFile.getInputStream(), OrderDTO[].class);
            Map<Integer, Map<Integer, BigDecimal>> monthByYearMap = new HashMap<>();

            for (OrderDTO orderDTO : orderDTOs) {
                if (orderDTO.getStatus().equals(OrderStatus.COMPLETED.toString())) {
                    int year = orderDTO.getOrdered_at().getYear();
                    int month = orderDTO.getOrdered_at().getMonth();
                    BigDecimal total = orderDTO.getTotal();

                    Map<Integer, BigDecimal> innerMap = monthByYearMap.computeIfAbsent(year, k -> new HashMap<>());

                    innerMap.put(month, innerMap.getOrDefault(month, BigDecimal.ZERO).add(total));
                }
            }
            return monthByYearMap;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    //Получаем последний возможный месяц из файла и берем предыдущие 11
    public Map<Integer, Map<Integer, BigDecimal>> getLastYearWithPreviousMonths(Map<Integer, Map<Integer, BigDecimal>> yearWithMonthMap) {
        Map<Integer, Map<Integer, BigDecimal>> lastYearWithPreviousMonths = new HashMap<>();

        // Если вывод пустой, возвращаем пустую карту
        if (yearWithMonthMap.isEmpty()) {
            return lastYearWithPreviousMonths;
        }

        // Находим максимальный год
        int maxYear = Collections.max(yearWithMonthMap.keySet());

        // Получаем крайний месяц в максимальном году
        Map<Integer, BigDecimal> lastYearMap = yearWithMonthMap.get(maxYear);
        int maxMonth = Collections.max(lastYearMap.keySet());

        // Отсчитываем 12 месяцев назад от крайнего месяца в максимальном году
        int startYear;
        int startMonth;
        if (maxMonth > 12) {
            startYear = maxYear;
            startMonth = maxMonth - 11; // Начинаем с предыдущего года
        } else {
            startYear = maxYear - 1; // Начинаем с предыдущего года
            startMonth = maxMonth + 1; // Начинаем с последнего месяца предыдущего года
        }

        // Добавляем месяцы для последнего года и предыдущего 11 месяцев
        for (int year = startYear; year <= maxYear; year++) {
            Map<Integer, BigDecimal> monthMap = yearWithMonthMap.getOrDefault(year, new HashMap<>());
            Map<Integer, BigDecimal> newMonthMap = new HashMap<>();

            for (int month = startMonth; month <= 12; month++) {
                BigDecimal total = monthMap.getOrDefault(month, BigDecimal.ZERO);
                newMonthMap.put(month, total);
            }

            lastYearWithPreviousMonths.put(year, newMonthMap);
            startMonth = 1; // Начинаем с января для последующих лет
        }

        return lastYearWithPreviousMonths;
    }

    //Получаем месяцы с максимальной прибылью
    public List<String> getMaxTotalMonths(Map<Integer, Map<Integer, BigDecimal>> yearWithMonthMap) {
        List<String> maxTotalMonths = new ArrayList<>();

        // Если вывод пустой, возвращаем пустой список
        if (yearWithMonthMap.isEmpty()) {
            return maxTotalMonths;
        }

        // Находим максимальную сумму заказов
        BigDecimal maxTotal = BigDecimal.ZERO;
        for (Map<Integer, BigDecimal> monthMap : yearWithMonthMap.values()) {
            for (BigDecimal total : monthMap.values()) {
                maxTotal = maxTotal.max(total);
            }
        }

        // Собираем месяцы с максимальной суммой заказов
        for (Map.Entry<Integer, Map<Integer, BigDecimal>> entry : yearWithMonthMap.entrySet()) {
            for (Map.Entry<Integer, BigDecimal> innerEntry : entry.getValue().entrySet()) {
                if (innerEntry.getValue().compareTo(maxTotal) == 0) {
                    String month = Month.of(innerEntry.getKey()).toString().toLowerCase();
                    maxTotalMonths.add(month);
                }
            }
        }

        return maxTotalMonths;
    }

    //генерирум отчет
    private Map<String, List<String>> generateReport(List<String> maxTotalMonths) {
        Map<String, List<String>> report = new HashMap<>();
        report.put("months", maxTotalMonths);
        return report;
    }
}
