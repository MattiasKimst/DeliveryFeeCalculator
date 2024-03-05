package com.example.deliveryfeecalculator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

import java.time.LocalDateTime;


//Entity class, represents the objects that will be stored in the database
//in this case the class represents weather data table in h2 database

@Entity
public class WeatherData {

    //we generate new unique id for every set of weatherData we fetch as we could fetch the same data multiple times
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stationName;
    private String wmoCode;
    private double airTemperature;
    private double windSpeed;
    private String weatherPhenomenon;
    //timestamp is not the one from xml but the time when data was fetches from Ilmateenistus endpoint
    private LocalDateTime timestamp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public String getWmoCode() {
        return wmoCode;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setWmoCode(String wmoCode) {
        this.wmoCode = wmoCode;
    }

    public double getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(double airTemperature) {
        this.airTemperature = airTemperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherPhenomenon() {
        return weatherPhenomenon;
    }

    public void setWeatherPhenomenon(String weatherPhenomenon) {
        this.weatherPhenomenon = weatherPhenomenon;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp() {
        timestamp = LocalDateTime.now();

    }
}

