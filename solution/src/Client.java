import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// Класс в соответствии с которым мы будем преобразовывать наших клиентов из json формата в объекты Java
public class Client {
    // Аннотация @JsonProperty указывается для того, чтобы дать понять библиотеке(в моём случае Jackson) какие ключи из
    // json файла каким полям класса соответствуют
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("ordered_at")
    private String orderedAt;
    @JsonProperty("status")
    private String status;
    @JsonProperty("total")
    private String total;

}
