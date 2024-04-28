import com.fasterxml.jackson.core.JsonProcessingException;
import dto.OrderDTO;
import dto.OrderStatus;
import util.FileUtils;
import util.JsonUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        List<OrderDTO> orders;

        try {
//            OrderGenerator.generateOrdersJsonFile(100_000, "format-test.json");
            orders = readOrdersFromJson("format.json");
        } catch (JsonProcessingException ex) {
            ex.printStackTrace(); // TODO: Dump to error log
            System.err.println("Error parsing JSON file located in: ");
            return;
        } catch (IOException ex) {
            ex.printStackTrace(); // TODO: Dump to error log
            System.err.println("Error reading JSON file located in: ");
            return;
        }

        long start = System.nanoTime();
        System.out.println(
                orders
                        .parallelStream()
                        .filter(Application::isOrderPaid)
                        .collect(
                                Collectors.groupingBy(
                                        order -> order.orderedAt().getMonth().toString().toLowerCase(),
                                        Collectors.reducing(BigDecimal.ZERO, OrderDTO::total, BigDecimal::add)
                                )
                        )
        );
        long finish = System.nanoTime();
        long timeElapsed = finish - start;

//        Map<String, BigDecimal> test = new HashMap<>();
//        JsonUtils.getParsed(order -> test.merge(order.orderedAt().getMonth().toString().toLowerCase(), order.total(), BigDecimal::add));

        System.out.println("Time elapsed: " + timeElapsed / 1_000_000 + "ms");
    }

    public static boolean isOrderPaid(OrderDTO order) {
        return order.status() == OrderStatus.DELIVERY ||
               order.status() == OrderStatus.COMPLETED;
    }

    public static List<OrderDTO> readOrdersFromJson(String path) throws IOException {
        return JsonUtils.parseJsonArray(FileUtils.readJsonFile(path), OrderDTO.class);
    }
}
