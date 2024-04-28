package application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.Order;
import java.io.File;
import java.io.IOException;
import java.util.List;

class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Order> orders = objectMapper.readValue(new File("input.json"), new TypeReference<>(){});
            System.out.println(ReportGenerator.generate(orders));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
