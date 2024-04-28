package org.crock.contest.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Адаптер для  Gson для корректного парсинга даты
 */
public class DateDeserializer implements JsonDeserializer<LocalDateTime> {
    private final DateTimeFormatter dateFormat;

    public DateDeserializer(DateTimeFormatter dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement,
                                     Type type,
                                     JsonDeserializationContext jsonDeserializationContext) {
        String dateStr = jsonElement.getAsString();
        return LocalDateTime.parse(dateStr, dateFormat);
    }
}