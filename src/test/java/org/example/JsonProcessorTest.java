package org.example;

import org.example.model.Order;
import org.example.model.Report;
import org.example.model.Status;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JsonProcessorTest {

    @Test
    void test_getOrdersFromJson() throws ParseException, IOException {
        String path = "src/test/java/resources/test_case1.json";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC")); // Устанавливаем временную зону UTC
        List<Order> expected = List.of(
                new Order("25b003b9-ab22-4a24-a616-dd0303f983d8",
                        formatter.parse("2023-03-05T08:34:21.123"),
                        Status.COMPLETED,
                        13990.0),
                new Order("0999c6aa-1bac-4ded-9a54-92fff4f34d69",
                        formatter.parse("2023-12-14T11:10:29.408"),
                        Status.CANCELED,
                        494992.00),
                new Order("0999c6aa-1bac-4ded-9a54-92fff4f34d69",
                        formatter.parse("2023-12-14T11:15:31.108"),
                        Status.CREATED,
                        14760)
        );
        assertThat(JsonProcessor.getOrdersFromJson(path)).isEqualTo(expected);
    }

    @Test
    void test_reportToJson() {
        Report report = new Report(
                List.of("december", "july")
        );
        String expected = "{«months»:[«december»,«july»]}";
        assertThat(JsonProcessor.reportToJson(report)).isEqualTo(expected);
    }
}
