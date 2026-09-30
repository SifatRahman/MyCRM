package com.sifat.MyCRM.config;

import org.springframework.stereotype.Component;

@Component
public class SanctionsScoringEngine {
    private final AMLSanctionConfigurationService configurationService;

    public SanctionsScoringEngine(AMLSanctionConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public double calculate(
            double nameScore,
            double dobScore,
            double docScore,
            double nationalityScore,
            double addressScore,
            double pobScore) {
        var config = configurationService.getConfiguration();

        return nameScore * config.getAfterScoreNameWeight() +
                        dobScore * config.getAfterScoreDobWeight() +
                        docScore * config.getAfterScoreDocWeight() +
                        nationalityScore * config.getAfterScoreNationalityWeight() +
                        addressScore * config.getAfterScoreAddressWeight() +
                        pobScore * config.getAfterScorePobWeight();
    }
}