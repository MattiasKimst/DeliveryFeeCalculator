package com.example.deliveryfeecalculator.service;


import com.example.deliveryfeecalculator.model.TemperatureExtraFeeRule;
import com.example.deliveryfeecalculator.repository.TemperatureExtraFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemperatureExtraFeeRuleService {

    private final TemperatureExtraFeeRuleRepository temperatureExtraFeeRuleRepository;

    @Autowired
    public TemperatureExtraFeeRuleService(TemperatureExtraFeeRuleRepository temperatureExtraFeeRuleRepository) {
        this.temperatureExtraFeeRuleRepository = temperatureExtraFeeRuleRepository;
    }

    /**
     *
     * @return a list of all temperature extra fee rules in db
     */
    public List<TemperatureExtraFeeRule> getAllTemperatureExtraFeeRules() {
        return temperatureExtraFeeRuleRepository.findAll();
    }


    /**
     * method for adding a new temperature extra fee to db
     * @param vehicle vehicle
     * @param minTemperature minimum temperature for the rule to apply
     * @param maxTemperature maximum temperature for the rule to apply
     * @param fee fee when rule applies
     * @return save the rule to db
     */
    public TemperatureExtraFeeRule createTemperatureExtraFeeRule(String vehicle, double minTemperature, double maxTemperature, double fee) {
        TemperatureExtraFeeRule temperatureExtraFeeRule = new TemperatureExtraFeeRule();
        temperatureExtraFeeRule.setVehicle(vehicle);
        temperatureExtraFeeRule.setMinTemperature(minTemperature);
        temperatureExtraFeeRule.setMaxTemperature(maxTemperature);
        temperatureExtraFeeRule.setFee(fee);
        return temperatureExtraFeeRuleRepository.save(temperatureExtraFeeRule);
    }

    /**
     * method for deleting a rule
     * @param id id of a rule we want to delete from db
     */
    public void deleteTemperatureExtraFeeRule(Long id) {
        if (!temperatureExtraFeeRuleRepository.existsById(id)) {
            // Return false if the rule with the given ID does not exist
            return;
        }
        temperatureExtraFeeRuleRepository.deleteById(id);
    }
}
