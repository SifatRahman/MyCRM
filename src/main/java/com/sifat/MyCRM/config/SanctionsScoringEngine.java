package com.sifat.MyCRM.config;

import org.springframework.stereotype.Component;

@Component
public class SanctionsScoringEngine {
    private final AMLSanctionConfigurationService configurationService;

    public SanctionsScoringEngine(AMLSanctionConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public double calculateIndividualScore(
            double nameScore,
            double dobScore,
            double docScore,
            double nationalityScore,
            double addressScore,
            double pobScore) {
        var config = configurationService.getConfiguration();

        return nameScore * config.getAfterScoreIndividualNameWeight() +
                        dobScore * config.getAfterScoreIndividualDobWeight() +
                        docScore * config.getAfterScoreIndividualDocWeight() +
                        nationalityScore * config.getAfterScoreIndividualNationalityWeight() +
                        addressScore * config.getAfterScoreIndividualAddressWeight() +
                        pobScore * config.getAfterScoreIndividualPobWeight();
    }

    public double calculateEntityScore(
            double nameScore,
            double addressScore) {
        var config = configurationService.getConfiguration();

        return nameScore * config.getAfterScoreEntityNameWeight() +
                addressScore * config.getAfterScoreEntityAddressWeight();
    }
}