# Delivery Fee Calculator

Developed according to Java Programming Trial Task by Fujitsu Estonia 2024 by Mattias Kimst

## Overview
The Delivery Fee Calculator is a sub-functionality of a food delivery application that calculates the delivery fee for food couriers based on defined business rules such as regional base fee, vehicle type, and weather conditions. 

## Key Features
1. **Database Management**: Stores and manages weather data, including information such as station name, WMO code, air temperature, wind speed, weather phenomenon, and timestamp of observations.
2. **Scheduled Weather Data Import**: Configurable scheduled task (CronJob) imports weather data from the Estonian Environment Agency's weather portal, ensuring the database is updated with the latest information at regular intervals.
3. **Delivery Fee Calculation**: Implements business rules to calculate the delivery fee based on input parameters from REST interface requests, weather data from the database, and predefined pricing criteria.
4. **RESTful Interface**: Provides a RESTful API endpoint (/calculateDeliveryFee) to request delivery fees based on specified city and vehicle type. Returns the calculated delivery fee or appropriate error messages.

## Technologies Used
- Java 
- Maven
- Spring boot 
- H2 Database 
- RESTful API 
- CronJob task scheduler

## Dependencies
Third-party dependencies are used in the project. Browse the Maven pom.xml file for details of libraries and their versions.

## How to Run

1. Clone the Repository:

```
git clone <repository-url>
```

2. Move to project directory
```
cd <project-directory>
```
3. Build the project

```
mvn clean install
```
4. Run the Application:
```
mvn spring-boot:run
```
This command will start the Spring Boot application. By default, the application will run on port 8080.

Access the Application:
Once the application has started successfully, you can access it in your web browser using the following URL:

http://localhost:8080


