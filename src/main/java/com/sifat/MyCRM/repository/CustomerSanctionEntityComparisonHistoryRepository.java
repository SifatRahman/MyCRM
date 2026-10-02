package com.sifat.MyCRM.repository;

import com.sifat.MyCRM.entity.CustomerSanctionEntityComparisonHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSanctionEntityComparisonHistoryRepository extends JpaRepository<CustomerSanctionEntityComparisonHistory,String> {
}
