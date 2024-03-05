-- Create the table for weatherdata
CREATE TABLE WeatherData (
                             id BIGINT NOT NULL PRIMARY KEY,
                             station_name VARCHAR(255) NOT NULL,
                             wmo_code VARCHAR(255),
                             air_temperature DOUBLE,
                             wind_speed DOUBLE,
                             weather_phenomenon VARCHAR(255),
                             timestamp TIMESTAMP
);