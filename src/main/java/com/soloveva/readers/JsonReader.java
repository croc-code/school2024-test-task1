package com.soloveva.readers;
import com.soloveva.models.Order;
import com.soloveva.models.OrderStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class JsonReader {
    public static List<Order> readJsonAndSaveData(String filePath)
            throws IOException, ParseException, NumberFormatException {
        JSONParser parser = new JSONParser();
        List<Order> orders = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            Object array = parser.parse(reader);
            JSONArray jsonArray = (JSONArray) array;

            for(Object element : jsonArray){
                JSONObject jsonData = (JSONObject) element;

                String userID = (String) jsonData.get("user_id");

                String orderedAt = (String) jsonData.get("ordered_at");
                LocalDateTime orderTime = LocalDateTime.parse(orderedAt);

                String status = (String) jsonData.get("status");
                OrderStatus orderStatus = OrderStatus.valueOf(status);

                String total = (String) jsonData.get("total");
                Double totalPrice = Double.parseDouble(total);

                Order order = new Order(userID, orderTime, orderStatus, totalPrice);

                orders.add(order);
            }
        }

        return orders;
    }
}
