import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private final static String fileName = "input.json";
    private final static String completedStatus = "COMPLETED";

    private static final String[] months = new String[]{"«january»", "«february»", "«march»", "«april»",
            "«may»", "«june»", "«july»", "«august»", "«september»", "«october»", "«november»", "«december»"};


    public static void main(String[] args) throws Exception {

        List<OrderEntity> orders = readOrders(fileName);
        List<OrderEntity> filteredOrders = filterOrders(orders, completedStatus);
        Map<Integer,Double> totalSpentForEachMonth = findTotalSpentForEachMonth(filteredOrders);
        System.out.println(findMostSpentMonths(totalSpentForEachMonth));

    }

    private static List<OrderEntity> readOrders(String fileName) throws IOException {

        List<OrderEntity> orders = new ObjectMapper().readValue(new File(fileName), new TypeReference<>() {});
        return orders;
    }

    private static List<OrderEntity> filterOrders(List<OrderEntity> orders, String filter){

        return orders.stream().filter(order -> order.getStatus().equals(filter)).collect(Collectors.toList());
    }

    private static Map<Integer, Double> findTotalSpentForEachMonth(List<OrderEntity> orders){
        Map<Integer, Double> spentPerMonths = new HashMap<>();
        for (OrderEntity order : orders) {
            if (!spentPerMonths.containsKey(order.getDate())){
                spentPerMonths.put(order.getDate(), order.getTotal());
            }
            else {
                spentPerMonths.put(order.getDate(), spentPerMonths.getOrDefault(order.getDate(), 0.0) + order.getTotal());
            }
        }
        return spentPerMonths;
    }

    private static String findMostSpentMonths(Map<Integer,Double> totalSpent){

        String mostSpentMonths = "{«months»: [";
        StringBuilder builder = new StringBuilder(mostSpentMonths);
        int cnt = 0;
        List<Map.Entry<Integer, Double>> totalSpentList = new ArrayList<>(totalSpent.entrySet());
        totalSpentList.sort(Map.Entry.comparingByValue());

        double maxCost = totalSpentList.get(totalSpentList.size() - 1).getValue();
        for (Map.Entry<Integer, Double> monthData : totalSpentList) {
            if (monthData.getValue().equals(maxCost)){
                builder.append(months[monthData.getKey()-1]);
                if (cnt > 0){
                    builder.append(", " + months[monthData.getKey()-1]);
                }
                cnt++;
            }
        }
        builder.append("]}");
        return builder.toString();
    }
}
