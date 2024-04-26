package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static JSONArray readJsonFile(String fileName) throws FileNotFoundException {
        try (FileReader reader = new FileReader(fileName)) {//пробуем прочитать файл и если получается, то функция возвращает массив из объектов json.
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;
            return jsonArray;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static JSONObject generateReport(JSONArray jsonArray){
        String[] monthNames = {"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"};
        JSONObject jsonObjectMonths = new JSONObject();// этот json объект необходим, чтобы в него записывать массив
//из месяцев по ключу "months". Именно его текущая функция вернёт в конце выполнения(требование задания).
        float maxSumOfSpentMoney = 0;// максимальное значение количества потраченных денег, которое будет обновляться, если найдётся больше
        ArrayList<String> monthArray = new ArrayList<>();//изменяемый массив, который будет передаваться в jsonObjectMonths
// по ключу "months". Так как названия месяцев пробегаются в порядке следования в году, значит, необходимо
//добавлять элементы в конец массива, поэтому удобнее всего взять ArrayList.
        for (int monthNumber = 1; monthNumber < 13; monthNumber++) {
            float sumOfSpentMoneyInMonth = 0;//в эту переменную будут суммироваться траты в текущем месяце
            for (int index = 0; index < jsonArray.size(); index++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(index);//каждую итерацию записываем в эту переменную текущий объект из jsonArray.
                String currentMonthStr = jsonObject.get("ordered_at").toString().substring(5 ,7);// по ключу "ordered_at" получаем строку, из которой выделяем подстроку с 5 по 7 элемент, потому что там содержится порядковый номер месяца.
                int currentMonth = Integer.parseInt(currentMonthStr);//переводим подстроку из String в int, чтобы можно было сравнить с monthNumber, который int.
                if((monthNumber == currentMonth) && jsonObject.get("status").toString().equals("COMPLETED")){//если месцы совпали и статус заказа "COMPLETED", то прибавляем цену к текущей сумме трат в этом месяце.
                    float spentMoneyOfObject = Float.valueOf((String) jsonObject.get("total"));
                    sumOfSpentMoneyInMonth += spentMoneyOfObject;
                }
            }
            if(sumOfSpentMoneyInMonth > maxSumOfSpentMoney){//если значение трат за месяц превысило максимальное значение трат за месяц
                maxSumOfSpentMoney = sumOfSpentMoneyInMonth;//то максимальному значению присвоится текущее суммарное значение трат.
                jsonObjectMonths.remove("months");//удалится текущее значение, которое лежит по ключу "months".
                monthArray.clear();//сам массив месяцев очистится
                monthArray.add(monthNames[monthNumber - 1]);//добавится текущий
                jsonObjectMonths.put("months", monthArray);//положим по ключу "months" обновлённый массив месяцев.
            }
            else if(sumOfSpentMoneyInMonth == maxSumOfSpentMoney){//если текущая сумма совпала с максимальной
                monthArray.add(monthNames[monthNumber - 1]);//добавим этот месяц в массив месяцев
                jsonObjectMonths.put("months", monthArray);//положим по ключу "months" обновлённый массив месяцев.
            }
        }
        outputConsole(monthArray);//здесь используется функция вывода названия месяцев на консоль.
        return jsonObjectMonths;
    }

    public static void outputConsole(ArrayList monthArray){//в качестве аргумента передаём массив месяцев.
        for (int i = 0; i < monthArray.size(); i++) {//итерируем поочереди каждый элемент и выводим его.
            if(i < monthArray.size() - 1){
                System.out.print(monthArray.get(i) + ", ");
            }
            else {
                System.out.println(monthArray.get(i));
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        JSONArray jsonArray = readJsonFile("input.json");//читаем файл, передав название файла в качестве аргумента
        generateReport(jsonArray);//создаём отчёт, а также внутри заключена функция вывода на консоль.
    }
}