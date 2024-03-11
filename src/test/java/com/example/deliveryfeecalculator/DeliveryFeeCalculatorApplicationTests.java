package com.example.deliveryfeecalculator;

import com.example.deliveryfeecalculator.model.WeatherData;
import com.example.deliveryfeecalculator.repository.WeatherDataRepository;
import com.example.deliveryfeecalculator.service.DeliveryFeeCalculatorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TODO test adding, deleting a rule and calculating fee according to rules added
 */
@SpringBootTest
class DeliveryFeeCalculatorApplicationTests {


    @MockBean
    private WeatherDataRepository weatherDataRepository;
    @Autowired
    private DeliveryFeeCalculatorService deliveryFeeCalculatorService;

    @Test
    void contextLoads() {
    }


    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void testGetBaseRules() {

        // Create the URL for the API endpoint, if the server port is 8080
        String apiUrl = "http://localhost:8080/base/getRules";

        // Send a GET request to the endpoint
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        // Verify that the response status code is OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void testGetTemperatureExtraRules() {

        // Create the URL for the API endpoint, if the server port is 8080
        String apiUrl = "http://localhost:8080/temp/getRules";

        // Send a GET request to the endpoint
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        // Verify that the response status code is OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void testGetWindSpeedExtraRules() {

        // Create the URL for the API endpoint, if the server port is 8080
        String apiUrl = "http://localhost:8080/wind/getRules";

        // Send a GET request to the endpoint
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        // Verify that the response status code is OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void testGetPhenomenonExtraRules() {

        // Create the URL for the API endpoint, if the server port is 8080
        String apiUrl = "http://localhost:8080/phenomenon/getRules";

        // Send a GET request to the endpoint
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        // Verify that the response status code is OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
