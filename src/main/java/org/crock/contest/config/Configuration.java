package org.crock.contest.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.crock.contest.converter.ConverterFromJson;
import org.crock.contest.converter.OrderArrayFromJsonConverter;
import org.crock.contest.order.Order;
import org.crock.contest.utils.DateDeserializer;
import org.crock.contest.utils.Reader;
import org.crock.contest.utils.ListOrderReader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Класс конфигурации объектов:
 * - {@link Gson}. Для{@link Gson} используется адаптер{@link DateDeserializer}
 * для записи даты из json в {@link Order}.
 * - {@link ListOrderReader} - чтение из файла массива заказов
 */
public class Configuration {
    public static Gson gsonInit() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new DateDeserializer(pattern));
        return gsonBuilder.create();
    }

    public static Reader<List<Order>> listOrdersReader() {
        ConverterFromJson<List<Order>> converter2Orders = new OrderArrayFromJsonConverter(gsonInit());
        return new ListOrderReader(converter2Orders);
    }
}
