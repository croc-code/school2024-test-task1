package ru.croc.javaschool2024.marketplace.utils;


import java.io.IOException;

public class JsonReport {

    /**
     * Метод отвечающий за вывод информации в json
     *
     * @param jsonMonthInfo - информация для вывода
     */
    public static byte[] generateReport(String jsonMonthInfo) throws IOException {
        return jsonMonthInfo.getBytes();
    }
}