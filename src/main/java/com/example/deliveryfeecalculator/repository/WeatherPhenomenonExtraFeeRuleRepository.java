package com.example.deliveryfeecalculator.repository;


import com.example.deliveryfeecalculator.model.WeatherPhenomenonExtraFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherPhenomenonExtraFeeRuleRepository extends JpaRepository<WeatherPhenomenonExtraFeeRule, Long> {
}
