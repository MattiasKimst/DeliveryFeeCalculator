package com.example.deliveryfeecalculator.service;


import com.example.deliveryfeecalculator.model.WeatherData;
import com.example.deliveryfeecalculator.repository.WeatherDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;


@Service
public class WeatherDataImportService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataImportService.class);

    @Autowired
    private WeatherDataRepository weatherDataRepository;


    /**
     * Imports weather data from the Ilmateenistus API.
     * Retrieves XML response from the API endpoint, parses it using DOM,
     * and inserts relevant weather data into the database.
     * @throws Exception if parsing or database insertion fails.
     */
    public void importWeatherData() {

        // URL to Ilmateenistus API endpoint
        String apiUrl = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";


        // instance of WebClient to make HTTP requests
        WebClient webClient = WebClient.create();
        logger.info("Webclient created");


        // Send a GET request to the weather portal's API and retrieve the XML response
        String xmlResponse = webClient.get().uri(apiUrl).retrieve().bodyToMono(String.class).block();
        logger.info("xmlResponse received");


        // Parse the XML response using DOM
        logger.info("Trying to parse xml");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlResponse)));

            // Access the root element of the document
            Element root = document.getDocumentElement();

            // Access the list of station elements
            NodeList stationList = root.getElementsByTagName("station");

            // Process each station element
            for (int i = 0; i < stationList.getLength(); i++) {

                Element stationElement = (Element) stationList.item(i);

                // Extract data fields from the 'station' element
                String stationName = stationElement.getElementsByTagName("name").item(0).getTextContent();

                //we are only interested in weather stations in Tallinn, Tartu or Pärnu
                //if we begin to extract any other station's data we cancel it to avoid unnecessary work
                if (!(stationName.equals("Tallinn-Harku") || stationName.equals("Tartu-Tõravere") || stationName.equals("Pärnu"))) {
                    continue;
                }
                String wmoCode = stationElement.getElementsByTagName("wmocode").item(0).getTextContent();
                double airTemperature = Double.parseDouble(stationElement.getElementsByTagName("airtemperature").item(0).getTextContent());
                double windSpeed = Double.parseDouble(stationElement.getElementsByTagName("windspeed").item(0).getTextContent());
                String phenomenon = stationElement.getElementsByTagName("phenomenon").item(0).getTextContent();
                logger.info("Parsed info: " + stationName + ", " + wmoCode + ", " + airTemperature + ", " + windSpeed + ", " + phenomenon);


                // Create a new instance of WeatherData and set its parameters
                WeatherData weatherData = new WeatherData();
                weatherData.setStationName(stationName);
                weatherData.setWmoCode(wmoCode);
                weatherData.setAirTemperature(airTemperature);
                weatherData.setWindSpeed(windSpeed);
                weatherData.setWeatherPhenomenon(phenomenon);
                weatherData.setTimestamp();

                // Insert weather data into the database if necessary
                weatherDataRepository.save(weatherData);

                logger.info("Parsing xml successful, saved to database");
            }
        } catch (Exception e) {
            logger.info("Parsing xml or saving to database failed");
            e.printStackTrace();
        }
    }
}
