package com.sifat.MyCRM.config;


import com.sifat.MyCRM.entity.AMLSanctionConfiguration;
import com.sifat.MyCRM.repository.AMLSanctionConfigurationRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@Service
@RequiredArgsConstructor
public class AMLSanctionConfigurationService {

    private final AMLSanctionConfigurationRepository repository;

    @Getter
    private volatile AMLSanctionConfiguration configuration;

    @PostConstruct
    public void loadOnStartup() {
        configuration = repository.findFirstBy()
                .orElseGet(this::createDefaultConfiguration);
    }

    private AMLSanctionConfiguration createDefaultConfiguration() {

        AMLSanctionConfiguration config =
                AMLSanctionConfiguration.builder()
                        .id(UUID.randomUUID().toString())
                        .onlyExactYearMatchDobScore(0.40)
                        .yearInBetweenGivenTwoYearMatchDobScore(0.30)
                        .onlyYearMatchedWithApproximateYearDobScore(0.30)
                        .afterScoreIndividualNameWeight(0.30)
                        .afterScoreIndividualDobWeight(0.20)
                        .afterScoreIndividualDocWeight(0.25)
                        .afterScoreIndividualNationalityWeight(0.15)
                        .afterScoreIndividualAddressWeight(0.05)
                        .afterScoreIndividualPobWeight(0.05)
                        .afterScoreEntityNameWeight(0.60)
                        .afterScoreEntityAddressWeight(0.40)

                        .individualHighRiskStartScore(0.85)
                        .individualPotentialMatchStartScore(0.70)
                        .individualSanctionClearTillScore(0.65)

                        .entityHighRiskStartScore(0.95)
                        .entityPotentialMatchStartScore(0.88)
                        .entitySanctionClearTillScore(0.78)
                        .build();

        return repository.save(config);
    }

    public synchronized void reload() {
        configuration = repository.findFirstBy()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "AML sanction configuration not found!"
                        ));
    }



}