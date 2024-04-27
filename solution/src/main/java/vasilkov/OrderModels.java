package vasilkov;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Модели данных для заказов.
 */
//  Комментарий
//  Не хотелось делать решение громоздким(иметь 10тки классов)
public class OrderModels {
    /**
     * Перечисление статусов заказа.
     */
    public enum Status { COMPLETED, CANCELED, CREATED, DELIVERY }

    /**
     * Запись данных о заказе.
     */
    public record Order(
            UUID user_id, // Идентификатор пользователя
            LocalDateTime ordered_at, // Время размещения заказа
            Status status, // Статус заказа
            double total // Общая сумма заказа
    ) {}

    /**
     * Запись для хранения результата.
     */
    public record Result(
            @JsonProperty("«months»") List<String> months // Список месяцев в результате
    ) {}
}
