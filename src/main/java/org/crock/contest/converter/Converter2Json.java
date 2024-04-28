package org.crock.contest.converter;

/**
 *
 * @param <T> -  Объект, который хотим записать в json
 */
public interface Converter2Json<T> {
    String object2Json(T object);

}
