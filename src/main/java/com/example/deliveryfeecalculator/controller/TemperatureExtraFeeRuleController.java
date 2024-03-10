package com.example.deliveryfeecalculator.controller;

import com.example.deliveryfeecalculator.model.TemperatureExtraFeeRule;
import com.example.deliveryfeecalculator.service.TemperatureExtraFeeRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class TemperatureExtraFeeRuleController {

    private static final Logger logger = LoggerFactory.getLogger(TemperatureExtraFeeRuleController.class);

    @Autowired
    private TemperatureExtraFeeRuleService temperatureExtraFeeRuleService;

    @GetMapping("/temp/getRules")
    public ResponseEntity<List<TemperatureExtraFeeRule>> getAllTemperatureExtraFeeRules() {
        List<TemperatureExtraFeeRule> temperatureExtraFeeRules = temperatureExtraFeeRuleService.getAllTemperatureExtraFeeRules();
        return new ResponseEntity<>(temperatureExtraFeeRules, HttpStatus.OK);
    }

    @PostMapping("/temp/postRule")
    public ResponseEntity<TemperatureExtraFeeRule> createTemperatureExtraFeeRule(@RequestBody TemperatureExtraFeeRule temperatureExtraFeeRule) {
        logger.info("Post request received");
        TemperatureExtraFeeRule createdTemperatureExtraFeeRule = temperatureExtraFeeRuleService.createTemperatureExtraFeeRule(
                temperatureExtraFeeRule.getVehicle(),
                temperatureExtraFeeRule.getMinTemperature(),
                temperatureExtraFeeRule.getMaxTemperature(),
                temperatureExtraFeeRule.getFee()
        );
        return ResponseEntity.created(URI.create("/temperature-extra-fee-rules/" + createdTemperatureExtraFeeRule.getId()))
                .body(createdTemperatureExtraFeeRule);
    }

    @DeleteMapping("/temp/delete/{id}")
    public ResponseEntity<Void> deleteTemperatureExtraFeeRule(@PathVariable Long id) {
        logger.info("delete request received");
        temperatureExtraFeeRuleService.deleteTemperatureExtraFeeRule(id);
        return ResponseEntity.noContent().build();
    }
}
