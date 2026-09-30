package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.AMLSanctionConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AMLSanctionConfigurationRepository extends JpaRepository<AMLSanctionConfiguration,String> {

    Optional<AMLSanctionConfiguration> findFirstBy();
}
