package com.example.deliveryfeecalculator.service;

import com.example.deliveryfeecalculator.model.BaseFeeRule;
import com.example.deliveryfeecalculator.repository.BaseFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BaseFeeRuleService {

    @Autowired
    private final BaseFeeRuleRepository baseFeeRuleRepository;

    public BaseFeeRuleService(BaseFeeRuleRepository baseFeeRuleRepository) {
        this.baseFeeRuleRepository = baseFeeRuleRepository;
    }


    public List<BaseFeeRule> getAllBaseFeeRules() {
        return baseFeeRuleRepository.findAll();
    }

    public Optional<BaseFeeRule> getBaseFeeRuleById(Long id) {
        return baseFeeRuleRepository.findById(id);
    }

    public BaseFeeRule createBaseFeeRule(BaseFeeRule baseFeeRule) {
        return baseFeeRuleRepository.save(baseFeeRule);
    }

    public boolean deleteBaseFeeRule(Long id) {
        if (!baseFeeRuleRepository.existsById(id)) {
            // case where the rule with the given ID does not exist
            return false;
        }
        baseFeeRuleRepository.deleteById(id);
        return true;
    }
}
