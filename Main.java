import Exceptions.EmptyOrderArrayException;
import Exceptions.InvalidOrderListException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static final String FILE_NAME = "format.json";

    public static void main(String[] args) {
        try {
            JSONArray jsonOrderArray = (JSONArray) new JSONParser().parse(new FileReader(FILE_NAME));

            String report = report(createOrderList(jsonOrderArray));

            System.out.println(report);

        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл с входными данными.");
        } catch (IOException e){
            System.out.println("Не удалось корректно прочитать файл со входными данными.");
        } catch (ParseException e){
            System.out.println("Входные данные некорректны.");
        } catch (InvalidOrderListException e){
            System.out.println(e.getMessage());
            System.out.println("Причина:");
            System.out.println(e.getCauseException().getMessage());
        } catch (EmptyOrderArrayException e){
            System.out.println(e.getMessage());
        }
    }
    //Этот метод из JSON-массива сделает список заказов.
    private static ArrayList<Order> createOrderList(JSONArray jsonOrderArray) throws InvalidOrderListException {

        try {

            ArrayList<Order> orderList = new ArrayList<>();

            for (Object item : jsonOrderArray) {
                orderList.add(new Order(
                        (String) ((JSONObject) item).get("user_id"),
                        LocalDateTime.parse((String) ((JSONObject) item).get("ordered_at")),
                        OrderStatus.valueOf((String) ((JSONObject) item).get("status")),
                        new BigDecimal((String) ((JSONObject) item).get("total")))
                );
            }
            return orderList;
        } catch (IllegalArgumentException | DateTimeParseException e){
            throw new InvalidOrderListException("Не удалось преобразовать список заказов.", e);
        }
    }

    private static String report(ArrayList<Order> orderList) throws EmptyOrderArrayException {
        if(orderList.isEmpty())
            throw new EmptyOrderArrayException("Массив заказов пуст");

        Map<Month, BigDecimal> monthTotal = orderList.stream().
                filter(order -> order.getStatus() == OrderStatus.COMPLETED). //Возьмём только завершённые
                collect(Collectors.toMap( //Преобразуем в Map, сгруппированную по месяцам, хранящую сумму для каждого месяца
                item -> item.getOrderedAt().getMonth(),
                Order::getTotal,
                BigDecimal::add     //Суммируем все значения getTotal() для каждого месяца
        ));

        BigDecimal max = monthTotal.entrySet().stream(). //Найдём максимальное значение за месяц
                 max(Map.Entry.comparingByValue()).orElseThrow().getValue();

        String[] maxMonths = monthTotal.entrySet().stream().
                filter(entry -> entry.getValue().equals(max)) //Возьмём только те месяцы, где сумма равна максимальной
                .sorted(Map.Entry.comparingByKey()) //Оставшиеся месяцы отсортируем
                .map(entry -> entry.getKey().toString()). //Преобразуем в поток, а потом и в массив строк
                toArray(String[]::new);

        String result = "{«months»: ["; //Соберём итоговую строку в требуемом формате
        for(int i = 0; i< maxMonths.length; i++) {
            result += "«" + maxMonths[i].toLowerCase() + "»";
            if(i!=maxMonths.length-1)
                result += ", ";
        }
        result += "]}";
        return result;

    }
}
