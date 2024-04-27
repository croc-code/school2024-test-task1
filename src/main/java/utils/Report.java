package utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Report {
    @JsonProperty("months")
    private List<String> months;

    public Report(List<String> months) {
        this.months = months;
    }
}
