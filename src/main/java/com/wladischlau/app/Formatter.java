package com.wladischlau.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wladischlau.app.dto.order.OrderDTO;
import com.wladischlau.app.exception.NoOrdersFoundException;
import com.wladischlau.app.service.OrderService;
import com.wladischlau.app.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * Основной класс приложения.
 * </p>
 * <p>
 * Предоставляет статический метод {@link Formatter#main(String[])} для
 * форматирования данных о {@link OrderDTO заказах} и вывода результатов
 * в формате <a href="https://www.json.org/json-en.html">JSON</a>.
 * </p>
 * <p>
 * Для использования файла с данными, не находящегося
 * по {@link Formatter#DEFAULT_DATA_LOCATION стандартному пути}, необходимо
 * указать путь к файлу в качестве аргумента
 * командной строки при запуске программы.
 * </p>
 *
 * @see OrderService
 * @see JsonUtils
 */
public class Formatter {

    /**
     * Путь к файлу с данными по умолчанию.
     */
    public static final String DEFAULT_DATA_LOCATION = "format.json";

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(Formatter.class);
    private static final Logger ERROR_LOGGER = LoggerFactory.getLogger("errorFileLogger");

    public static void main(String[] args) {
        List<OrderDTO> orders;
        var dataLocation =
                args.length > 0 && !args[0].isBlank() ? args[0] : DEFAULT_DATA_LOCATION;
        try {
            orders = OrderService.readOrdersFromJson(dataLocation);
        } catch (JsonProcessingException ex) {
            var message = String.format(
                    "Error parsing JSON file located in: %s.",
                    dataLocation
            );
            DEFAULT_LOGGER.error(message);
            ERROR_LOGGER.error(message, ex);
            return;
        } catch (IOException ex) {
            var message = String.format(
                    "File not found in location: %s.",
                    dataLocation
            );
            DEFAULT_LOGGER.error(message);
            ERROR_LOGGER.error(message, ex);
            return;
        }

        try {
            System.out.print(
                    JsonUtils.toJsonArrayFieldBeautified(
                            OrderService.processOrderList(orders),
                            "months"
                    )
            );
        } catch (NoOrdersFoundException | JsonProcessingException ex) {
            DEFAULT_LOGGER.error(ex.getMessage());
            ERROR_LOGGER.error(ex.getMessage(), ex);
        }
    }
}
