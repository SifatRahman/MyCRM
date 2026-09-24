package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionIndividualDesignation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionIndividualDesignationRepository extends JpaRepository<SanctionIndividualDesignation,String> {
}
