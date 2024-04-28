package ru.croc.javaschool2024.marketplace.utils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonReport {

    private static final Path reportPath = Paths.get("../reports/report.json");
    public static void saveReportToFile(String json) throws IOException {
        if (Files.notExists(reportPath.getParent())) {
            Files.createDirectories(reportPath.getParent());
        }
        Files.write(reportPath, json.getBytes());
    }
}