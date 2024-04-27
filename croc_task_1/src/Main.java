import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        // money : month pair
        Map<String, String> moneyMonthPair = new Hashtable<>();

        // month in 2023-01-16T13:56:39.492 form
        List<String> months = new ArrayList<>();

        // names of months
        List<String> updatedMonths = new ArrayList<>();

        // total spent from each purchase
        List<String> totals = new ArrayList<>();

        // java object in form of json. Takes values from input.json
        JSONArray jsonArray = new JSONArray(readJson());

        // takes date and money values from input.json,
        // transforms months to a normal form
        // puts values to the dictionary
        getMoneyMonthsValues(moneyMonthPair, jsonArray, months, totals, updatedMonths);

        //counts money in one month even if many purchases in one month
        Map<String, Double> totalSpentForMonths = findMonthsSums(moneyMonthPair);

        // returns json object but firstly finds maximum spent month in totalSpentForMonths pair.
        returnJson(findMaxSpend(totalSpentForMonths));
    }

    // creates String object json with values from input.json
    private static String readJson() {
        String path = "src/input.json";
        String json = " ";
        try {
            json = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    // finds purchases of certain status, adds totals and months to the arrays, edit month format, puts values to money : month pair
    private static void getMoneyMonthsValues(Map<String, String> dictionary, JSONArray jsonArray, List<String> months, List<String> totals, List<String> updatedMonths) {
        for (int i = 0; i < jsonArray.length() - 1; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("status").equals("COMPLETED")) {
                totals.add(jsonObject.getString("total"));
                months.add(jsonObject.getString("ordered_at"));
                LocalDateTime updatedMonth = LocalDateTime.parse(months.get(i), DateTimeFormatter.ISO_DATE_TIME);
                String monthName = updatedMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                updatedMonths.add(monthName);
                dictionary.put(totals.get(i), updatedMonths.get(i));
            }
        }
    }

    // finds sums of the purchases for all similar months
    private static Map<String, Double> findMonthsSums(Map<String, String> dictionary) {
        Map<String, Double> sums = new HashMap<>();
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            Double key = Double.valueOf(entry.getKey());
            String value = entry.getValue();
            if (sums.containsKey(value)) {
                Double sum = sums.get(value) + key;
                sums.put(value, sum);
            } else {
                sums.put(value, key);
            }
        }
        return sums;
    }

    // finds max spent month among all months
    private static String findMaxSpend(Map<String, Double> sums) {
        double maxValue = Double.MIN_VALUE;
        String maxSpendingMonth = null;
        for (Map.Entry<String, Double> entry : sums.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxSpendingMonth = entry.getKey();
            }
        }
        return maxSpendingMonth;
    }

    // creates json object and outputs it
    private static void returnJson(String topMonth) {
        topMonth = topMonth.toLowerCase();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(topMonth);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("months", jsonArray);
        System.out.println(jsonObject);
    }
}

