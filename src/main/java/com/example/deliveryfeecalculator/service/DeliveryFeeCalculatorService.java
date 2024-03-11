package com.example.deliveryfeecalculator.service;

import com.example.deliveryfeecalculator.model.*;
import com.example.deliveryfeecalculator.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class DeliveryFeeCalculatorService {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeCalculatorService.class);

    @Autowired
    private WeatherDataRepository weatherDataRepository;


    /**
     * Calculates the delivery fee based on the given city and vehicle type.
     *
     * @param city The name of the city of delivery.
     * @param vehicleType The type of vehicle used for delivery.
     * @param date        Additional parameter, by default null, if provided correctly, fee for that time in past will be calculated
     * @return The calculated delivery fee.
     */

    public double calculateDeliveryFee(String city, String vehicleType, String date) {

        //The city names differ from station names that we use in weather db, to make a query we need to map cities to station names
        String stationName = switch (city) {
            case "Tallinn" -> "Tallinn-Harku";
            case "Tartu" -> "Tartu-Tõravere";
            case "Pärnu" -> "Pärnu";
            default -> throw new IllegalArgumentException("Invalid input for city");
        };

        LocalDateTime dateTime = null;
        //if date isn't null, then date was provided in request
        if (date != null) {
            logger.info("Trying to parse date");
            try {
                dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
                logger.info("Parsed date " + dateTime);
            } catch (
                    Exception e) {//we expect the date to be in format YYYY-MM-DDTHH:MM:SS, otherwise parsing fails and we will continue calculating with latest timestamp
                logger.info("Parsing dateTime failed, will throw an exception");
                throw new IllegalArgumentException("Invalid input for datetime");
            }
        }


        WeatherData weatherData = null;
        //fetch latest data of the station from database
        if (dateTime == null) {//additional datetime parameter not provided or incorrect
            logger.info("trying to fetch latest weatherData from db");
            weatherData = weatherDataRepository.findLatestByStationName(stationName);
            logger.info("latest weather data fetched from db with timestamp: " + weatherData.getTimestamp());
        } else {//if additional datetime parameter was provided and was of correct form
            logger.info("trying to fetch latest weatherData before provided timestamp from db");
            weatherData = weatherDataRepository.findLatestBeforeDateTime(stationName, dateTime);
            logger.info("weather data fetched from db with timestamp: " + weatherData.getTimestamp());
        }

        //calculate base fee
        double baseFee = calculateBaseFee(stationName, vehicleType);
        logger.info("calculated base fee: " + baseFee);

        //calculate extra fee
        double extraFees = calculateExtraFees(weatherData, vehicleType);
        logger.info("calculated extra fees: " + extraFees);


        // return delivery fee, which is the sum of base fee and extra fee
        return (baseFee + extraFees);
    }


    /**
     * Calculates the base delivery fee based on the given city and vehicle type
     * according to the rules fetched from db
     *
     * @param stationName    The name of the weather station corresponding to the city
     * @param vehicleType The type of vehicle used for delivery.
     * @return The calculated base delivery fee.
     */
    @Autowired
    private BaseFeeRuleRepository baseFeeRuleRepository;

    public double calculateBaseFee(String stationName, String vehicleType) {

        // Retrieve all base fee rules from the database
        List<BaseFeeRule> baseFeeRules = baseFeeRuleRepository.findAll();
        // Iterate through the rules and find the one that matches the city and vehicle type
        for (BaseFeeRule rule : baseFeeRules) { //lowercase both comparable strings
            if (rule.getStation().equalsIgnoreCase(stationName) && rule.getVehicle().equalsIgnoreCase(vehicleType)) {
                return rule.getFee();//there might be more than one suitable rule, we use only the first one for calculation
            }
        }
        // If no matching rule is found, throw an exception
        throw new IllegalArgumentException("No matching base fee rule found for provided city/vehicle");

    }


    /**
     * Calculates the extra delivery fees based on the given weather data and vehicle type
     * according to the business rules in db
     *
     * @param weatherData The weather data used for calculating extra fees.
     * @param vehicleType The type of vehicle used for delivery.
     * @return The calculated extra delivery fees.
     * @throws IllegalArgumentException if the vehicle type usage is forbidden based on weather conditions.
     */

    @Autowired
    private TemperatureExtraFeeRuleRepository temperatureExtraFeeRuleRepository;
    @Autowired
    private WindSpeedExtraFeeRuleRepository windSpeedExtraFeeRuleRepository;
    @Autowired
    private WeatherPhenomenonExtraFeeRuleRepository weatherPhenomenonExtraFeeRuleRepository;

    public double calculateExtraFees(WeatherData weatherData, String vehicleType) {

        //we will increase extraFee by each fulfilled condition defined in business rules
        double extraFee = 0.0;

        //iterate over temperature extra fee rules, if rule matches, increase extraFee by defined fee
        //may match many rules
        List<TemperatureExtraFeeRule> temperatureRules = temperatureExtraFeeRuleRepository.findAll();
        for (TemperatureExtraFeeRule rule : temperatureRules) {
            if (rule.getVehicle().equals(vehicleType) && weatherData.getAirTemperature() >= rule.getMinTemperature() && weatherData.getAirTemperature() <= rule.getMaxTemperature()) {
                extraFee += rule.getFee();
            }
        }
        //iterate over windspeed extra fee rules, if rule matches, increase extraFee by defined fee
        //may match many rules
        List<WindSpeedExtraFeeRule> windSpeedRules = windSpeedExtraFeeRuleRepository.findAll();
        for (WindSpeedExtraFeeRule rule : windSpeedRules) {
            if (rule.getVehicle().equals(vehicleType) && weatherData.getWindSpeed() >= rule.getMinWindSpeed() && weatherData.getWindSpeed() <= rule.getMaxWindSpeed()) {
                extraFee += rule.getFee();
            }
        }

        //iterate over phenomenon extra fee rules, if rule matches, increase extraFee by defined fee
        //may match many rules
        List<WeatherPhenomenonExtraFeeRule> phenomenonRules = weatherPhenomenonExtraFeeRuleRepository.findAll();
        for (WeatherPhenomenonExtraFeeRule rule : phenomenonRules) {
            if (rule.getVehicle().equals(vehicleType) && weatherData.getWeatherPhenomenon().toLowerCase().contains(rule.getPhenomenon().toLowerCase())) {
                extraFee += rule.getFee();
            }
        }

        return extraFee;
}
}
