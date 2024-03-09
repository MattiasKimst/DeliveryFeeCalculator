package com.example.deliveryfeecalculator.controller;

import com.example.deliveryfeecalculator.service.DeliveryFeeCalculatorService;
import com.example.deliveryfeecalculator.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryFeeController {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeCalculatorService.class);

    @Autowired
    private DeliveryFeeCalculatorService deliveryFeeCalculatorService;


    /**
     GET requests to the /calculateDeliveryFee
     Calculates the delivery fee for a given station(city) and vehicle type.
     @param stationName The name of the station for delivery.
     @param vehicleType The type of vehicle used for delivery.
     @param datetime    Optional parameter for moment of delivery in past
     @return An ApiResponse containing the calculated delivery fee if successful,
     with a success message, or an error if response is unsuccessful.
     */
    @GetMapping("/calculateDeliveryFee") //datetime parameter is not required, if not provided, its value will be null
    public ApiResponse<Double> calculateDeliveryFee(@RequestParam String stationName, @RequestParam String vehicleType, @RequestParam(value = "datetime", required = false) String datetime) {
        logger.info("calculateDeliveryFee call received");
        try {
            //call the calculateDeliveryFee method to calculate the delivery fee.
            double deliveryFee = deliveryFeeCalculatorService.calculateDeliveryFee(stationName, vehicleType, datetime);
            logger.info("deliveryfee " + deliveryFee + " calculated");

            //if calculation was successful
            return new ApiResponse<>(true, "Delivery fee calculated successfully", deliveryFee);

        }

        //invalid input or weather conditions
        catch (IllegalArgumentException ex) {
            logger.info("illegal argument for get request");
            return new ApiResponse<>(false, ex.getMessage(), null);
        }

        //any other exception handling
        catch (Exception ex) {
            logger.info("Error while calculating fee");
            return new ApiResponse<>(false, "An error occurred while calculating delivery fee", null);
        }
    }
}
