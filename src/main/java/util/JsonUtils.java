package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonUtils {

    public static <T> List<T> parseJsonArray(String content, Class<T> valueType)
            throws JsonProcessingException {
        var mapper = getOrderMapper();
        return mapper
                .readValue(
                        content,
                        mapper
                                .getTypeFactory()
                                .constructCollectionType(List.class, valueType)
                );
    }

    public static ObjectMapper getOrderMapper() {
        var mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    }
}
