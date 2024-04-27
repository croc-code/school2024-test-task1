package ru.sarmosov.task.service;

import ru.sarmosov.task.entity.Purchase;
import ru.sarmosov.task.enums.Status;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class CountService {

    public static List<Month> service(List<Purchase> purchases) {

        Map<Month, Double> monthTotaleMap = new HashMap<>();

        for (Purchase purchase : purchases) {
            if (!purchase.getStatus().equals(Status.COMPLETED)) continue;
            Month month = purchase.getMonth();
            monthTotaleMap.put(month, monthTotaleMap.getOrDefault(month, 0d) + purchase.getTotal());
        }

        Double maxTotal = monthTotaleMap.values().stream()
                .max(Comparator.naturalOrder())
                .orElseThrow(RuntimeException::new);

        return monthTotaleMap.entrySet().stream()
                .filter(it -> it.getValue().equals(maxTotal))
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
    }
}
