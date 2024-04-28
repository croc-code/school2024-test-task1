import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dto.OrderDTO;
import dto.OrderStatus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class OrderGenerator {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public static void generateOrdersJsonFile(int numOrders, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("[\n");
            Random random = new Random();
            for (int i = 0; i < numOrders; i++) {
                OrderDTO order = new OrderDTO(
                        UUID.randomUUID(),
                        LocalDateTime.now().minusDays(random.nextInt(365)),
                        OrderStatus.values()[random.nextInt(OrderStatus.values().length)],
                        BigDecimal.valueOf(random.nextDouble() * 2000).setScale(2, RoundingMode.HALF_UP)
                );
                writer.write(objectMapper.writeValueAsString(order));
                writer.write(i == numOrders - 1 ? "\n" : ",\n");
            }
            writer.write("]");
        }
    }
}
