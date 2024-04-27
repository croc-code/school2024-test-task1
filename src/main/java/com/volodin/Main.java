package com.volodin;

import com.volodin.jsonclasses.JsonWorker;
import com.volodin.orderclasses.OrderAnalysis;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JsonWorker jsonWorker = new JsonWorker("input.json");
        String report = OrderAnalysis.createReport(jsonWorker.getOrdersFromFile());
        jsonWorker.createReportFile(report);
        System.out.println(report);

    }
}
