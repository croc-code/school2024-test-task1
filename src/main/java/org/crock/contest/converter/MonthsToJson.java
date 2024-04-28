package org.crock.contest.converter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Класс для перевода массива массивов строк(по задаче это месяца в каждом году.
 * Т.е. каждый массив - отдельный год с его месяцами, где была максимальная сумма.
 */
public class MonthsToJson implements Converter2Json<List<List<String>>> {
    private final Gson gson;

    public MonthsToJson(final Gson gson) {
        this.gson = gson;
    }

    /**
     *
     * @param object - объект который переводим в Json
     * @return json
     * Пример строки после отработки такого метода:
     * {"months":["march"]}
     * {"months":["december"]}
     */
    @Override
    public String object2Json(List<List<String>> object) {
        StringBuilder json = new StringBuilder();
        for (var el : object) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("months", gson.toJsonTree(el));
            json.append(jsonObject).append('\n');
        }
        return json.toString();
    }
}
