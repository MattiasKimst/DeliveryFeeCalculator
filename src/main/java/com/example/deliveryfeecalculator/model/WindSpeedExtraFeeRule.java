package com.example.deliveryfeecalculator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WindSpeedExtraFeeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vehicle;
    private double minWindSpeed;
    private double maxWindSpeed;
    private double fee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public double getMinWindSpeed() {
        return minWindSpeed;
    }

    public void setMinWindSpeed(double minWindSpeed) {
        this.minWindSpeed = minWindSpeed;
    }

    public double getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public void setMaxWindSpeed(double maxWindSpeed) {
        this.maxWindSpeed = maxWindSpeed;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}