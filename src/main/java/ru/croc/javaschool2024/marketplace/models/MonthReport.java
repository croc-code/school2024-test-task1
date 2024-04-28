package ru.croc.javaschool2024.marketplace.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class MonthReport {
    private List<String> months;

    public static String toJson(List<String> months) throws Exception {
        MonthReport report = new MonthReport(months);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(report);
    }

}
