package ru.croc.javaschool2024.pojo;

import org.junit.jupiter.api.Test;
import ru.croc.javaschool2024.marketplace.models.Order;
import ru.croc.javaschool2024.marketplace.models.OrderStatus;
import ru.croc.javaschool2024.marketplace.utils.JsonParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PojoTest {
    private final String FULL_TEST_PATH = "src/test/java/ru/croc/javaschool2024/resourses/format.json";

    @Test
    void parseEqualsTest() throws IOException {
        List<Order> orders = JsonParser.readJsonFile(FULL_TEST_PATH);

        assertNotNull(orders);
        assertFalse(orders.isEmpty());

        Order expectedOrder = new Order(
                "3acfb0b7-04bd-4978-be4c-3929372277c1",
                LocalDateTime.parse("2023-01-16T13:56:39.492", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                OrderStatus.COMPLETED,
                new BigDecimal("1917.00")
        );

        Order actualOrder = orders.getFirst();

        assertEquals(expectedOrder.getUserId(), actualOrder.getUserId());
        assertEquals(expectedOrder.getOrderAt(), actualOrder.getOrderAt());
        assertEquals(expectedOrder.getStatus(), actualOrder.getStatus());
        assertEquals(0, expectedOrder.getPrice().compareTo(actualOrder.getPrice()));  // Сравнение BigDecimal
    }

    @Test
    void parseDateNotEqualsTest() throws IOException {
        List<Order> orders = JsonParser.readJsonFile(FULL_TEST_PATH);

        assertNotNull(orders);
        assertFalse(orders.isEmpty());

        Order expectedOrder = new Order(
                "3acfb0b7-04bd-4978-be4c-3929372277c1",
                LocalDateTime.parse("2023-02-16T13:56:39.492", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                OrderStatus.COMPLETED,
                new BigDecimal("1917.00")
        );

        Order actualOrder = orders.getFirst();

        assertEquals(expectedOrder.getUserId(), actualOrder.getUserId());
        assertNotEquals(expectedOrder.getOrderAt(), actualOrder.getOrderAt());
        assertEquals(expectedOrder.getStatus(), actualOrder.getStatus());
        assertEquals(0, expectedOrder.getPrice().compareTo(actualOrder.getPrice()));  // Сравнение BigDecimal
    }

    @Test
    void parseIdNotEqualsTest() throws IOException {
        List<Order> orders = JsonParser.readJsonFile(FULL_TEST_PATH);

        assertNotNull(orders);
        assertFalse(orders.isEmpty());

        Order expectedOrder = new Order(
                "3acfb0b7-04bd-4978-as4c-3929372277c1",
                LocalDateTime.parse("2023-02-16T13:56:39.492", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                OrderStatus.COMPLETED,
                new BigDecimal("1917.00")
        );

        Order actualOrder = orders.getFirst();

        assertNotEquals(expectedOrder.getUserId(), actualOrder.getUserId());
        assertNotEquals(expectedOrder.getOrderAt(), actualOrder.getOrderAt());
        assertEquals(expectedOrder.getStatus(), actualOrder.getStatus());
        assertEquals(0, expectedOrder.getPrice().compareTo(actualOrder.getPrice()));  // Сравнение BigDecimal
    }

    @Test
    void parseAllNotEqualsTest() throws IOException {
        List<Order> orders = JsonParser.readJsonFile(FULL_TEST_PATH);

        assertNotNull(orders);
        assertFalse(orders.isEmpty());

        Order expectedOrder = new Order(
                "3acfb0b7-04bd-4978-as4c-3929372277c1",
                LocalDateTime.parse("2023-02-16T13:56:39.492", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                OrderStatus.CANCELED,
                new BigDecimal("1917.01")
        );

        Order actualOrder = orders.getFirst();

        assertNotEquals(expectedOrder.getUserId(), actualOrder.getUserId());
        assertNotEquals(expectedOrder.getOrderAt(), actualOrder.getOrderAt());
        assertNotEquals(expectedOrder.getStatus(), actualOrder.getStatus());
        assertNotEquals(0, expectedOrder.getPrice().compareTo(actualOrder.getPrice()));  // Сравнение BigDecimal
    }


}
