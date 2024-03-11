# BONUS Delivery Fee Calculator with REST interface for managing rules 

Developed according to Java Programming Trial Task from Fujitsu Estonia 2024 by Mattias Kimst

## Overview
The Delivery Fee Calculator is a sub-functionality of a food delivery application that calculates the delivery fee for food couriers based on defined business rules such as regional base fee, vehicle type, and weather conditions.
This version implements bonus tasks so that rules for base fees and extra fees could be managed (CRUD) through the REST interface

## Key Features
1. **Database Management**: Stores and manages weather data, including information such as station name, WMO code, air temperature, wind speed, weather phenomenon, and timestamp in H2 database (data directory in  project structure).
2. **Scheduled Weather Data Import**:(CronJob) imports weather data from the Estonian Environment Agency's weather portal on 15th minute of every hour
3. **Delivery Fee Calculation**: Implements business rules to calculate the delivery fee based on input parameters from REST interface requests, weather data from the database, and predefined pricing criteria.
4. **RESTful Interface**: Provides a RESTful API endpoint (/calculateDeliveryFee) to request delivery fees based on specified city and vehicle type. Returns the calculated delivery fee or appropriate error messages.
5. **Junit Test coverage** All possible inputs and outputs, endpoint and business logic are covered by junit tests.

## Technologies Used
- Java 
- Maven
- Spring boot 
- H2 Database 
- RESTful API 
- CronJob task scheduler
- Junit tests

## Dependencies
Third-party dependencies are used in the project. Browse the Maven pom.xml file for details of libraries and their versions.

## How to Run

Make sure to use Java 21

This is a maven project. If encountering problems with running this, seek for maven or your IDE docs.

In default configuration, this application runs on port 8080. Visit http://localhost:8080 for welcome page

H2 database console could be accessed at http://localhost:8080/h2-console login credentials are in application.properties file

To make a get request, replace < city > and < vehicle > which you want to calculate the fee for with one of the cities from valid input space {"Tallinn","Tartu","Pärnu"} 
and a vehicle from list {"Car","Scooter","Bike"}. You can test invalid input aswell, which results in response containing
corresponding error message and null value for fee.
http://localhost:8080/calculateDeliveryFee?city=<city>&vehicleType=<vehicle>

In order to run Junit test, make sure that "Generic H2 (Server)" option is selected from H2 console

## Business rules
in this version of application you can add and delete business rules yourself using POST and DELETE methods. Simple forms for submitting those
requests are available on the welcome page http://localhost:8080




