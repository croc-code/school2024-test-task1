package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Order;
import org.example.model.Report;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class for processing JSON data related to orders and reports.
 */
public final class JsonProcessor {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String MONTH_START = "«";
    private static final String MONTH_END = "»";
    private static final String JSON_START = "{";
    private static final String JSON_END = "}";
    private static final String MONTHS = "months";
    private static final String ARRAY_START = ":[";
    private static final String ARRAY_END = "]";

    private JsonProcessor() {
    }

    /**
     * Reads JSON data from the file and converts it into a list of {@link Order} objects.
     *
     * @param path the path to the JSON file containing order data
     * @return a list of Order objects parsed from the JSON data
     * @throws IOException if an I/O error occurs while reading the file or parsing the JSON data
     */
    public static List<Order> getOrdersFromJson(String path) throws IOException {
        return MAPPER.readValue(new File(path), new TypeReference<>() {
        });
    }

    /**
     * Converts a {@link Report} object into a JSON string representation.
     *
     * @param report the Report object to be converted
     * @return a JSON string representing the given Report object
     */
    public static String reportToJson(Report report) {
        return generateHeader() +
                ARRAY_START +
                generateMonthsArray(report.months()) +
                ARRAY_END +
                JSON_END;
    }

    private static String generateMonthsArray(List<String> months) {
        return months.stream()
                .map(month -> MONTH_START + month + MONTH_END)
                .collect(Collectors.joining(","));
    }

    private static StringBuilder generateHeader() {
        StringBuilder header = new StringBuilder();
        return header.append(JSON_START)
                .append(MONTH_START)
                .append(MONTHS)
                .append(MONTH_END);
    }
}