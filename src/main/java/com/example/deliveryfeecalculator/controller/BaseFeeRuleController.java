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

    /**
     * Get method that returns all basefee rules from database
     * @return status code 200 ok, list of base fee rules
     */
    @GetMapping("base/getRules")
    public ResponseEntity<List<BaseFeeRule>> getAllBaseFeeRules() {
        List<BaseFeeRule> baseFeeRules = baseFeeRuleService.getAllBaseFeeRules();
        return new ResponseEntity<>(baseFeeRules, HttpStatus.OK);
    }

    /**
     * Post method for adding a new basefeerule
     * @param baseFeeRule
     * @return status code 201 created, uri of new resource, content of new rule
     */
    @PostMapping("/base/postRule")
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


    /**
     * delete method for deleting a basefeerule
     * @param id corresponding rule id (type long)
     * @return indication that request was successfully processed, no content in response body
     */
    @DeleteMapping("/base/delete/{id}")
    public ResponseEntity<Void> deleteBaseFeeRule(@PathVariable("id") long id) {
        logger.info("delete request received");
        baseFeeRuleService.deleteBaseFeeRule(id);
        return ResponseEntity.noContent().build();
    }
}