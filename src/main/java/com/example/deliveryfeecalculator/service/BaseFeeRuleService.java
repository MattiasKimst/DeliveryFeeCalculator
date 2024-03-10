package com.example.deliveryfeecalculator.service;

import com.example.deliveryfeecalculator.model.BaseFeeRule;
import com.example.deliveryfeecalculator.repository.BaseFeeRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseFeeRuleService {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeCalculatorService.class);

    @Autowired
    private final BaseFeeRuleRepository baseFeeRuleRepository;

    public BaseFeeRuleService(BaseFeeRuleRepository baseFeeRuleRepository) {
        this.baseFeeRuleRepository = baseFeeRuleRepository;
    }


    /**
     * method for getting a list of all base fee rules in db
     * @return list with all base fee rules
     */
    public List<BaseFeeRule> getAllBaseFeeRules() {
        return baseFeeRuleRepository.findAll();
    }


    /**
     * a method for inserting a new base fee rule to db
     * @param city city
     * @param vehicle vehicle
     * @param station corresponding weather station name in ilmateenistus api
     * @param fee fee
     * @return save the rule with provided data in db
     */
    public BaseFeeRule createBaseFeeRule(String city, String vehicle, String station, double fee) {
        BaseFeeRule baseFeeRule = new BaseFeeRule();
        baseFeeRule.setCity(city);
        baseFeeRule.setVehicle(vehicle);
        baseFeeRule.setStation(station);
        baseFeeRule.setFee(fee);
        return baseFeeRuleRepository.save(baseFeeRule);
    }

    /**
     * a method for deleting a rule from db
     *
     * @param id id of a rule we want to delete
     */
    public void deleteBaseFeeRule(Long id) {
        logger.info("deleteBaseRule with id "+id);
        if (!baseFeeRuleRepository.existsById(id)) {
            // case where the rule with the given ID does not exist, we simply exit
            return;
        }
        baseFeeRuleRepository.deleteById(id); //if exists we delete the rule
    }
}
