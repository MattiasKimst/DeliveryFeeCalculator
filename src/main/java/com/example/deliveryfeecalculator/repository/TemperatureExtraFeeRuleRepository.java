package com.example.deliveryfeecalculator.repository;

import com.example.deliveryfeecalculator.model.TemperatureExtraFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureExtraFeeRuleRepository extends JpaRepository<TemperatureExtraFeeRule, Long> {
}
