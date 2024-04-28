package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**Также создадим еще один POJO под названием SuccessfulMonths, который будет предназначен для сериализации
 * и корректного вывода по условию задания**/
@Getter
@Setter
@AllArgsConstructor
public class SuccessfulMonths {
    private List<String> months;
}
