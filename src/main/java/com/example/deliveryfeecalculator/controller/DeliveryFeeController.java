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

    //getMapping specifies that it handles GET requests to the /calculateDeliveryFee endpoint.
    @GetMapping("/calculateDeliveryFee")
    public ApiResponse<Double> calculateDeliveryFee(
            @RequestParam String stationName,
            @RequestParam String vehicleType
    ) {
        logger.info("calculateDeliveryFee call received");
        try { //call the calculateDeliveryFee method to calculate the delivery fee.

            double deliveryFee = deliveryFeeCalculatorService.calculateDeliveryFee(stationName, vehicleType);
            logger.info("deliveryfee "+deliveryFee+" calculated");
            //successfull calculation
            return new ApiResponse<>(true, "Delivery fee calculated successfully", deliveryFee);
        }//invalid input or weatherconditions
        catch (IllegalArgumentException ex) {
            logger.info("illega argument for get request");
            return new ApiResponse<>(false, ex.getMessage(), null);
        }//anu other exception handling
        catch (Exception ex) {
            logger.info("Error while calculating fee");
            return new ApiResponse<>(false, "An error occurred while calculating delivery fee", null);
        }
    }
}
