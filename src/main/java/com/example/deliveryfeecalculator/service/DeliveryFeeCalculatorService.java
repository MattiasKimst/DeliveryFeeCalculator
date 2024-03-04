import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryFeeCalculatorService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    public double calculateDeliveryFee(String city, String vehicleType) {
        // Retrieve weather data for the given city from the database
        WeatherData weatherData = weatherDataRepository.findLatestByCity(city);

        double baseFee = calculateRegionalBaseFee(city, vehicleType);
        double extraFee = calculateExtraFees(weatherData, vehicleType);

        //Logic to calculate deliveryfee
        double deliveryFee = 0.0;
        // Implement business rules...

        return deliveryFee;
    }

    private double calculateRegionalBaseFee(String city, String vehicleType) {
        // Implementation of business rules for calculateing the regional base fee
        switch (city) {
            case "Tallinn":
                switch (vehicleType) {
                    case "Car":
                        return 4.0;
                    case "Scooter":
                        return 3.5;
                    case "Bike":
                        return 3.0;
                }
            case "Tartu":
                switch (vehicleType) {
                    case "Car":
                        return 3.5;
                    case "Scooter":
                        return 3.0;
                    case "Bike":
                        return 2.5;
                }
            case "Pärnu":
                switch (vehicleType) {
                    case "Car":
                        return 3.0;
                    case "Scooter":
                        return 2.5;
                    case "Bike":
                        return 2.0;
                }
            default:
                return 0.0; // if invalid city or vehicle type return 0
        }
    }

    private double calculateExtraFees(WeatherData weatherData, String vehicleType) {
        // Implementation of business rules for calculateing extra fees based on weather conditions
        double extraFee = 0.0;

        if (("Scooter".equals(vehicleType) || "Bike".equals(vehicleType)) && weatherData != null) {
            // Calculate extra fee based on air temperature (ATEF)
            double airTemperature = weatherData.getAirTemperature();
            if (airTemperature < -10) {
                extraFee += 1.0;
            } else if (airTemperature >= -10 && airTemperature < 0) {
                extraFee += 0.5;
            }

            // Calculate extra fee based on wind speed (WSEF)
            double windSpeed = weatherData.getWindSpeed();
            if ("Bike".equals(vehicleType)) {
                if (windSpeed >= 10 && windSpeed <= 20) {
                    extraFee += 0.5;
                } else if (windSpeed > 20) {
                    // Error message: "Usage of selected vehicle type is forbidden"
                    throw new IllegalArgumentException("Usage of selected vehicle type is forbidden");
                }
            }

            // Calculate extra fee based on weather phenomenon (WPEF)
            String weatherPhenomenon = weatherData.getWeatherPhenomenon();
            if ("Scooter".equals(vehicleType) || "Bike".equals(vehicleType)) {
                if ("snow".equalsIgnoreCase(weatherPhenomenon) || "sleet".equalsIgnoreCase(weatherPhenomenon)) {
                    extraFee += 1.0;
                } else if ("rain".equalsIgnoreCase(weatherPhenomenon)) {
                    extraFee += 0.5;
                } else if ("glaze".equalsIgnoreCase(weatherPhenomenon) ||
                        "hail".equalsIgnoreCase(weatherPhenomenon) ||
                        "thunder".equalsIgnoreCase(weatherPhenomenon)) {
                    // Error message: "Usage of selected vehicle type is forbidden"
                    throw new IllegalArgumentException("Usage of selected vehicle type is forbidden");
                }
            }
        }

        return extraFee;
    }
}
