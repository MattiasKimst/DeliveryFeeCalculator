package com.example.deliveryfeecalculator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.Timestamp;


//Entity classes represent the objects that will be stored in the database
@Entity
public class WeatherData {

    //weather data table in database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationName;
    private String wmoCode;
    private double airTemperature;
    private double windSpeed;
    private String weatherPhenomenon;
    private Timestamp timestamp;


}

