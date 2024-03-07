package com.example.deliveryfeecalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@SpringBootTest
class DeliveryFeeCalculatorApplicationTests {

    @Test
    void contextLoads() {
    }



    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void testCalculateDeliveryFeeEndpointTallinnCar() {

        // Create the URL for the API endpoint, if the server port is 8080
        String apiUrl = "http://localhost:8080/calculateDeliveryFee?stationName=Tallinn&vehicleType=Car";

        // Send a GET request to the API endpoint
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        // Verify that the response status code is OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the response body contains the expected result
        String expectedResponse = "{\"success\":true,\"message\":\"Delivery fee calculated successfully\",\"data\":4.0}";
        assertEquals(expectedResponse, response.getBody());
    }


}
