package com.xx1ee;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(getReport(scanner.nextLine()));
        scanner.close();
    }
    /**
     * Метод формирования отчета
     * Конвертатируем объекты из json в List заказов(Purchase) с помощью jsonConverter
     * Вызвав из ReportGenerator метод generateMonthsList, получаем месяцы, у которых сумма трат максимальна,
     * отсортированные в порядке следования в течение года
     * Вызвав convertMonthsToJson возвращаем строку в формате json следующего формата:
     *    - {"months": ["march"]}
     *    - {"months": ["march", "december"]}
     **/
    public static String getReport(String fileName) {
        JsonConverter jsonConverter = new JsonConverter(fileName);
        List<Purchase> purchaseList = jsonConverter.getPurchasesFromJson();
        List<String> monthsList = ReportGenerator.generateMonthsList(purchaseList);
        return jsonConverter.convertMonthsToJson(monthsList);
    }
}
