# Delivery Fee Calculator

Developed according to Java Programming Trial Task from Fujitsu Estonia 2024 by Mattias Kimst

## Overview
The Delivery Fee Calculator is a sub-functionality of a food delivery application that calculates the delivery fee for food couriers based on defined business rules such as regional base fee, vehicle type, and weather conditions. 

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
`http://localhost:8080/calculateDeliveryFee?city=<city>&vehicleType=<vehicle>`
To make a GET request with optional datetime parameter, replace <code>&lt;city&gt;</code>, <code>&lt;vehicle&gt;</code> and <code>< yyyy-MM-ddTHH:mm:ss ></code> with one of the cities from valid input space {Tallinn, Tartu, Pärnu}, a vehicle from the list {Car, Scooter, Bike} and a date in provided format like 2024-03-09T10:00:00. `http://localhost:8080/calculateDeliveryFee?city=<city>&vehicleType=<vehicle>&datetime=<yyyy-MM-ddTHH:mm:ss>`

In order to run Junit test, make sure that "Generic H2 (Server)" option is selected from H2 console

## Business rules

Business rules are hard-coded in this version, bonus branch of this project includes functionality to store business rules in database and modify them using POST and DELETE requests.
Business rules to calculate regional base fee (RBF):
- In case City = Tallinn and:
- Vehicle type = Car, then RBF = 4 €
- Vehicle type = Scooter, then RBF = 3,5 €
- Vehicle type = Bike, then RBF = 3 €
- In case City = Tartu and:
- Vehicle type = Car, then RBF = 3,5 €
- Vehicle type = Scooter, then RBF = 3 €
- Vehicle type = Bike, then RBF = 2,5 €
- In case City = Pärnu and:
- Vehicle type = Car, then RBF = 3 €
- Vehicle type = Scooter, then RBF = 2,5 €
- Vehicle type = Bike, then RBF = 2 €

Business rules to calculate extra fees for weather conditions:

Extra fee based on air temperature (ATEF) in a specific city is paid in case Vehicle type =
Scooter or Bike and:
- Air temperature is less than -10̊ C, then ATEF = 1 €
- Air temperature is between -10̊ C and 0̊ C, then ATEF = 0,5 €

Extra fee based on wind speed (WSEF) in a specific city is paid in case Vehicle type = Bike
and:

- Wind speed is between 10 m/s and 20 m/s, then WSEF = 0,5 €
- In case of wind speed is greater than 20 m/s, then the error message “Usage of selected vehicle
type is forbidden” has to be given

Extra fee based on weather phenomenon (WPEF) in a specific city is paid in case Vehicle
type = Scooter or Bike and:

- Weather phenomenon is related to snow or sleet, then WPEF = 1 €
- Weather phenomenon is related to rain, then WPEF = 0,5 €
- In case the weather phenomenon is glaze, hail, or thunder, then the error message “Usage of
selected vehicle type is forbidden” has to be given

## Contact
In case of any questions or problems regarding this project, feel free to conatct mattias.kimst@mail.ee
 



