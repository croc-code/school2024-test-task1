package vasilkov;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
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
    @JsonSerialize(using = Result.CustomStringSerializer.class)
    public record Result(
            List<String> months // Список месяцев в результате
    ) {
        /**
         * Вспомогательный метод для получения имени переменной, содержащей список месяцев.
         * @return Имя переменной списка месяцев.
         */
        private static String getResultVariableName() {
            return Result.class.getRecordComponents()[0].getName();
        }

        /**
         * Кастомный сериализатор для преобразования списка месяцев в JSON формат с использованием специальных символов.
         */
        public static class CustomStringSerializer extends StdSerializer<Result> {
            public CustomStringSerializer() {
                this(null);
            }
            public CustomStringSerializer(Class<OrderModels.Result> n) {
                super(n);
            }

            @Override
            public void serialize(OrderModels.Result result, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

                final String fieldNamePrefix = "«";
                final String fieldNameSuffix = "»";
                final String fieldName = fieldNamePrefix + getResultVariableName() + fieldNameSuffix;
                final String startArray = ": [";
                final String arrayDelimiter = ", ";
                final String endArray = "]";


                jsonGenerator.writeStartObject();
                jsonGenerator.writeFieldName(fieldName);

                jsonGenerator.writeRaw(startArray);

                for (
                        Iterator<String> iterator = result.months().iterator();
                        iterator.hasNext();
                )
                {
                    String month = iterator.next();
                    jsonGenerator.writeRaw(fieldNamePrefix + month + fieldNameSuffix);

                    if (iterator.hasNext()) {
                        jsonGenerator.writeRaw(arrayDelimiter);
                    }
                }

                jsonGenerator.writeRaw(endArray);

                jsonGenerator.writeEndObject();

            }
        }
    }
}
