package org.example.pojo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.Status;
import java.time.LocalDateTime;

/**Создадим POJO под названием Order. Названия полей POJO должны совпадать с полями JSON файла
 * Для простоты работы и уменьшения объемя кода была использована библиотека Lombok**/
@Getter
@Setter
@AllArgsConstructor
public class Order {
    private String user_id;
    private LocalDateTime ordered_at;
    private Status status;
    private double total;
}
