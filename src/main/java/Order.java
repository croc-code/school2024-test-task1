import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Order {
  @JsonProperty("user_id")
  private String userId;

  @JsonProperty("ordered_at")
  private Date orderedAt;

  private String status;

  private Double total;
  public Integer getMonth(){
    return orderedAt.toLocalDate().getMonthValue();
  }
}
