package com.example.deliveryfeecalculator.controller;

import com.example.deliveryfeecalculator.model.WindSpeedExtraFeeRule;
import com.example.deliveryfeecalculator.service.WindSpeedExtraFeeRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class WindSpeedExtraFeeRuleController {

    private static final Logger logger = LoggerFactory.getLogger(WindSpeedExtraFeeRuleController.class);

    @Autowired
    private WindSpeedExtraFeeRuleService windSpeedExtraFeeRuleService;

    @GetMapping("/wind/getRules")
    public ResponseEntity<List<WindSpeedExtraFeeRule>> getAllWindSpeedExtraFeeRules() {
        List<WindSpeedExtraFeeRule> windSpeedExtraFeeRules = windSpeedExtraFeeRuleService.getAllWindSpeedExtraFeeRules();
        return new ResponseEntity<>(windSpeedExtraFeeRules, HttpStatus.OK);
    }

    @PostMapping("/wind/postRule")
    public ResponseEntity<WindSpeedExtraFeeRule> createWindSpeedExtraFeeRule(@RequestBody WindSpeedExtraFeeRule windSpeedExtraFeeRule) {
        logger.info("Post request received");
        WindSpeedExtraFeeRule createdWindSpeedExtraFeeRule = windSpeedExtraFeeRuleService.createWindSpeedExtraFeeRule(
                windSpeedExtraFeeRule.getVehicle(),
                windSpeedExtraFeeRule.getMinWindSpeed(),
                windSpeedExtraFeeRule.getMaxWindSpeed(),
                windSpeedExtraFeeRule.getFee()
        );
        return ResponseEntity.created(URI.create("/wind-speed-extra-fee-rules/" + createdWindSpeedExtraFeeRule.getId()))
                .body(createdWindSpeedExtraFeeRule);
    }

    @DeleteMapping("/wind/delete/{id}")
    public ResponseEntity<Void> deleteWindSpeedExtraFeeRule(@PathVariable("id") long id) {
        logger.info("delete request received");
        windSpeedExtraFeeRuleService.deleteWindSpeedExtraFeeRule(id);
        return ResponseEntity.noContent().build();
    }
}
