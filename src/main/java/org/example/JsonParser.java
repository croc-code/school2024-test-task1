package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.enums.Status;
import org.example.pojo.Order;
import org.example.pojo.SuccessfulMonths;

import java.io.File;
import java.io.IOException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * JsonParser - это класс предназначенный для работы с json форматом и выполнения задания
 **/
public class JsonParser implements Parser<Order>{

    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    /**Данный метод десиализирует json файл format.json и возвращает лист из объектов класса Order(POJO).
     * Закоментированная часть метода предназначена для регистрация модуля при работе с LocalDateTime,
     * если не срабатывает findAndRegisterModules()**/
    @Override
    public List<Order> deserialize() {
        //mapper.registerModule(new JavaTimeModule());
        List<Order> orders;
        try {
            orders = mapper.readValue(new File("format.json"), new TypeReference<>(){});
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    /**Данный метод сериализирует ArrayList месяцев в формат json и возвращает строку в данном формате.
     * Вначале проихсодит сортировка месяцев в порядке их следования в течения года,
     * далее создается POJO под названием SuccessfulMonths и сериализируется**/
    @Override
    public String serialize(ArrayList<String> months) {
        /* Здесь идет сравнение месяцев сначала приводя к значению перечисления Month, а затем сортируем
          по числовому значению месяца**/
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

    /**В данном методе описана основная логика программы, а именно: нахождение месяцев или месяц, когда
     * пользователи тратили больше всего. Получаем максимальную сумму как разультат метода countTotalMax
     * , далее проверяем чтобы это была максимальная сумма заказа.
     * Добавляем месяц в нижнем регистре в ArrayList, если условие соблюдается.
     * Если возрващает пустой файл json, значит нет завершенных заказов**/
    @Override
    public String doCounting() {
        List<Order> orders = deserialize();
        ArrayList<String> months = new ArrayList<>();
        double maxTotal = countTotalMax(orders);
        for(Order order : orders) {
            if (order.getTotal() == maxTotal){
                months.add(order.getOrdered_at().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase());
            }
        }
        return serialize(months);
    }

    /**Данный метод предназначен для поиска максимальной суммы заказа, который выполнен(COMPLETED)
     * Цикл проходится по всему листу orders и возвращает максимальное значение, которое удовлетворяет усливию
     * maxTotal изначально инициализируется минимальный значением Double, так как заказа с отрицательной суммой не может быть**/
    private double countTotalMax(List<Order> orders) {
        double maxTotal = Double.MIN_VALUE;
        for(Order order : orders) {
            if (maxTotal < order.getTotal() && order.getStatus().equals(Status.COMPLETED)){
                maxTotal = order.getTotal();
            }
        }
        return maxTotal;
    }
}
