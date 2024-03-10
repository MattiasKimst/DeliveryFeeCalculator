package com.example.deliveryfeecalculator.controller;

import com.example.deliveryfeecalculator.model.WeatherPhenomenonExtraFeeRule;
import com.example.deliveryfeecalculator.service.WeatherPhenomenonExtraFeeRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class WeatherPhenomenonExtraFeeRuleController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherPhenomenonExtraFeeRuleController.class);

    @Autowired
    private WeatherPhenomenonExtraFeeRuleService weatherPhenomenonExtraFeeRuleService;

    @GetMapping("/phenomenon/getRules")
    public ResponseEntity<List<WeatherPhenomenonExtraFeeRule>> getAllWeatherPhenomenonExtraFeeRules() {
        List<WeatherPhenomenonExtraFeeRule> weatherPhenomenonExtraFeeRules = weatherPhenomenonExtraFeeRuleService.getAllWeatherPhenomenonExtraFeeRules();
        return new ResponseEntity<>(weatherPhenomenonExtraFeeRules, HttpStatus.OK);
    }

    @PostMapping("/phenomenon/postRule")
    public ResponseEntity<WeatherPhenomenonExtraFeeRule> createWeatherPhenomenonExtraFeeRule(@RequestBody WeatherPhenomenonExtraFeeRule weatherPhenomenonExtraFeeRule) {
        logger.info("Post request received");
        WeatherPhenomenonExtraFeeRule createdWeatherPhenomenonExtraFeeRule = weatherPhenomenonExtraFeeRuleService.createWeatherPhenomenonExtraFeeRule(
                weatherPhenomenonExtraFeeRule.getVehicle(),
                weatherPhenomenonExtraFeeRule.getPhenomenon(),
                weatherPhenomenonExtraFeeRule.getFee()
        );
        return ResponseEntity.created(URI.create("/weather-extra-fee-rules/" + createdWeatherPhenomenonExtraFeeRule.getId()))
                .body(createdWeatherPhenomenonExtraFeeRule);
    }

    @DeleteMapping("/phenomenon/delete/{id}")
    public ResponseEntity<Void> deleteWeatherPhenomenonExtraFeeRule(@PathVariable("id") long id) {
        logger.info("delete request received");
        weatherPhenomenonExtraFeeRuleService.deleteWeatherPhenomenonExtraFeeRule(id);
        return ResponseEntity.noContent().build();
    }
}
