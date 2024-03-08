package com.example.deliveryfeecalculator.service;

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


    /**
     * Calculates the delivery fee based on the given city and vehicle type.
     *
     * @param city        The name of the city for delivery.
     * @param vehicleType The type of vehicle used for delivery.
     * @return The calculated delivery fee.
     */

    public double calculateDeliveryFee(String city, String vehicleType) {

        //The city names differ from station names that we use in db, to make a query we need to map cities to station names
        String stationName = switch (city) {
            case "Tallinn" -> "Tallinn-Harku";
            case "Tartu" -> "Tartu-Tõravere";
            case "Pärnu" -> "Pärnu";
            default -> throw new IllegalArgumentException("Invalid input for city");
        };


        //fetch latest data of the station from database
        logger.info("trying to fetch latest weatherData from db");
        WeatherData weatherData = weatherDataRepository.findLatestByStationName(stationName);
        logger.info("latest weather data fetched from db with timestamp: " + weatherData.getTimestamp());


        //calculate base fee
        double baseFee = calculateBaseFee(city, vehicleType);
        logger.info("calculated base fee: " + baseFee);

        //calculate extra fee
        double extraFees = calculateExtraFees(weatherData, vehicleType);
        logger.info("calculated extra fees: " + extraFees);


        // return delivery fee, which is the sum of base fee and extra fee
        return (baseFee + extraFees);
    }


    /**
     * Calculates the base delivery fee based on the given city and vehicle type
     * according to the defined business rules
     *
     * @param city        The name of the city for delivery.
     * @param vehicleType The type of vehicle used for delivery.
     * @return The calculated base delivery fee.
     */
    private static double calculateBaseFee(String city, String vehicleType) {

        // Business rules to calculate regional base fee (RBF):
        switch (city) {
            case "Tallinn":
                switch (vehicleType) {
                    case "Car":
                        return 4.0;
                    case "Scooter":
                        return 3.5;
                    case "Bike":
                        return 3.0;
                    default:
                        throw new IllegalArgumentException("Invalid input for vehicle");

                }
            case "Tartu":
                switch (vehicleType) {
                    case "Car":
                        return 3.5;
                    case "Scooter":
                        return 3.0;
                    case "Bike":
                        return 2.5;
                    default:
                        throw new IllegalArgumentException("Invalid input for vehicle");
                }
            case "Pärnu":
                switch (vehicleType) {
                    case "Car":
                        return 3.0;
                    case "Scooter":
                        return 2.5;
                    case "Bike":
                        return 2.0;
                    default:
                        throw new IllegalArgumentException("Invalid input for vehicle");
                }
        }

        return 0;//if input city and vehicle wasn't mapped to any cases (shouldn't happen
        //  because in case of invalid city or vehicle an exception is thrown before
    }


    /**
     * Calculates the extra delivery fees based on the given weather data and vehicle type
     * according to the defined business rules
     *
     * @param weatherData The weather data used for calculating extra fees.
     * @param vehicleType The type of vehicle used for delivery.
     * @return The calculated extra delivery fees.
     * @throws IllegalArgumentException if the vehicle type usage is forbidden based on weather conditions.
     */
    private static double calculateExtraFees(WeatherData weatherData, String vehicleType) {

        //we will increase extraFee by each fulfilled condition defined in business rules
        double extraFee = 0.0;

        if (("Scooter".equals(vehicleType) || "Bike".equals(vehicleType)) && weatherData != null) {

            // Extra fee based on air temperature (ATEF) in a specific city is paid in case Vehicle type =
            //Scooter or Bike
            double airTemperature = weatherData.getAirTemperature();
            logger.info("Airtemperature: " + airTemperature);
            //Air temperature is less than -10̊ C, then ATEF = 1 €
            if (airTemperature < -10) {
                extraFee += 1.0;
            }
            //Air temperature is between -10̊ C and 0̊ C, then ATEF = 0,5 €
            else if (airTemperature >= -10 && airTemperature < 0) {
                extraFee += 0.5;
            }


            // Extra fee based on wind speed (WSEF) in a specific city is paid in case Vehicle type = Bike
            double windSpeed = weatherData.getWindSpeed();
            logger.info("Wind speed " + windSpeed);
            if ("Bike".equals(vehicleType)) {
                //Wind speed is between 10 m/s and 20 m/s, then WSEF = 0,5 €
                if (windSpeed >= 10 && windSpeed <= 20) {
                    extraFee += 0.5;
                }
                //In case of wind speed is greater than 20 m/s, then the error message “Usage of selected vehicle
                //type is forbidden” has to be given
                else if (windSpeed > 20) {
                    throw new IllegalArgumentException("Usage of selected vehicle type is forbidden");
                }
            }


            // Extra fee based on weather phenomenon (WPEF) in a specific city is paid in case Vehicle
            //type = Scooter or Bike
            String weatherPhenomenon = weatherData.getWeatherPhenomenon();
            logger.info("Weather phenomenon " + weatherPhenomenon);
            //Weather phenomenon is related to snow or sleet, then WPEF = 1 €
            if ("snow".equalsIgnoreCase(weatherPhenomenon) || "sleet".equalsIgnoreCase(weatherPhenomenon)) {
                extraFee += 1.0;
            }
            //Weather phenomenon is related to rain, then WPEF = 0,5 €
            else if ("rain".equalsIgnoreCase(weatherPhenomenon)) {
                extraFee += 0.5;
            }
            //In case the weather phenomenon is glaze, hail, or thunder, then the error message “Usage of
            //selected vehicle type is forbidden” has to be given
            else if ("glaze".equalsIgnoreCase(weatherPhenomenon) || "hail".equalsIgnoreCase(weatherPhenomenon) || "thunder".equalsIgnoreCase(weatherPhenomenon)) {
                throw new IllegalArgumentException("Usage of selected vehicle type is forbidden");
            }
        }

        return extraFee;
    }
}
