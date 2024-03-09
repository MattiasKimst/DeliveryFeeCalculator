package com.example.deliveryfeecalculator.controller;
import com.example.deliveryfeecalculator.model.BaseFeeRule;
import com.example.deliveryfeecalculator.service.BaseFeeRuleService;
import com.example.deliveryfeecalculator.service.DeliveryFeeCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController

public class BaseFeeRuleController {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryFeeCalculatorService.class);

    @Autowired
    private BaseFeeRuleService baseFeeRuleService;

    @GetMapping("/getRules")
    public ResponseEntity<List<BaseFeeRule>> getAllBaseFeeRules() {
        List<BaseFeeRule> baseFeeRules = baseFeeRuleService.getAllBaseFeeRules();
        return new ResponseEntity<>(baseFeeRules, HttpStatus.OK);
    }

    @PostMapping("/postRule")
    public ResponseEntity<BaseFeeRule> createBaseFeeRule(@RequestBody BaseFeeRule baseFeeRule) {
        logger.info("Post request received");
        BaseFeeRule createdBaseFeeRule = baseFeeRuleService.createBaseFeeRule(
                baseFeeRule.getCity(),
                baseFeeRule.getVehicle(),
                baseFeeRule.getStation(),
                baseFeeRule.getFee()
        );
        return ResponseEntity.created(URI.create("/postRule/" + createdBaseFeeRule.getId()))
                .body(createdBaseFeeRule);
    }


    @DeleteMapping("/delete{id}")
    public ResponseEntity<Void> deleteBaseFeeRule(@PathVariable Long id) {
        baseFeeRuleService.deleteBaseFeeRule(id);
        return ResponseEntity.noContent().build();
    }
}