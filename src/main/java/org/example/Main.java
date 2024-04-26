package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
*
* author matyushazvezda
 */

public class Main {
    //метод для формирования отчета
    private static String generateReport(List<String> maxMonths) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, List<String>> reportData = new HashMap<>();
        reportData.put("months", maxMonths);

        return objectMapper.writeValueAsString(reportData);
    }
    //метод для создания json файла
    private static void createJsonFile(String jsonContent, String filePath) throws IOException {
       try(FileWriter fileWriter = new FileWriter(filePath)) {
           fileWriter.write(jsonContent);
       }
    }

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Double> monthTotal = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);

        List<String> ans = new ArrayList<>();
        double maxTotal = Double.MIN_VALUE;

        try {
        // чтение json-файла
        File jsonFile = new File("src/main/resources/format.json");
        Sale[] sales = objectMapper.readValue(jsonFile, Sale[].class);

        //фильтрация заказов и суммирование суммы заказов по месяцам
        for (Sale value : sales) {
            if (value.getStatus().equals("COMPLETED")) {
                String month = dateFormat.format(value.getOrdered_at()).toLowerCase();
                /* если ключ month не существует, добавляет в map: monthTotal
                * в противном случае просуммирует существующее значение ключа
                * и значение value.getTotal()
                */
                monthTotal.merge(month, value.getTotal(), Double::sum);
            }
        }

        // поиск максимального месяца/месяцев
        for(String key : monthTotal.keySet()){
            Double value = monthTotal.get(key);
            /*
            * если value больше maxTotal, следовательно, нашли максимальный месяц
            * присваиваем maxTotal значение value,
            * очищаем полностью список и добавляем найденный месяц.
            * если value равен maxTotal, то добавляем в список новый элемент
            * без необходимости удаление элементов списка
            *
             */
            if(value > maxTotal){
                maxTotal = value;
                ans.clear();
                ans.add(key);
            } else if (value == maxTotal) {
                ans.add(key);
            }
        }
            /*вызов метода для формирования отчета JSON-файла
            * в директорию filePath
             */
            String filePath = "src/main/resources/output.json";
            createJsonFile(generateReport(ans), filePath);

            // вызов метода для формирования отчета в виде JSON-строки
            System.out.println(generateReport(ans));

            //обработка исключения: если нет файла для чтения, если произошла ошибка чтения/записи
        }   catch (FileNotFoundException e) {
            System.err.println("Ошибка: не удается найти указанный файл!");
            e.printStackTrace();
        }
            catch (IOException e) {
            System.err.println("Ошибка: произошла ошибка при чтении или записи json!");
            e.printStackTrace();
        }
    }
}