package org.crock.contest.converter;

/**
 * @param <T> - объект, который читаем из json
 */
public interface ConverterFromJson<T> {
    T json2Object(String json);
}
