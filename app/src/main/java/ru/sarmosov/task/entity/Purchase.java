package ru.sarmosov.task.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.sarmosov.task.enums.Status;
import ru.sarmosov.task.utils.DateFormatUtils;

import java.time.LocalDate;
import java.time.Month;

public class Purchase {

    private String user_id;
    private LocalDate ordered_at;
    private Status status;
    private Double total;


    @JsonProperty("ordered_at")
    public void setOrdered_at(String ordered_at) {
        this.ordered_at = DateFormatUtils.formatDate(ordered_at);
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = Status.fromString(status);
    }

    @JsonProperty("user_id")
    public void setId(String id) {
        this.user_id = id;
    }

    @JsonProperty("total")
    public void setTotal(String total) {
        this.total = Double.valueOf(total);
    }


    @Override
    public String toString() {
        return "Purchase{" +
                "user_id='" + user_id + '\'' +
                ", ordered_at=" + ordered_at +
                ", status='" + status + '\'' +
                ", total=" + total +
                '}';
    }



    public Status getStatus() {
        return status;
    }

    public Double getTotal() {
        return total;
    }

    public Month getMonth() {
        return ordered_at.getMonth();
    }
}
