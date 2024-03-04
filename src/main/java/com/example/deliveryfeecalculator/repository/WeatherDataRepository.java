package com.example.deliveryfeecalculator.repository;

import com.example.deliveryfeecalculator.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;


//methods for interacting with the database, such as saving, updating, deleting, and querying entities

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    // Custom query methods if needed
}

