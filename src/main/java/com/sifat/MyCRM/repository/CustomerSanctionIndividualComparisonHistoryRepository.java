package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.CustomerSanctionIndividualComparisonHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSanctionIndividualComparisonHistoryRepository extends JpaRepository<CustomerSanctionIndividualComparisonHistory,String> {
}
