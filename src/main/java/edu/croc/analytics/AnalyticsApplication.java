package edu.croc.analytics;

import edu.croc.analytics.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class AnalyticsApplication implements CommandLineRunner {
    private final ReportService reportService;

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            String report = reportService.generateReport(args[0]);
            System.out.println(report);
        } catch (Exception e) {
            log.error("Error generating report", e);
        }
    }
}
