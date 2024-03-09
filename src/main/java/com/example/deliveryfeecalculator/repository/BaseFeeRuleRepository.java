package com.example.deliveryfeecalculator.repository;

import com.example.deliveryfeecalculator.model.BaseFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseFeeRuleRepository extends JpaRepository<BaseFeeRule, Long> {
}