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
                        .afterScoreNameWeight(0.30)
                        .afterScoreDobWeight(0.20)
                        .afterScoreDocWeight(0.25)
                        .afterScoreNationalityWeight(0.15)
                        .afterScoreAddressWeight(0.05)
                        .afterScorePobWeight(0.05)
                        .highRiskStartScore(0.85)
                        .potentialMatchStartScore(0.70)
                        .sanctionClearTillScore(0.65)
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