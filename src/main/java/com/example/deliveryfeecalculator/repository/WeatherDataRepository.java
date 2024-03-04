package com.example.deliveryfeecalculator.repository;

import com.example.deliveryfeecalculator.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;


//methods for interacting with the database, such as saving, updating, deleting, and querying entities

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    //for calculating fees we need to find the latest data for the specified location
    @Query("SELECT wd FROM WeatherData wd WHERE wd.city = :city ORDER BY wd.timestamp DESC")
    WeatherData findLatestByCity(@Param("city") String city);
}
