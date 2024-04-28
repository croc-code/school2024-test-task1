package ru.croc.javaschool2024.marketplace;

import ru.croc.javaschool2024.marketplace.models.Order;
import ru.croc.javaschool2024.marketplace.utils.JsonParser;
import ru.croc.javaschool2024.marketplace.utils.JsonReport;
import ru.croc.javaschool2024.marketplace.utils.JsonUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Application {
    private static final Path reportPath = Paths.get("src/main/java/ru/croc/javaschool2024/reports/report.json");

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Please provide the path to the JSON file as a command-line argument.");
            return;
        }

        try {
            List<Order> orders = JsonParser.readJsonFile(args[0]);
            String outputJsonInfo = JsonUtils.findMonthWithMaxWastes(orders);
            JsonReport.saveReportToFile(outputJsonInfo, reportPath);
        } catch (IOException ex) {
            System.err.println("The system cannot find the path specified: " + args[0]);
        }

    }
}
