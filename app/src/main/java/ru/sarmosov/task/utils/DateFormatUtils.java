package ru.sarmosov.task.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatUtils {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public static LocalDate formatDate(String date) {
        return LocalDate.parse(date, formatter);
    }
}
