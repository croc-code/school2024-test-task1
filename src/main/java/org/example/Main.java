package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static final String[] monthNames = {"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"};
    public static JSONArray readJsonFile(String fileName){
        try (FileReader reader = new FileReader(fileName)) {//пробуем прочитать файл и если получается, то функция возвращает массив из объектов json.
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;
            return jsonArray;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static float getSumOfSpentMoneyInMonth(JSONArray jsonArray, int monthNumber){
        float sumOfSpentMoneyInMonth = 0;
        for (int index = 0; index < jsonArray.size(); index++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(index);//каждую итерацию записываем в эту переменную текущий объект из jsonArray.
            String currentMonthStr = jsonObject.get("ordered_at").toString().substring(5 ,7);// по ключу "ordered_at" получаем строку, из которой выделяем подстроку с 5 по 7 элемент, потому что там содержится порядковый номер месяца.
            int currentMonth = Integer.parseInt(currentMonthStr);//переводим подстроку из String в int, чтобы можно было сравнить с monthNumber, который int.
            if((monthNumber == currentMonth) && jsonObject.get("status").toString().equals("COMPLETED")){//если месцы совпали и статус заказа "COMPLETED", то прибавляем цену к текущей сумме трат в этом месяце.
                float spentMoneyOfOrder = Float.valueOf((String) jsonObject.get("total"));
                sumOfSpentMoneyInMonth += spentMoneyOfOrder;
            }
        }
        return sumOfSpentMoneyInMonth;
    }
    
    public static JSONObject generateReport(JSONArray jsonArray){
        JSONObject jsonObjectMonths = new JSONObject();// этот json объект необходим, чтобы в него записывать массив
//из месяцев по ключу "months". Именно его текущая функция вернёт в конце выполнения(требование задания).
        float maxSumOfSpentMoney = 0;// будет обновляться, если найдётся больше
        ArrayList<String> monthArrayWithMaxSum = new ArrayList<>();
        for (int monthNumber = 1; monthNumber < 13; monthNumber++) {
            float sumOfSpentMoneyInMonth = getSumOfSpentMoneyInMonth(jsonArray, monthNumber);
            if(sumOfSpentMoneyInMonth > maxSumOfSpentMoney){
                maxSumOfSpentMoney = sumOfSpentMoneyInMonth;
                monthArrayWithMaxSum.clear();
                monthArrayWithMaxSum.add(monthNames[monthNumber - 1]);//добавится текущий месяц
            }
            else if(sumOfSpentMoneyInMonth == maxSumOfSpentMoney){//если текущая сумма совпала с максимальной
                monthArrayWithMaxSum.add(monthNames[monthNumber - 1]);//добавим этот месяц в массив месяцев
            }
        }
        jsonObjectMonths.put("months", monthArrayWithMaxSum);
        outputConsole(monthArrayWithMaxSum);//здесь используется функция вывода названия месяцев на консоль.
        return jsonObjectMonths;
    }

    public static void outputConsole(ArrayList monthArray){//в качестве аргумента передаём массив месяцев.
        for (int i = 0; i < monthArray.size(); i++) {
            if(i < monthArray.size() - 1){
                System.out.print(monthArray.get(i) + ", ");
            }
            else {
                System.out.println(monthArray.get(i));
            }
        }
    }

    public static void main(String[] args){
        JSONArray jsonArray = readJsonFile("input.json");//читаем файл, передав название файла в качестве аргумента
        generateReport(jsonArray);//создаём отчёт, а также внутри заключена функция вывода на консоль.
    }
}