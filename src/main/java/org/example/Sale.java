package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * author matyushazvezda
 * класс Sale: сущность заказа
 * содержит id пользователя, дату заказа,
 * статус заказа и сумму заказа.
 *
 * Lombok аннотация для уменьшения кода:
 * автомотическая генерация конструкторов,
 * доступ к полям и т.д.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    private String user_id;
    private Date ordered_at;
    private String status;
    private double total;
}
