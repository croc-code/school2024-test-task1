package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.enums.Status;
import org.example.pojo.Order;
import org.example.pojo.SuccessfulMonths;

import java.io.File;
import java.io.IOException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public class JsonParser implements Parser<Order>{

    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    @Override
    public List<Order> deserialize() {
        mapper.registerModule(new JavaTimeModule());
        List<Order> orders;
        try {
            orders = mapper.readValue(new File("format.json"), new TypeReference<>(){});
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public String serialize(ArrayList<String> months) {
        months.sort(Comparator.comparingInt(month -> Month.valueOf(month.toUpperCase()).getValue()));
        SuccessfulMonths successfulMonths = new SuccessfulMonths(months);
        String json;
        try {
            json = mapper.writeValueAsString(successfulMonths);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    @Override
    public String doCounting() {
        List<Order> orders = deserialize();
        ArrayList<String> months = new ArrayList<>();
        double maxTotal = countTotalMax(orders);
        for(Order order : orders){
            if(order.getStatus().equals(Status.COMPLETED) && order.getTotal() == maxTotal){
                months.add(order.getOrdered_at().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase());
            }
        }
        return serialize(months);
    }

    private double countTotalMax(List<Order> orders){
        double maxTotal = orders.get(0).getTotal();
        for(Order order : orders){
            if(maxTotal < order.getTotal()){
                maxTotal = order.getTotal();
            }
        }
        return maxTotal;
    }
}
