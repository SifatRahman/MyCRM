package com.sifat.MyCRM.config;

import org.springframework.stereotype.Component;

@Component
public class SanctionsScoringEngine {

    private static final double NAME_WEIGHT = 0.40;
    private static final double DOB_WEIGHT = 0.30;
    private static final double ADDRESS_WEIGHT = 0.15;
    private static final double POB_WEIGHT = 0.15;

    public double calculate(
            double nameScore,
            double dobScore,
            double addressScore,
            double pobScore) {

        return nameScore * NAME_WEIGHT +
                        dobScore * DOB_WEIGHT +
                        addressScore * ADDRESS_WEIGHT +
                        pobScore * POB_WEIGHT;
    }
}