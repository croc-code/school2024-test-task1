import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {

    private final String userId;
    private final LocalDateTime orderedAt;
    private final OrderStatus status;
    private final BigDecimal total;

    public String getUserId(){
        return userId;
    }
    public LocalDateTime getOrderedAt(){
        return orderedAt;
    }
    public OrderStatus getStatus(){
        return status;
    }
    public BigDecimal getTotal(){
        return total;
    }

    public Order(String userId, LocalDateTime orderedAt, OrderStatus status, BigDecimal total){
        this.userId = userId;
        this.orderedAt = orderedAt;
        this.status = status;
        this.total = total;
    }
}
