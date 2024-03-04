import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryFeeController {

    @Autowired
    private DeliveryFeeCalculatorService deliveryFeeCalculatorService;

    @GetMapping("/calculateDeliveryFee")
    public double calculateDeliveryFee(@RequestParam String city, @RequestParam String vehicleType) {
        return deliveryFeeCalculatorService.calculateDeliveryFee(city, vehicleType);
    }
}
