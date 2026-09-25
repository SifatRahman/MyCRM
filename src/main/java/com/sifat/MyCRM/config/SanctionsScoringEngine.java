package com.sifat.MyCRM.config;

import org.springframework.stereotype.Component;

@Component
public class SanctionsScoringEngine {

    private static final double NAME_WEIGHT = 0.40;
    private static final double DOB_WEIGHT = 0.25;
    private static final double NATIONALITY_WEIGHT = 0.15;
    private static final double ADDRESS_WEIGHT = 0.10;
    private static final double POB_WEIGHT = 0.10;

    public double calculate(
            double nameScore,
            double dobScore,
            double nationalityScore,
            double addressScore,
            double pobScore) {

        return nameScore * NAME_WEIGHT +
                        dobScore * DOB_WEIGHT +
                        nationalityScore * NATIONALITY_WEIGHT +
                        addressScore * ADDRESS_WEIGHT +
                        pobScore * POB_WEIGHT;
    }
}