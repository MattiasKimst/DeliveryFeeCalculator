package com.example.deliveryfeecalculator.service;


import com.example.deliveryfeecalculator.model.TemperatureExtraFeeRule;
import com.example.deliveryfeecalculator.repository.TemperatureExtraFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemperatureExtraFeeRuleService {

    private final TemperatureExtraFeeRuleRepository temperatureExtraFeeRuleRepository;

    @Autowired
    public TemperatureExtraFeeRuleService(TemperatureExtraFeeRuleRepository temperatureExtraFeeRuleRepository) {
        this.temperatureExtraFeeRuleRepository = temperatureExtraFeeRuleRepository;
    }

    public List<TemperatureExtraFeeRule> getAllTemperatureExtraFeeRules() {
        return temperatureExtraFeeRuleRepository.findAll();
    }

    public Optional<TemperatureExtraFeeRule> getTemperatureExtraFeeRuleById(Long id) {
        return temperatureExtraFeeRuleRepository.findById(id);
    }

    public TemperatureExtraFeeRule createTemperatureExtraFeeRule(String vehicle, double minTemperature, double maxTemperature, double fee) {
        TemperatureExtraFeeRule temperatureExtraFeeRule = new TemperatureExtraFeeRule();
        temperatureExtraFeeRule.setVehicle(vehicle);
        temperatureExtraFeeRule.setMinTemperature(minTemperature);
        temperatureExtraFeeRule.setMaxTemperature(maxTemperature);
        temperatureExtraFeeRule.setFee(fee);
        return temperatureExtraFeeRuleRepository.save(temperatureExtraFeeRule);
    }

    public boolean deleteTemperatureExtraFeeRule(Long id) {
        if (!temperatureExtraFeeRuleRepository.existsById(id)) {
            // Return false if the rule with the given ID does not exist
            return false;
        }
        temperatureExtraFeeRuleRepository.deleteById(id);
        return true;
    }
}
