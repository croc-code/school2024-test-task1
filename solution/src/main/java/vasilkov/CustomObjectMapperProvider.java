package vasilkov;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
/**
 * Класс, предоставляющий настраиваемый объект ObjectMapper.
 */
public class CustomObjectMapperProvider implements ObjectMapperProvider {

    /**
     * Получение настроенного объекта ObjectMapper.
     * @return Настроенный объект ObjectMapper.
     */
    @Override
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Регистрация модуля для работы с датами и временем в формате Java Time
        mapper.registerModule(new JavaTimeModule());

        // Создание модуля для настройки сериализатора строк
        SimpleModule module = new SimpleModule();
        // Добавление сериализатора для строк
        module.addSerializer(String.class, new CustomStringSerializer());

        // Регистрация модуля в ObjectMapper
        mapper.registerModule(module);
        // Отключение автоматического заключения имен полей в кавычки при сериализации JSON
        mapper.disable(JsonWriteFeature.QUOTE_FIELD_NAMES.mappedFeature());

        return mapper;
    }

    /**
     * Внутренний класс для кастомной сериализации строк.
     */
    private static class CustomStringSerializer extends StdSerializer<String> {

        protected CustomStringSerializer() {
            super(String.class);
        }

        /**
         * Метод для сериализации строки с добавлением специальных символов.
         * @param value Строка для сериализации.
         * @param jsonGenerator Генератор JSON.
         * @param serializerProvider Провайдер сериализатора.
         * @throws IOException Исключение ввода-вывода.
         */
        @Override
        public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeRawValue("«" + value + "»");
        }
    }
}