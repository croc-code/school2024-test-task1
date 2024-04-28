package org.crock.contest.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.crock.contest.order.Order;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.crock.contest.order.Orders.isValidOrder;

/**
 * {@link OrderArrayFromJsonConverter} класс для конвертации json в массив Order.
 */
public class OrderArrayFromJsonConverter implements ConverterFromJson<List<Order>> {
    private final Gson gson;

    public OrderArrayFromJsonConverter(final Gson gson) {
        this.gson = gson;
    }

    @Override
    public List<Order> json2Object(final String json) {
        // перевод json в массив order
        final Type listType = new TypeToken<List<Order>>() {
        }.getType();
        final List<Order> orders = gson.fromJson(json, listType);

        // валидация входных данных, так как некоторые поля могут быть null или не валидны
        // ( как например, цена может быть меньше нуля)
        List<Order> validOrders = new ArrayList<>();
        for (Order order : orders) {
            String errors = isValidOrder(order);
            if (errors.isEmpty()) {
                validOrders.add(order);
            } else {
                //оповещение пользователя, что не все заказы прошли проверка, а также указывается причина, почему
                // используется вместо логера в целях упрощения задачи
                System.out.println("Invalid order: " + order + " reasons:" + errors);
            }
        }
        // убираем повторные заказы (так как не сказано, что только уникальные заказы), а также убираем  null
        return validOrders.stream().distinct().filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}