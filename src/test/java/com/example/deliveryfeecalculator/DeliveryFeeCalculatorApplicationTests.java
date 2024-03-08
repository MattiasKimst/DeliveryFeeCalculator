package com.example.deliveryfeecalculator;

import com.example.deliveryfeecalculator.model.WeatherData;
import com.example.deliveryfeecalculator.repository.WeatherDataRepository;
import com.example.deliveryfeecalculator.service.DeliveryFeeCalculatorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest
class DeliveryFeeCalculatorApplicationTests {


    @MockBean
    private WeatherDataRepository weatherDataRepository;
    @MockBean
    private DeliveryFeeCalculatorService deliveryFeeCalculatorService;

    @Test
    void contextLoads() {
    }


    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void testCalculateDeliveryFeeEndpointTallinnCar() {

        // Create the URL for the API endpoint, if the server port is 8080
        String apiUrl = "http://localhost:8080/calculateDeliveryFee?stationName=Tallinn&vehicleType=Car";

        // Send a GET request to the endpoint
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        // Verify that the response status code is OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the response body contains the expected result
        String expectedResponse = "{\"success\":true,\"message\":\"Delivery fee calculated successfully\",\"data\":4.0}";
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testTartuScooterMinus15Snow() {
        // Mock weather data for Tartu with specific conditions triggering extra fees
        WeatherData tartuWeatherData = new WeatherData();
        tartuWeatherData.setAirTemperature(-15.0); // Air temperature is less than -10̊ C, then ATEF = 1 €
        tartuWeatherData.setWindSpeed(15.0); // Wind speed is between 10 m/s and 20 m/s, then WSEF = 0,5 €
        tartuWeatherData.setWeatherPhenomenon("snow"); // Weather phenomenon is related to snow or sleet, then WPEF = 1 €

        // Mock the repository to return the mocked weather data
        when(weatherDataRepository.findLatestByStationName("Tartu-Tõravere")).thenReturn(tartuWeatherData);

        // Calculate delivery fee for Tartu with a scooter RBF= 3€
        double deliveryFee = deliveryFeeCalculatorService.calculateDeliveryFee("Tartu", "Scooter");

        // Assert the calculated delivery fee
        assertEquals(5.5, deliveryFee); // Expected delivery fee = RBF (3) + ATEF (1) + WSEF (0.5) + WPEF (1)
    }

    @Test
    void testPärnuBikeMinus5Rain() {
        // Mock weather data for Pärnu with specific conditions triggering extra fees
        WeatherData pärnuWeatherData = new WeatherData();
        pärnuWeatherData.setAirTemperature(-5.0); // Air temperature is between -10̊ C and 0̊ C, then ATEF = 0,5 €
        pärnuWeatherData.setWindSpeed(5.0); // wind speed is less than 10m/s, then WSEF = 0€
        pärnuWeatherData.setWeatherPhenomenon("rain"); // Weather phenomenon is related to rain, then WPEF = 0,5 €

        // Mock the repository to return the mocked weather data
        when(weatherDataRepository.findLatestByStationName("Pärnu")).thenReturn(pärnuWeatherData);

        // Calculate delivery fee for pärnu with a scooter RBF= 2.5€
        double deliveryFee = deliveryFeeCalculatorService.calculateDeliveryFee("Pärnu", "Bike");

        // Assert the calculated delivery fee
        assertEquals(3.5, deliveryFee); // Expected delivery fee = RBF (2.5) + ATEF (0.5) + WSEF (0) + WPEF (0.5)
    }

    @Test
    void testTallinnBike25ms() {
        // Mock weather data for Tallinn with specific conditions triggering extra fees
        WeatherData tallinnWeatherData = new WeatherData();
        tallinnWeatherData.setAirTemperature(5.0); //Air temperature is more than 0 C, then ATEF = 0 €
        tallinnWeatherData.setWindSpeed(25.0); // In case of wind speed is greater than 20 m/s, then the error message
        tallinnWeatherData.setWeatherPhenomenon(""); //no weather phenomenon

        // Mock the repository to return the mocked weather data
        when(weatherDataRepository.findLatestByStationName("Tallinn-Harku")).thenReturn(tallinnWeatherData);

        // Assert the calculated delivery fee
        assertThrows(IllegalArgumentException.class, () -> {
            double deliveryFee = deliveryFeeCalculatorService.calculateDeliveryFee("Tallinn", "Scooter");
        });
    }


}
