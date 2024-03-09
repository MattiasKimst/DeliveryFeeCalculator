package com.example.deliveryfeecalculator.controller;
import com.example.deliveryfeecalculator.model.BaseFeeRule;
import com.example.deliveryfeecalculator.service.BaseFeeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/base-fee-rules")
public class BaseFeeRuleController {

    @Autowired
    private BaseFeeRuleService baseFeeRuleService;

    @GetMapping
    public ResponseEntity<List<BaseFeeRule>> getAllBaseFeeRules() {
        List<BaseFeeRule> baseFeeRules = baseFeeRuleService.getAllBaseFeeRules();
        return new ResponseEntity<>(baseFeeRules, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseFeeRule> createBaseFeeRule(@RequestBody BaseFeeRule baseFeeRule) {
        BaseFeeRule createdBaseFeeRule = baseFeeRuleService.createBaseFeeRule(baseFeeRule);
        return ResponseEntity.created(URI.create("/api/base-fee-rules/" + createdBaseFeeRule.getId()))
                .body(createdBaseFeeRule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseFeeRule(@PathVariable Long id) {
        baseFeeRuleService.deleteBaseFeeRule(id);
        return ResponseEntity.noContent().build();
    }
}