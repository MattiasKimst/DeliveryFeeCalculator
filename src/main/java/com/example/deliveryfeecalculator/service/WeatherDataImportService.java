package com.example.deliveryfeecalculator.service;

import com.example.deliveryfeecalculator.model.Station;
import com.example.deliveryfeecalculator.model.WeatherData;
import com.example.deliveryfeecalculator.model.Observations;
import com.example.deliveryfeecalculator.repository.WeatherDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;

@Service
public class WeatherDataImportService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataImportService.class);
    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Scheduled(cron = "0 1 * * * *") // Cron expression: run every hour, 15 minutes after the hour
    public void importWeatherData() {
        // URL to Ilmateenistus API endpoint
        String apiUrl = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

        // instance of WebClient to make HTTP requests
        WebClient webClient = WebClient.create();
        logger.info("webclient created");

        // Send a GET request to the weather portal's API and retrieve the XML response
        String xmlResponse = webClient.get().uri(apiUrl).retrieve().bodyToMono(String.class).block();
        logger.info("xmlResponse to Ilmateenistus api sent");

        // Parse the XML response using JAXB
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Observations.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Observations observations = (Observations) unmarshaller.unmarshal(new StringReader(xmlResponse));

            // Access the list of stations and process them as needed
            List<Station> stations = observations.getStations();
            for (Station station : stations) {
                // Extract the required data fields from xml
                String stationName = station.getName();
                String wmoCode = station.getWmocode();
                Double airTemperature = station.getAirtemperature();
                Double windSpeed = station.getWindspeed();
                String phenomenon = station.getPhenomenon();


                // Create a new instance of WeatherData and set its parameters according to extracted data from xml
                WeatherData weatherData = new WeatherData();
                weatherData.setStationName(stationName);
                weatherData.setWmoCode(wmoCode);
                weatherData.setAirTemperature(airTemperature);
                weatherData.setWindSpeed(windSpeed);
                weatherData.setWeatherPhenomenon(phenomenon);
                weatherData.setTimestamp();

                //insert to database
                // We only want to put WeatherData instance to database, if the station is Tallinn, Tartu or Pärnu
                if (stationName.equals("Tallinn-Harku") || stationName.equals("Tartu-Tõravere") || stationName.equals("Pärnu")) {
                    weatherDataRepository.save(weatherData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle parsing or database insertion errors
        }
    }
}
