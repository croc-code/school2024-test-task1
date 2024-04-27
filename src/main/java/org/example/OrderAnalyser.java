package org.example;

import org.example.model.Order;
import org.example.model.Report;
import org.example.model.Status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

/**
 * Class provides methods to analyze orders and generate reports
 */
public class OrderAnalyser {

    private static final Predicate<Order> IS_COMPLETED = order -> Status.COMPLETED == order.status();

    /**
     * Generates a report based on the list of orders provided. The report contains a list of months
     * in which orders with the maximum total were completed.
     *
     * @param orders a list of {@link Order} objects to be analyzed
     * @return a {@link Report} object containing a list of months
     */
    public Report generateReport(List<Order> orders) {
        final double maxTotal = getMaxTotal(orders);
        List<String> months = orders.parallelStream()
                .filter(IS_COMPLETED)
                .filter(order -> maxTotal == order.total())
                .map(order -> extractMonth(order.ordered_at()))
                .toList();
        return new Report(months);
    }

    private double getMaxTotal(List<Order> orders) {
        return orders.parallelStream()
                .filter(IS_COMPLETED)
                .mapToDouble(Order::total)
                .max()
                .orElse(0);
    }

    private String extractMonth(Date ordered_at) {
        LocalDate localDate = LocalDate.ofInstant(ordered_at.toInstant(), ZoneId.systemDefault());
        return localDate.getMonth()
                .toString()
                .toLowerCase(Locale.ENGLISH);
    }
}
