package com.example.deliveryfeecalculator;

import com.example.deliveryfeecalculator.service.WeatherDataImportService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class DeliveryFeeCalculatorApplication {

    @Autowired
    private WeatherDataImportService weatherDataImportService;

    public static void main(String[] args) {
        SpringApplication.run(DeliveryFeeCalculatorApplication.class, args);
    }

    // annotation to trigger the import process when the application context is initialized.
    @PostConstruct
    @Scheduled(cron = "0 1 * * * *") // Cron expression: run every hour, 15 minutes after the hour
    public void importWeatherDataOnStartup() {
        weatherDataImportService.importWeatherData();
    }
}

