import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        // JSON-файл
        String filepath = String.valueOf(Paths.get("format.json"));
        // Генерация отчета
        String report = generateReport(filepath);
        // Вывод отчета в консоль
        System.out.println(report);
    }
    public static String generateReport(String filepath) {
        try {

            // Чтение файла и парсинг данных в массив json-объектов
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader(filepath));

            // Список месяцев в порядке их следования в течении года
            List<String> orderedMonths = new ArrayList<>();
            for (Month month : Month.values()) {
                orderedMonths.add(month.toString().toLowerCase());
            }

            // Map для хранения общей суммы трат за каждый месяц
            Map<String, Double> totalPerMonth = new LinkedHashMap<>();
            for (String month : orderedMonths) {
                totalPerMonth.put(month, 0.0);
            }

            // Вычисление общей суммы трат за месяц
            for (Object order : jsonArray) {
                JSONObject jsonOrder = (JSONObject) order;
                // Учёт только завершенных заказов
                if (!jsonOrder.get("status").equals("COMPLETED")) {
                    continue;
                }

                // Получение данных о покупке (месяце и сумме)
                String orderedAt = jsonOrder.get("ordered_at").toString().substring(0, 10);
                LocalDate date = LocalDate.parse(orderedAt);
                String month = date.getMonth().toString().toLowerCase();
                Double total = Double.parseDouble((String) jsonOrder.get("total"));
                // Добавление суммы заказа к общей сумме за месяц
                totalPerMonth.put(month, totalPerMonth.getOrDefault(month, 0.0) + total);
            }

            // Максимальная сумма трат за месяц
            double maxTotal = Collections.max(totalPerMonth.values());
            // Список месяцев с максимальной суммой трат
            List<String> suitableMonths = new ArrayList<>();
            for (Map.Entry<String, Double> entry : totalPerMonth.entrySet()) {
                if (entry.getValue() == maxTotal) {
                    suitableMonths.add(entry.getKey());
                }
            }

            // Создание JSON-объекта с месяцами с наибольшими тратами пользователей
            JSONObject result = new JSONObject();
            result.put("months", suitableMonths);
            // Возврат строки в формате JSON
            return result.toJSONString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            // Возврат пустого JSON-объекта в случае ошибки
            return "{}";
        }
    }
}