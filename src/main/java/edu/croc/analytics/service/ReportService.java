package edu.croc.analytics.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.croc.analytics.dto.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {
    private final ObjectMapper mapper;

    private record Report(List<String> months) {}

    public String generateReport(String filePath) throws IOException {
        Order[] orders = readOrdersJson(filePath);

        Map<Month, BigDecimal> totals = getTotalByMonth(orders);

        List<String> months = getMaxTotalMonths(totals);
        return mapper.writeValueAsString(new Report(months));
    }

    private Order[] readOrdersJson(String filePath) throws IOException {
        try(var source = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            return mapper.readValue(source, Order[].class);
        }
    }

    private Map<Month, BigDecimal> getTotalByMonth(Order[] orders) {
        Map<Month, BigDecimal> totals = new ConcurrentHashMap<>(Month.values().length);
        for (Month month : Month.values()) {
            totals.put(month, BigDecimal.ZERO);
        }

        Arrays.stream(orders).parallel()
                .filter(order -> order.status().equals(Order.Status.COMPLETED))
                .forEach(order -> totals.compute(
                        order.orderedAt().getMonth(),
                        (moth, currentMothTotal) -> currentMothTotal.add(order.total()))
                );
        return totals;
    }

    private List<String> getMaxTotalMonths(Map<Month, BigDecimal> totals) {
        BigDecimal max = Collections.max(totals.values());

        List<String> result = new ArrayList<>();
        for (Month month : Month.values()) {
            if (totals.get(month).equals(max)) {
                result.add(month.name().toLowerCase());
            }
        }
        return result;
    }
}
