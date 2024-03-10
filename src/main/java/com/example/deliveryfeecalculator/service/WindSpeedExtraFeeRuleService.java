package com.example.deliveryfeecalculator.service;

import com.example.deliveryfeecalculator.model.WindSpeedExtraFeeRule;
import com.example.deliveryfeecalculator.repository.WindSpeedExtraFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WindSpeedExtraFeeRuleService {

    private final WindSpeedExtraFeeRuleRepository windSpeedExtraFeeRuleRepository;

    @Autowired
    public WindSpeedExtraFeeRuleService(WindSpeedExtraFeeRuleRepository windSpeedExtraFeeRuleRepository) {
        this.windSpeedExtraFeeRuleRepository = windSpeedExtraFeeRuleRepository;
    }

    public List<WindSpeedExtraFeeRule> getAllWindSpeedExtraFeeRules() {
        return windSpeedExtraFeeRuleRepository.findAll();
    }

    public Optional<WindSpeedExtraFeeRule> getWindSpeedExtraFeeRuleById(Long id) {
        return windSpeedExtraFeeRuleRepository.findById(id);
    }

    public WindSpeedExtraFeeRule createWindSpeedExtraFeeRule(String vehicle, double minWindSpeed, double maxWindSpeed, double fee) {
        WindSpeedExtraFeeRule windSpeedExtraFeeRule = new WindSpeedExtraFeeRule();
        windSpeedExtraFeeRule.setVehicle(vehicle);
        windSpeedExtraFeeRule.setMinWindSpeed(minWindSpeed);
        windSpeedExtraFeeRule.setMaxWindSpeed(maxWindSpeed);
        windSpeedExtraFeeRule.setFee(fee);
        return windSpeedExtraFeeRuleRepository.save(windSpeedExtraFeeRule);
    }

    public boolean deleteWindSpeedExtraFeeRule(Long id) {
        if (!windSpeedExtraFeeRuleRepository.existsById(id)) {
            // Return false if the rule with the given ID does not exist
            return false;
        }
        windSpeedExtraFeeRuleRepository.deleteById(id);
        return true;
    }
}
