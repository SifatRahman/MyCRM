package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionIndividualLastDayUpdated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionIndividualLastDayUpdatedRepository extends JpaRepository<SanctionIndividualLastDayUpdated,String> {
}
