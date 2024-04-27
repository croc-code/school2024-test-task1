package org.example;

import org.example.model.Order;
import org.example.model.Report;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderAnalyserTest {
    @Test
    @DisplayName("test generate order with one month")
    void test_generateReport1() throws IOException {
        OrderAnalyser orderAnalyser = new OrderAnalyser();
        List<Order> orders = JsonProcessor.getOrdersFromJson("src/test/java/resources/test_case1.json");
        Report expected = new Report(
                List.of("march")
        );
        assertThat(orderAnalyser.generateReport(orders)).isEqualTo(expected);
    }

    @Test
    @DisplayName("test generate order with few month")
    void test_generateReport2() throws IOException {
        OrderAnalyser orderAnalyser = new OrderAnalyser();
        List<Order> orders = JsonProcessor.getOrdersFromJson("src/test/java/resources/test_case2.json");
        Report expected = new Report(
                List.of("january", "july", "june")
        );
        assertThat(orderAnalyser.generateReport(orders)).isEqualTo(expected);
    }
}
