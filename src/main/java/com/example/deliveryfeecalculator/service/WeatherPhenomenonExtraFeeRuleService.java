package com.example.deliveryfeecalculator.service;

import com.example.deliveryfeecalculator.model.WeatherPhenomenonExtraFeeRule;
import com.example.deliveryfeecalculator.repository.WeatherPhenomenonExtraFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherPhenomenonExtraFeeRuleService {

    private final WeatherPhenomenonExtraFeeRuleRepository weatherPhenomenonExtraFeeRuleRepository;

    @Autowired
    public WeatherPhenomenonExtraFeeRuleService(WeatherPhenomenonExtraFeeRuleRepository weatherPhenomenonExtraFeeRuleRepository) {
        this.weatherPhenomenonExtraFeeRuleRepository = weatherPhenomenonExtraFeeRuleRepository;
    }

    public List<WeatherPhenomenonExtraFeeRule> getAllWeatherPhenomenonExtraFeeRules() {
        return weatherPhenomenonExtraFeeRuleRepository.findAll();
    }

    public Optional<WeatherPhenomenonExtraFeeRule> getWeatherPhenomenonExtraFeeRuleById(Long id) {
        return weatherPhenomenonExtraFeeRuleRepository.findById(id);
    }

    public WeatherPhenomenonExtraFeeRule createWeatherPhenomenonExtraFeeRule(String vehicle, String phenomenon, double fee) {
        WeatherPhenomenonExtraFeeRule weatherPhenomenonExtraFeeRule = new WeatherPhenomenonExtraFeeRule();
        weatherPhenomenonExtraFeeRule.setVehicle(vehicle);
        weatherPhenomenonExtraFeeRule.setPhenomenon(phenomenon);
        weatherPhenomenonExtraFeeRule.setFee(fee);
        return weatherPhenomenonExtraFeeRuleRepository.save(weatherPhenomenonExtraFeeRule);
    }

    public boolean deleteWeatherPhenomenonExtraFeeRule(Long id) {
        if (!weatherPhenomenonExtraFeeRuleRepository.existsById(id)) {
            // Return false if the rule with the given ID does not exist
            return false;
        }
        weatherPhenomenonExtraFeeRuleRepository.deleteById(id);
        return true;
    }
}
