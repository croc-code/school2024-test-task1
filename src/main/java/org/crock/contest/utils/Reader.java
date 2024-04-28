package org.crock.contest.utils;

/**
 * @param <T> - объект, который хотим считать из файла json
 */
public interface Reader<T> {
    T readObjectFromJsonFile(String fileName);
}
