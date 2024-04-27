package application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.Order;
import utils.ReportGenerator;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Order> orders = objectMapper.readValue(new File("input.json"), new TypeReference<List<Order>>(){});
            System.out.println(ReportGenerator.generate(orders));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

// запись json
//ObjectMapper objectMapper = new ObjectMapper();
//Car car = new Car("yellow", "renault");
//objectMapper.writeValue(new File("target/car.json"), car);
