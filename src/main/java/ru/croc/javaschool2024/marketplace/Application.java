package ru.croc.javaschool2024.marketplace;

import ru.croc.javaschool2024.marketplace.models.Order;
import ru.croc.javaschool2024.marketplace.utils.JsonParser;
import ru.croc.javaschool2024.marketplace.utils.JsonReport;
import ru.croc.javaschool2024.marketplace.utils.JsonUtils;

import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Please provide the path to the JSON file as a command-line argument.");
            return;
        }

        try {
            List<Order> orders = JsonParser.readJsonFile(args[0]);
            String outputJsonInfo = JsonUtils.findMonthWithMaxWastes(orders);
            JsonReport.saveReportToFile(outputJsonInfo);
        } catch (IOException ex) {
            System.out.println("The system cannot find the path specified: " + args[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
