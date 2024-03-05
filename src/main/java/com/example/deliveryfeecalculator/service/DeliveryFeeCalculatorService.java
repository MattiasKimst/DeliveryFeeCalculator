package com.example.deliveryfeecalculator.service;

import com.example.deliveryfeecalculator.DeliveryFeeCalculatorApplication;
import com.example.deliveryfeecalculator.model.WeatherData;
import com.example.deliveryfeecalculator.repository.WeatherDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeliveryFeeCalculatorService {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeCalculatorService.class);

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    public double calculateDeliveryFee(String city, String vehicleType) {


        logger.info("calculateDeliveryFee successfully called");

        // Retrieve latest entry for weather data of the given city from the database

        //The city names differ from station names that we use in db, to make a query we need to map cities to station names
        String stationName = switch (city) {
            case "Tallinn" -> "Tallinn-Harku";
            case "Tartu" -> "Tartu-Tõravere";
            case "Pärnu" -> "Pärnu";
            default -> city;
        };

        //collect latest data of the station from Ilmateenistus
        WeatherData weatherData = weatherDataRepository.findLatestByStationName(stationName);
        logger.info("latest weather data fetched from db with timestamp"+weatherData.getTimestamp());




        double baseFee = calculateBaseFee(city, vehicleType);
        logger.info("calculated basefee: " + baseFee);
        double extraFee = calculateExtraFees(weatherData, vehicleType);
        logger.info("extrafee calculated: "+extraFee);



        //deliveryfee is the sum of basefee and extrafee
        return (baseFee + extraFee);
    }

    private double calculateBaseFee(String city, String vehicleType) {


        // Implementation of business rules for calculateing the regional base fee
        switch (city) {
            case "Tallinn":
                switch (vehicleType) {
                    case "Car":
                        return 4.0;
                    case "Scooter":
                        return 3.5;
                    case "Bike":
                        return 3.0;
                }
            case "Tartu":
                switch (vehicleType) {
                    case "Car":
                        return 3.5;
                    case "Scooter":
                        return 3.0;
                    case "Bike":
                        return 2.5;
                }
            case "Pärnu":
                switch (vehicleType) {
                    case "Car":
                        return 3.0;
                    case "Scooter":
                        return 2.5;
                    case "Bike":
                        return 2.0;
                }
            default:
                return 0.0; // if invalid city or vehicle type return 0
        }
    }

    private double calculateExtraFees(WeatherData weatherData, String vehicleType) {
        // Implementation of business rules for calculateing extra fees based on weather conditions
        double extraFee = 0.0;

        if (("Scooter".equals(vehicleType) || "Bike".equals(vehicleType)) && weatherData != null) {
            // Calculate extra fee based on air temperature (ATEF)
            double airTemperature = weatherData.getAirTemperature();
            logger.info("Airtemoerature: "+ airTemperature);
            if (airTemperature < -10) {
                extraFee += 1.0;
            } else if (airTemperature >= -10 && airTemperature < 0) {
                extraFee += 0.5;
            }

            // Calculate extra fee based on wind speed (WSEF)
            double windSpeed = weatherData.getWindSpeed();
            if ("Bike".equals(vehicleType)) {
                if (windSpeed >= 10 && windSpeed <= 20) {
                    extraFee += 0.5;
                } else if (windSpeed > 20) {
                    // Error message: "Usage of selected vehicle type is forbidden"
                    throw new IllegalArgumentException("Usage of selected vehicle type is forbidden");
                }
            }

            // Calculate extra fee based on weather phenomenon (WPEF)
            String weatherPhenomenon = weatherData.getWeatherPhenomenon();
            if ("Scooter".equals(vehicleType) || "Bike".equals(vehicleType)) {
                if ("snow".equalsIgnoreCase(weatherPhenomenon) || "sleet".equalsIgnoreCase(weatherPhenomenon)) {
                    extraFee += 1.0;
                } else if ("rain".equalsIgnoreCase(weatherPhenomenon)) {
                    extraFee += 0.5;
                } else if ("glaze".equalsIgnoreCase(weatherPhenomenon) || "hail".equalsIgnoreCase(weatherPhenomenon) || "thunder".equalsIgnoreCase(weatherPhenomenon)) {
                    // Error message: "Usage of selected vehicle type is forbidden"
                    throw new IllegalArgumentException("Usage of selected vehicle type is forbidden");
                }
            }
        }

        return extraFee;
    }
}
