package com.example.deliveryfeecalculator.service;

import com.example.deliveryfeecalculator.model.WindSpeedExtraFeeRule;
import com.example.deliveryfeecalculator.repository.WindSpeedExtraFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WindSpeedExtraFeeRuleService {

    private final WindSpeedExtraFeeRuleRepository windSpeedExtraFeeRuleRepository;

    @Autowired
    public WindSpeedExtraFeeRuleService(WindSpeedExtraFeeRuleRepository windSpeedExtraFeeRuleRepository) {
        this.windSpeedExtraFeeRuleRepository = windSpeedExtraFeeRuleRepository;
    }

    /**
     * method for getting all windspeed rules from db
     * @return list of rules
     */
    public List<WindSpeedExtraFeeRule> getAllWindSpeedExtraFeeRules() {
        return windSpeedExtraFeeRuleRepository.findAll();
    }


    /**
     * method for adding a new windspeed rule to db
     * @param vehicle vehicle
     * @param minWindSpeed minimum wind speed when rule applies
     * @param maxWindSpeed maximum wind speed when rule applies
     * @param fee corresponding fee
     * @return save rule to db
     */
    public WindSpeedExtraFeeRule createWindSpeedExtraFeeRule(String vehicle, double minWindSpeed, double maxWindSpeed, double fee) {
        WindSpeedExtraFeeRule windSpeedExtraFeeRule = new WindSpeedExtraFeeRule();
        windSpeedExtraFeeRule.setVehicle(vehicle);
        windSpeedExtraFeeRule.setMinWindSpeed(minWindSpeed);
        windSpeedExtraFeeRule.setMaxWindSpeed(maxWindSpeed);
        windSpeedExtraFeeRule.setFee(fee);
        return windSpeedExtraFeeRuleRepository.save(windSpeedExtraFeeRule);
    }

    /**
     * method for deleting a windspeed extra fee rule
     * @param id id of the rule we want to delete
     */
    public void deleteWindSpeedExtraFeeRule(Long id) {
        if (!windSpeedExtraFeeRuleRepository.existsById(id)) {
            // Return false if the rule with the given ID does not exist
            return;
        }
        windSpeedExtraFeeRuleRepository.deleteById(id);
    }
}
