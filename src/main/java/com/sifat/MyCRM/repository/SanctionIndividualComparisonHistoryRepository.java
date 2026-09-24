package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.SanctionIndividualComparisonHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionIndividualComparisonHistoryRepository extends JpaRepository<SanctionIndividualComparisonHistory,String> {
}
