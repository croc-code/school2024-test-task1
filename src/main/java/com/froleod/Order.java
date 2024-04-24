package com.froleod;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс, представляющий данные о заказе пользователя.
 * Аннотация @Lombok - для автоматической генерации геттеров, сеттеров, конструкторов и других стандартных методов
 */

@Data
public class Order {
    // Идентификатор пользователя, совершившего заказ
    private String user_id;

    // Дата и время совершения заказа
    private LocalDateTime ordered_at;

    // Статус заказа
    private String status;


    // Общая сумма заказа
    private double total;

}
