package com.example.deliveryfeecalculator;

import com.example.deliveryfeecalculator.service.WeatherDataImportService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class DeliveryFeeCalculatorApplication {

    @Autowired
    private WeatherDataImportService weatherDataImportService;

    public static void main(String[] args) {
        SpringApplication.run(DeliveryFeeCalculatorApplication.class, args);
    }

    //PostConstruct annotation is to trigger the import process when the application context is initialized.
    @PostConstruct
    @Scheduled(cron = "1 * * * * *") // later change to 15 minutes!!!
    public void importWeatherDataOnStartup() {
        weatherDataImportService.importWeatherData();
    }
}

