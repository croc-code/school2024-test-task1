import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Purchase {
    private String user_id;
    private LocalDateTime ordered_at;
    private Status status;
    private BigDecimal total;

    public Purchase(String user_id, LocalDateTime ordered_at, Status status, BigDecimal total) {
        this.user_id = user_id;
        this.ordered_at = ordered_at;
        this.status = status;
        this.total = total;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getOrdered_at() {
        return ordered_at;
    }

    public void setOrdered_at(LocalDateTime ordered_at) {
        this.ordered_at = ordered_at;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
