package com.soloveva;

import com.soloveva.services.AnalyticsSystem;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Неверное количество аргументов!");
            return;
        }

        AnalyticsSystem analyticsSystem = new AnalyticsSystem();

        try {
            String result = analyticsSystem.getMostProfitMonths(args[0]);

            System.out.println("Отчёт сформирован успешно!");
            System.out.println(result);
        } catch (IOException | ParseException e) {
            System.out.println("Ошибка при формировании отчёта!");
            System.out.println(e.getMessage());
        }
    }
}

