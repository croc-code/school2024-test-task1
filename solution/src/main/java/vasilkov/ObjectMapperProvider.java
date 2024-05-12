package vasilkov;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Интерфейс, определяющий метод для предоставления объекта ObjectMapper.
 */
public interface ObjectMapperProvider {
    /**
     * Получение объекта ObjectMapper.
     * @return Объект ObjectMapper.
     */
    ObjectMapper getObjectMapper();
}
