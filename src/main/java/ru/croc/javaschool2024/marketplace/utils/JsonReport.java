package ru.croc.javaschool2024.marketplace.utils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonReport {

    private static final Path reportPath = Paths.get("src/main/java/ru/croc/javaschool2024/reports/report.json");

    /**
     * Метод отвечающий за вывод информации в json
     * @param jsonMonthInfo - информация для вывода
     */
    public static void saveReportToFile(String jsonMonthInfo) throws IOException {
        if (Files.notExists(reportPath.getParent())) {
            Files.createDirectories(reportPath.getParent());
        }
        Files.write(reportPath, jsonMonthInfo.getBytes());
    }
}