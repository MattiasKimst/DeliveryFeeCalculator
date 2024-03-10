package com.example.deliveryfeecalculator.service;

import com.example.deliveryfeecalculator.model.WeatherPhenomenonExtraFeeRule;
import com.example.deliveryfeecalculator.repository.WeatherPhenomenonExtraFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherPhenomenonExtraFeeRuleService {

    private final WeatherPhenomenonExtraFeeRuleRepository weatherPhenomenonExtraFeeRuleRepository;

    @Autowired
    public WeatherPhenomenonExtraFeeRuleService(WeatherPhenomenonExtraFeeRuleRepository weatherPhenomenonExtraFeeRuleRepository) {
        this.weatherPhenomenonExtraFeeRuleRepository = weatherPhenomenonExtraFeeRuleRepository;
    }

    /**
     *
     * @return a list of all phenomenon extra fee rules in db
     */
    public List<WeatherPhenomenonExtraFeeRule> getAllWeatherPhenomenonExtraFeeRules() {
        return weatherPhenomenonExtraFeeRuleRepository.findAll();
    }

    /**
     * a method for adding a new phenomenon extra fee rule to db
     * @param vehicle corresponding vehicle
     * @param phenomenon phenomenon name
     * @param fee corresponding fee
     * @return save a new rule to db
     */
    public WeatherPhenomenonExtraFeeRule createWeatherPhenomenonExtraFeeRule(String vehicle, String phenomenon, double fee) {
        WeatherPhenomenonExtraFeeRule weatherPhenomenonExtraFeeRule = new WeatherPhenomenonExtraFeeRule();
        weatherPhenomenonExtraFeeRule.setVehicle(vehicle);
        weatherPhenomenonExtraFeeRule.setPhenomenon(phenomenon);
        weatherPhenomenonExtraFeeRule.setFee(fee);
        return weatherPhenomenonExtraFeeRuleRepository.save(weatherPhenomenonExtraFeeRule);
    }

    /**
     * a method for deleting a phenomenon rule
     * @param id id of a rule we want to delete
     */
    public void deleteWeatherPhenomenonExtraFeeRule(Long id) {
        if (!weatherPhenomenonExtraFeeRuleRepository.existsById(id)) {
            // Return false if the rule with the given ID does not exist
            return;
        }
        weatherPhenomenonExtraFeeRuleRepository.deleteById(id);
    }
}
