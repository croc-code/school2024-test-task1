package ru.croc.javaschool2024.marketplace.utils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonReport {


    /**
     * Метод отвечающий за вывод информации в json
     *
     * @param jsonMonthInfo - информация для вывода
     */
    public static void saveReportToFile(String jsonMonthInfo, Path reportPath) throws IOException {
        if (Files.notExists(reportPath.getParent())) {
            Files.createDirectories(reportPath.getParent());
        }
        Files.write(reportPath, jsonMonthInfo.getBytes());
    }
}