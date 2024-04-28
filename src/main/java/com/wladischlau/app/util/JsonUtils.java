package com.wladischlau.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Collection;
import java.util.List;

/**
 * Утилитарный класс для работы с JSON.
 */
public abstract class JsonUtils {

    /**
     * Список управляющих символов, после которых необходимо добавить пробел в JSON-строке.
     */
    private static final List<String> CONTROL_CHARACTERS = List.of(":", ",");

    /**
     * Объект ObjectMapper для преобразования JSON.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    /**
     * Преобразует JSON-строку в список объектов заданного типа.
     *
     * @param content   JSON-строка.
     * @param valueType тип элементов списка.
     * @param <T>       тип элементов списка.
     * @return список объектов заданного типа.
     * @throws JsonProcessingException если возникает ошибка при обработке JSON.
     */
    public static <T> List<T> parseJsonArray(
            String content,
            Class<T> valueType
    ) throws JsonProcessingException {
        return MAPPER
                .readValue(
                        content,
                        MAPPER
                                .getTypeFactory()
                                .constructCollectionType(List.class, valueType)
                );
    }

    /**
     * Преобразует коллекцию в JSON-строку вида:
     * <p>
     * <blockquote><pre>
     *     {"propertyName": ["collectionElement1", "collectionElement2"]}
     * </pre></blockquote>
     *
     * @param collection   коллекция объектов.
     * @param propertyName имя свойства.
     * @param <T>          тип элементов коллекции.
     * @return JSON-строка.
     * @throws JsonProcessingException если возникает ошибка при обработке JSON.
     */
    public static <T extends Collection<?>> String toJsonArrayFieldBeautified(
            T collection,
            String propertyName
    ) throws JsonProcessingException {
        return beautifyJsonString(toJsonArrayField(collection, propertyName));
    }

    /**
     * Преобразует коллекцию в JSON-строку фомрмата:
     * <p>
     * <blockquote><pre>
     *     {"propertyName":["collectionElement1","collectionElement2"]}
     * </pre></blockquote>
     *
     * @param collection   коллекция объектов.
     * @param propertyName имя свойства.
     * @param <T>          тип элементов коллекции.
     * @return JSON-строка.
     */
    public static <T extends Collection<?>> String toJsonArrayField(
            T collection,
            String propertyName
    ) {
        ObjectNode rootNode = MAPPER.createObjectNode();
        rootNode.set(propertyName, MAPPER.valueToTree(collection));
        return rootNode.toString();
    }

    private static String beautifyJsonString(String jsonString) {
        return jsonString.replaceAll(
                "[" + String.join("", CONTROL_CHARACTERS) + "]", "$0 "
        );
    }
}
