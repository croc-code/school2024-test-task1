package edu.croc.analytics;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class AnalyticsApplication implements CommandLineRunner {
    private final ReportService reportService;

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        String report = reportService.generateReport("format.json");
        System.out.println(report);
    }
}
