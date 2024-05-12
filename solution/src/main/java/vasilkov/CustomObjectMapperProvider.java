package vasilkov;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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

        mapper.disable(JsonWriteFeature.QUOTE_FIELD_NAMES.mappedFeature());

        return mapper;
    }

}
