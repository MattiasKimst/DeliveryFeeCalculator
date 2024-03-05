package com.example.deliveryfeecalculator.repository;

import com.example.deliveryfeecalculator.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



//methods for interacting with the database, such as saving, updating, deleting, and querying entities

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {


    //get weather data for specified station that has the newest timestamp
    @Query("SELECT wd FROM WeatherData wd WHERE wd.timestamp = (SELECT MAX(wd2.timestamp) FROM WeatherData wd2 WHERE wd2.stationName = :stationName)")
    WeatherData findLatestByStationName(@Param("stationName") String stationName);

}
