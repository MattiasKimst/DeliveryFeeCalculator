package com.example.deliveryfeecalculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeliveryFeeCalculatorApplication {
    private static final Logger logger
            = LoggerFactory.getLogger(DeliveryFeeCalculatorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DeliveryFeeCalculatorApplication.class, args);
        logger.info("Example log");
    }

}
