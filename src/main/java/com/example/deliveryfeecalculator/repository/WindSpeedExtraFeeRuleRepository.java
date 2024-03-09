package com.example.deliveryfeecalculator.repository;


import com.example.deliveryfeecalculator.model.WindSpeedExtraFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WindSpeedExtraFeeRuleRepository extends JpaRepository<WindSpeedExtraFeeRule, Long> {
}
